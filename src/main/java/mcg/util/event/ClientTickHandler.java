package mcg.util.event;

import club.nsdn.nyasamarailway.api.cart.AbsLocoBase;
import club.nsdn.nyasamarailway.item.tool.ItemNTP32Bit;
import club.nsdn.nyasamarailway.item.tool.ItemNTP8Bit;
import club.nsdn.nyasamarailway.network.NetworkWrapper;
import club.nsdn.nyasamarailway.network.TrainPacket;
import club.nsdn.nyasamarailway.util.TrainController;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import mcg.util.MCGUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Enumeration;

/**
 * Created by drzzm32 on 2020.3.22.
 */
public class ClientTickHandler {
    private static ClientTickHandler instance;

    public static ClientTickHandler instance() {
        if (instance == null)
            instance = new ClientTickHandler();
        return instance;
    }

    public static Class<?> EntityMCGBoat;
    public static Field jump;

    public static SerialPort serialPort;
    public static String portName = "";

    public static void refreshPort() {
        if (!portName.isEmpty()) {
            try {
                CommPortIdentifier.getPortIdentifier(portName);
            } catch (Exception ex) {
                serialPort = null;
                portName = "";
            }
        } else {
            try {
                Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
                while (portList.hasMoreElements()) {
                    String name = portList.nextElement().getName();
                    if (name.contains("COM") && !name.equals("COM1")) {
                        CommPortIdentifier identifier = CommPortIdentifier.getPortIdentifier(name);
                        CommPort port = identifier.open(name, 3000);
                        if (port instanceof SerialPort) {
                            portName = name;
                            serialPort = (SerialPort) port;
                            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                            MCGUtil.logger.info("Port connected: " + portName);
                            return;
                        }
                    }
                }
            } catch (Exception ignored) { }
        }
    }

    public float x, y, z; // [x, y, z]

    public void getData() {
        if (serialPort != null) {
            try {
                InputStream stream = serialPort.getInputStream();
                if (stream.available() <= 32)
                    return;
                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader buffer = new BufferedReader(reader);
                if (buffer.ready()) {
                    String str = buffer.readLine();
                    if (str.startsWith("[") && str.endsWith("]")) {
                        String[] parts = str.split("[\\[\\]]");
                        for (String s : parts) {
                            if (!s.contains(","))
                                continue;
                            String[] params = s.split(",");
                            if (params.length == 3) {
                                x = Float.parseFloat(params[0]);
                                y = Float.parseFloat(params[1]);
                                z = Float.parseFloat(params[2]);
                            }
                        }
                    }
                }
            } catch (Exception ignored) { }
        } else
            refreshPort();
    }

    public ClientTickHandler() {
        try {
            EntityMCGBoat = Class.forName("moe.gensoukyo.mcgproject.common.entity.EntityMCGBoat");
            jump = EntityMCGBoat.getDeclaredField("jump");
            jump.setAccessible(true);
        } catch (Exception ignored) {
            EntityMCGBoat = null;
            jump = null;
        }

        refreshPort();
    }

    public void setJump(Entity entity, float val) {
        try {
            jump.set(entity, val);
        } catch (Exception ignored) { }
    }

    public void modifyBoat() {
        if (EntityMCGBoat == null || jump == null)
            return;

        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player == null) return;

        Entity entity = player.getRidingEntity();
        if (EntityMCGBoat.isInstance(entity)) {
            entity.stepHeight = 0.5F;
            setJump(entity, 0.0F);
        }
    }

    public void modifyFOV() {
        Minecraft minecraft = Minecraft.getMinecraft();
        float val = (y + 32767) / 65535;
        minecraft.gameSettings.fovSetting = 5 + 160 * val;
    }

    public void trainCtl() {
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayer player = minecraft.player;

        float ctrl = x, dir = z;
        int P = 0, R = 0, D = 0; // P:0~20, R:1~10
        if (ctrl >= 0) {
            P = (int) (ctrl / 32767 * 20);
            R = 10;
        } else {
            P = 0;
            R = 10 + (int) (ctrl / 32767 * 9);
        }
        if (dir >= 64) D = 1;
        else if (dir <= -64) D = -1;
        else D = 0;

        if (player == null) return;

        ItemStack stack = player.getHeldItemMainhand();
        if (!stack.isEmpty()) {
            TrainPacket packet = new TrainPacket();
            if (stack.getItem() instanceof ItemNTP8Bit) {
                ItemNTP8Bit ntp8Bit = (ItemNTP8Bit) stack.getItem();
                packet.fromStack(stack);

                EntityMinecart cart = packet.getCartInClient(ntp8Bit.cart.get(stack));
                if (cart != null) {
                    if (cart instanceof AbsLocoBase) {
                        TrainController.doControl(packet, player);
                        packet.P = P; packet.R = R; packet.Dir = D;
                        NetworkWrapper.instance.sendToServer(packet);
                        packet.toStack(stack);
                        return;
                    }
                    TrainController.doControl(packet, player);
                    packet.P = P; packet.R = R; packet.Dir = D;
                    NetworkWrapper.instance.sendToServer(packet);
                    packet.toStack(stack);
                }
            } else if (stack.getItem() instanceof ItemNTP32Bit) {
                ItemNTP32Bit ntp32Bit = (ItemNTP32Bit) stack.getItem();
                packet.fromStack(stack);

                int[] carts = ntp32Bit.carts.get(stack);
                if (carts.length == 1 && carts[0] == -1)
                    return;
                TrainController.doControl(packet, player);
                packet.P = P; packet.R = R; packet.Dir = D;
                NetworkWrapper.instance.sendToServer(packet);
                packet.toStack(stack);
            }
        }
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        getData();

        if (serialPort != null) {
            modifyFOV();
            trainCtl();
        }

        modifyBoat();
    }
}
