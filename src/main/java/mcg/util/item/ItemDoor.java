package mcg.util.item;

import mcg.util.MCGUtil;
import mcg.util.block.BlockDoor;
import mcg.util.creativetab.CreativeTabLoader;

/**
 * Created by drzzm32 on 2019.2.24.
 */
public class ItemDoor extends net.minecraft.item.ItemDoor {

    public ItemDoor(BlockDoor door) {
        super(door);
        setUnlocalizedName(door.getUnlocalizedName().replace("tile.", ""));
        setRegistryName(MCGUtil.MODID, door.getRegistryName().getResourcePath());
        setCreativeTab(CreativeTabLoader.tabMCGUtil);
    }

}
