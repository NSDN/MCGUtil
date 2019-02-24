package mcg.util.block;

import mcg.util.MCGUtil;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import mcg.util.creativetab.CreativeTabLoader;
import net.minecraft.block.state.IBlockState;

/**
 * Created by drzzm32 on 2019.2.24.
 */
public class BlockLogo extends BlockGlass {

    public BlockLogo() {
        super(Material.GLASS, false);
        setUnlocalizedName("Logo");
        setRegistryName(MCGUtil.MODID, "logo");
        setHardness(2.0F);
        setLightLevel(1);
        setSoundType(SoundType.GLASS);
        setResistance(10.0F);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

}
