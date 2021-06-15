package artifality.block;

import artifality.ArtifalityMod;
import artifality.block.base.BaseBlock;
import artifality.block.base.BaseGlassBlock;
import artifality.block.base.CrystalBlock;
import artifality.block.base.LensBlock;
import artifality.item.base.BaseBlockItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ArtifalityBlocks {

    private static final Map<Identifier, BaseBlockItem> ITEMS = new LinkedHashMap<>();
    private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    public static final Block INCREMENTAL_CRYSTAL = add("incremental_crystal", new CrystalBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.GLASS).luminance(value -> 6), "Incremental Crystal"));
    public static final Block INCREMENTAL_CRYSTAL_BLOCK = add("incremental_crystal_block", new BaseBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.GLASS), "cube_all", "Incremental Crystal Block"));
    public static final Block INCREMENTAL_GLASS = add("incremental_glass", new BaseGlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS).sounds(BlockSoundGroup.GLASS).nonOpaque(), "cube_all", "Incremental Glass"));
    public static final Block INCREMENTAL_LENS = add("incremental_lens", new IncrementalLensBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.NETHERITE).nonOpaque(), "Incremental Lens"));

    public static final Block LUNAR_CRYSTAL_CRYSTAL = add("lunar_crystal_crystal", new CrystalBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.GLASS).luminance(value -> 6), "Lunar Crystal"));
    public static final Block LUNAR_CRYSTAL_BLOCK = add("lunar_crystal_block", new BaseBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.GLASS), "Lunar Crystal Block"));
    public static final Block LUNAR_CRYSTAL_GLASS = add("lunar_crystal_glass", new BaseGlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS).sounds(BlockSoundGroup.GLASS).nonOpaque(), "Lunar Crystal Glass"));
    public static final Block LUNAR_CRYSTAL_LENS = add("lunar_crystal_lens", new LensBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.NETHERITE).nonOpaque(), "Lunar Crystal Lens"));

    public static final Block CRYSTAL_HEART = add("crystal_heart", new CrystalBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.GLASS).luminance(value -> 6), "Crystal Heart"));
    public static final Block CRYSTAL_HEART_BLOCK = add("crystal_heart_block", new BaseBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.GLASS), "Crystal Heart Block"));
    public static final Block CRYSTAL_HEART_GLASS = add("crystal_heart_glass", new BaseGlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS).sounds(BlockSoundGroup.GLASS).nonOpaque(), "Crystal Heart Glass"));
    public static final Block CRYSTAL_HEART_LENS = add("crystal_heart_lens", new LensBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.NETHERITE).nonOpaque(), "Crystal Heart Lens"));

    public static final Block ARTIFACT_UPGRADER = add("artifact_upgrader", new ArtifactUpgraderBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE), "cube_bottom_top", "Artifact Upgrader"));

    private static Block add(String name, Block block) {
        Item.Settings settings = new Item.Settings();
        settings.group(ArtifalityMod.ITEMS);
        return addBlockItem(name, block, new BaseBlockItem(block, settings));
    }

    private static Block addBlockItem(String name, Block block, BaseBlockItem item) {
        addBlock(name, block);
        if (item != null) {
            item.appendBlocks(Item.BLOCK_ITEMS, item);
            ITEMS.put(new Identifier(ArtifalityMod.MODID, name), item);
        }
        return block;
    }

    private static void addBlock(String name, Block block) {
        BLOCKS.put(new Identifier(ArtifalityMod.MODID, name), block);
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
}
