package mcg.util.block;

import mcg.util.MCGUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by drzzm32 on 2019.2.24.
 */
public class BlockLoader {

    private static BlockLoader instance;
    public static BlockLoader instance() {
        if (instance == null) instance = new BlockLoader();
        return instance;
    }

    public static LinkedList<Block> blocks;
    public static LinkedHashMap<Block, Item> itemBlocks;
    public static Block logo;

    public static LinkedList<BlockDoor> doors;

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        MCGUtil.logger.info("registering Blocks");
        event.getRegistry().registerAll(blocks.toArray(new Block[0]));
    }

    @SubscribeEvent
    public void registerItemBlocks(RegistryEvent.Register<Item> event) {
        MCGUtil.logger.info("registering ItemBlocks");
        for (Block b : blocks) {
            if (b instanceof BlockDoor) continue;
            String regName = b.getUnlocalizedName().toLowerCase();
            if (b.getRegistryName() != null)
                regName = b.getRegistryName().toString().split(":")[1];
            itemBlocks.put(b, new ItemBlock(b).setRegistryName(MCGUtil.MODID, regName));
        }
        event.getRegistry().registerAll(itemBlocks.values().toArray(new Item[0]));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerItemBlockModels(ModelRegistryEvent event) {
        MCGUtil.logger.info("registering ItemBlock Models (Block's Icon)");
        for (Item i : itemBlocks.values()) {
            String regName = i.getUnlocalizedName().toLowerCase();
            if (i.getRegistryName() != null)
                regName = i.getRegistryName().toString();
            ModelLoader.setCustomModelResourceLocation(i, 0,
                    new ModelResourceLocation(regName, "inventory")
            );
        }
    }

    public BlockLoader() {
        blocks = new LinkedList<>();
        itemBlocks = new LinkedHashMap<>();
        logo = new BlockLogo();
        blocks.add(logo);

        blocks.add(new BlockDoor("BookDoor", "book_door", false));
        blocks.add(new BlockDoor("EienDoor2", "eien_2_door", true));
        blocks.add(new BlockDoor("EienDoor", "eien_door", true));
        blocks.add(new BlockDoor("GlassDoor", "glass_door", false));
        blocks.add(new BlockDoor("NihonDoor", "nihon_door", false));
        blocks.add(new BlockDoor("WoodDoor2", "wood_2_door", true));
        blocks.add(new BlockDoor("WoodDoor", "wood_door", false));

        doors = new LinkedList<>();
        for (Block block : blocks) {
            if (block instanceof BlockDoor)
                doors.add((BlockDoor) block);
        }

        blocks.add(new BlockTrapDoor("WoodTrapdoor", "wood_trapdoor"));
        blocks.add(new BlockTrapDoor("NewspaperTrapdoor", "newspaper_trapdoor"));
    }

}