package mcg.util.proxy;

import mcg.util.event.EventRegister;
import net.minecraftforge.fml.common.event.*;

/**
 * Created by drzzm32 on 2020.3.22.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        EventRegister.registerClient();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }


}
