package mcg.util.proxy;

import mcg.util.event.EventRegister;
import net.minecraftforge.fml.common.event.*;

/**
 * Created by drzzm32 on 2020.3.22.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        EventRegister.registerCommon();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
