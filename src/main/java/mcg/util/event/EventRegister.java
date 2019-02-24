package mcg.util.event;

import mcg.util.block.BlockLoader;
import mcg.util.item.ItemLoader;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by drzzm32 on 2019.2.24.
 */
public class EventRegister {

    public static void registerCommon() {
        MinecraftForge.EVENT_BUS.register(BlockLoader.instance());
        MinecraftForge.EVENT_BUS.register(ItemLoader.instance());
    }

    public static void registerServer() {

    }

    public static void registerClient() {
    }

}
