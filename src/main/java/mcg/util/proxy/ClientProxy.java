package mcg.util.proxy;

import mcg.util.MCGUtil;
import mcg.util.event.EventRegister;
import mcg.util.hackychat.ClientCmdHacker;
import mcg.util.hackychat.CmdFilteredChat;
import mcg.util.hackychat.FilteredChatHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;

/**
 * Created by drzzm32 on 2020.3.22.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        EventRegister.registerClient();

        Class<?> cls = null;
        try {
            cls = Class.forName("moe.gensoukyo.mcgproject.common.feature.hackychat.ClientCmdHacker");
        } catch (Exception ignored) { }
        if (cls == null) {
            MCGUtil.logger.error("Load HackyChat from " + MCGUtil.NAME);
            Runtime.getRuntime().addShutdownHook(new Thread(MCGUtil.NAME + " Shutdown Thread") {
                @Override
                public void run() {
                    MCGUtil.logger.info("Closing something...");
                    ClientCmdHacker.FUNC_TIMER.cancel();
                }
            });
            MinecraftForge.EVENT_BUS.register(FilteredChatHandler.instance());
            ClientCmdHacker.replaceHandlerInstance();
            ClientCommandHandler.instance.registerCommand(new CmdFilteredChat());
        } else {
            MCGUtil.logger.info("Load HackyChat from MCG Project");
        }
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
