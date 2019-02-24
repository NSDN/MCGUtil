package mcg.util.block;

import mcg.util.MCGUtil;
import mcg.util.creativetab.CreativeTabLoader;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * Created by drzzm32 on 2019.2.24.
 */
public class BlockTrapDoor extends net.minecraft.block.BlockTrapDoor {

    public BlockTrapDoor(String name, String id) {
        super(Material.WOOD);
        setHardness(3.0F);
        setSoundType(SoundType.WOOD);
        setUnlocalizedName(name);
        setRegistryName(MCGUtil.MODID, id);
        setCreativeTab(CreativeTabLoader.tabMCGUtil);
    }

}
