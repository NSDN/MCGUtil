package mcg.util.creativetab;

import mcg.util.block.BlockLoader;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.*;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by drzzm32 on 2019.2.24.
 */
public class CreativeTabLoader {

    public static CreativeTabs tabMCGUtil;

    public CreativeTabLoader(FMLPreInitializationEvent event) {
        tabMCGUtil = new CreativeTabs("tabMCGUtil") {
            @Override
            public ItemStack getTabIconItem() {
                return BlockLoader.itemBlocks.get(BlockLoader.logo).getDefaultInstance();
            }
        };
    }

}
