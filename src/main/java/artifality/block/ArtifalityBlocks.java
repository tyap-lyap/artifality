package artifality.block;

import artifality.ArtifalityMod;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ArtifalityBlocks {

    private static final Map<Identifier, BlockItem> ITEMS = new LinkedHashMap<>();
    private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();


    public static final Block INCREMENTAL_CRYSTAL = add("incremental_crystal", new CrystalBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(BlockSoundGroup.GLASS), "Incremental Crystal"));
    public static final Block INCREMENTAL_CRYSTAL_BLOCK = add("incremental_crystal_block", new BaseBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(BlockSoundGroup.GLASS), "cube_all", "Incremental Crystal Block"));
    public static final Block INCREMENTAL_GLASS = add("incremental_glass", new BaseGlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS).sounds(BlockSoundGroup.GLASS).nonOpaque(), "cube_all", "Incremental Glass"));

    //public static final Block LUNAR_CRYSTAL_CRYSTAL = add("lunar_crystal_crystal", new CrystalBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(BlockSoundGroup.GLASS), "Lunar Crystal"));
    public static final Block LUNAR_CRYSTAL_BLOCK = add("lunar_crystal_block", new BaseBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(BlockSoundGroup.GLASS), "Lunar Crystal Block"));
    public static final Block LUNAR_CRYSTAL_GLASS = add("lunar_crystal_glass", new BaseGlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS).sounds(BlockSoundGroup.GLASS).nonOpaque(), "Lunar Crystal Glass"));

    public static final Block INCREMENTAL_LENS = add("incremental_lens", new IncrementalLensBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(BlockSoundGroup.NETHERITE).nonOpaque(), "Incremental Lens"));


    private static Block add(String name, Block block) {
        Item.Settings settings = new Item.Settings();
        settings.group(ArtifalityMod.ITEMS);
        return addBlockItem(name, block, new BlockItem(block, settings));
    }

    private static Block addBlockItem(String name, Block block, BlockItem item) {
        addBlock(name, block);
        if (item != null) {
            item.appendBlocks(Item.BLOCK_ITEMS, item);
            ITEMS.put(new Identifier(ArtifalityMod.MODID, name), item);
        }
        return block;
    }

    private static Block addBlock(String name, Block block) {
        BLOCKS.put(new Identifier(ArtifalityMod.MODID, name), block);
        return block;
    }

    public static void register() {

        for (Identifier id : ITEMS.keySet()) {
            Registry.register(Registry.ITEM, id, ITEMS.get(id));
        }
        for (Identifier id : BLOCKS.keySet()) {
            Registry.register(Registry.BLOCK, id, BLOCKS.get(id));
        }
    }

    public static Map<Identifier, Block> getBlocks() {
        return BLOCKS;
    }

    public static Map<Identifier, BlockItem> getBlockItems() {
        return ITEMS;
    }
}
