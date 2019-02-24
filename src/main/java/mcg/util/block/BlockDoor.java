package mcg.util.block;

import mcg.util.MCGUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * Created by drzzm32 on 2019.2.24.
 */
public class BlockDoor extends net.minecraft.block.BlockDoor {

    public BlockDoor(String name, String id, boolean hasLock) {
        super(hasLock ? Material.IRON : Material.WOOD);
        disableStats();
        setSoundType(hasLock ? SoundType.METAL : SoundType.WOOD);
        setHardness(hasLock ? 5.0F : 3.0F);
        setUnlocalizedName(name);
        setRegistryName(MCGUtil.MODID, id);
    }

}
