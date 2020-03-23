package mcg.util;

import mcg.util.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

/**
 * Created by drzzm32 on 2020.3.22.
 */
@Mod(modid = MCGUtil.MODID, name = MCGUtil.NAME, version = MCGUtil.VERSION)
public class MCGUtil {

    @Mod.Instance
    public static MCGUtil instance;
    public static final String MODID = "mcgutil";
    public static final String NAME = "MCG Util";
    public static final String VERSION = "1.0";
    public static Logger logger;

    @SidedProxy(clientSide = "mcg.util.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static MCGUtil getInstance() {
        return instance;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
