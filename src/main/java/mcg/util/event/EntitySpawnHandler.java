package mcg.util.event;

import mcg.util.MCGUtil;
import mcg.util.entity.EntitySpawn;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by drzzm32 on 2020.2.20.
 */
public class EntitySpawnHandler {

    private static EntitySpawnHandler instance;

    public static EntitySpawnHandler instance() {
        if (instance == null)
            instance = new EntitySpawnHandler();
        return instance;
    }

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        while (!EntitySpawn.ENTITY_STACK.isEmpty()) {
            EntitySpawn.EntityInfo info = EntitySpawn.ENTITY_STACK.pop();
            World world = DimensionManager.getWorld(info.dimension);
            world.spawnEntity(info.entity);
            MCGUtil.logger.info("Spawned entity via Multi-thread at: " + info.entity.getPosition());
        }
    }

}
