package mcg.util.proxy;

import mcg.util.event.EventRegister;
import mcg.util.creativetab.CreativeTabLoader;
import net.minecraftforge.fml.common.event.*;

/**
 * Created by drzzm32 on 2019.2.24.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        new CreativeTabLoader(event);
        EventRegister.registerCommon();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
