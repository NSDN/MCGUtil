package mcg.util.event;

import net.minecraftforge.common.MinecraftForge;

import java.awt.*;

/**
 * Created by drzzm32 on 2020.3.22.
 */
public class EventRegister {

    public static void registerCommon() {
    }

    public static void registerServer() {

    }

    public static void registerClient() {
        MinecraftForge.EVENT_BUS.register(ClientTickHandler.instance());
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (Font f : ge.getAllFonts())
            System.out.println(f.getName());
    }

}
