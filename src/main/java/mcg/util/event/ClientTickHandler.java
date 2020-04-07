package mcg.util.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;

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

    public ClientTickHandler() {
        try {
            EntityMCGBoat = Class.forName("moe.gensoukyo.mcgproject.common.entity.EntityMCGBoat");
            jump = EntityMCGBoat.getDeclaredField("jump");
            jump.setAccessible(true);
        } catch (Exception ignored) {
            EntityMCGBoat = null;
            jump = null;
        }
    }

    public void setJump(Entity entity, float val) {
        try {
            jump.set(entity, val);
        } catch (Exception ignored) { }
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
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
}
