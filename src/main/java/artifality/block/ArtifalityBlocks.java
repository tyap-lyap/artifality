package artifality.block;

import artifality.ArtifalityMod;
import artifality.block.base.CrystalBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import ru.bclib.blocks.BaseBlock;
import ru.bclib.registry.BlockRegistry;

@SuppressWarnings("unused")
public class ArtifalityBlocks extends BlockRegistry {

    public static final Block SMALL_INCREMENTAL_CRYSTAL_CLUSTER = addCluster("small_incremental_crystal_cluster", "small");
    public static final Block MEDIUM_INCREMENTAL_CRYSTAL_CLUSTER = addCluster("medium_incremental_crystal_cluster", "medium");
    public static final Block INCREMENTAL_CRYSTAL_CLUSTER = addCluster("incremental_crystal_cluster", "large");
    public static final Block BUDDING_INCREMENTAL_CRYSTAL = add("budding_incremental_crystal", new BuddingIncrementalCrystalBlock(copyOf(Blocks.STONE).ticksRandomly()));
    public static final Block INCREMENTAL_CRYSTAL_BLOCK = addLitBlock("incremental_crystal_block");
    public static final Block INCREMENTAL_CRYSTAL_LENS = add("incremental_crystal_lens", new IncrementalCrystalLensBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.NETHERITE).nonOpaque()));

    public static final Block SMALL_LUNAR_CRYSTAL_CLUSTER = addCluster("small_lunar_crystal_cluster", "small");
    public static final Block MEDIUM_LUNAR_CRYSTAL_CLUSTER = addCluster("medium_lunar_crystal_cluster", "medium");
    public static final Block LUNAR_CRYSTAL_CLUSTER = addCluster("lunar_crystal_cluster", "large");
    public static final Block BUDDING_LUNAR_CRYSTAL = add("budding_lunar_crystal", new BuddingLunarCrystalBlock(copyOf(Blocks.STONE).ticksRandomly()));
    public static final Block LUNAR_CRYSTAL_BLOCK = addLitBlock("lunar_crystal_block");
    public static final Block LUNAR_CRYSTAL_LENS = add("lunar_crystal_lens", new LunarCrystalLensBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.NETHERITE).nonOpaque()));

    public static final Block SMALL_LIFE_CRYSTAL_CLUSTER = addCluster("small_life_crystal_cluster", "small");
    public static final Block MEDIUM_LIFE_CRYSTAL_CLUSTER = addCluster("medium_life_crystal_cluster", "medium");
    public static final Block LIFE_CRYSTAL_CLUSTER = addCluster("life_crystal_cluster", "large");
    public static final Block BUDDING_LIFE_CRYSTAL = add("budding_life_crystal", new BuddingLifeCrystalBlock(copyOf(Blocks.STONE).ticksRandomly()));
    public static final Block LIFE_CRYSTAL_BLOCK = addLitBlock("life_crystal_block");
    public static final Block LIFE_CRYSTAL_LENS = add("life_crystal_lens", new LifeCrystalLensBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.NETHERITE).nonOpaque()));

    public static final Block UPGRADING_PEDESTAL = add("upgrading_pedestal", new UpgradingPedestalBlock(copyOf(Blocks.COBBLESTONE)));
//    public static final Block LUNAR_PEDESTAL = addDummy("lunar_pedestal", new UpgradingPedestalBlock(copyOf(Blocks.COBBLESTONE)));

    private static BlockRegistry BLOCK_REGISTRY;

    private ArtifalityBlocks() {
        super(ArtifalityMod.ITEMS_ITEM_GROUP);
    }

    private static Block addCluster(String name, String type){
        return add(name, new CrystalBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).luminance(10), type));
    }

    private static Block addLitBlock(String name){
        return add(name, new BaseBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15)));
    }

    private static Block add(String id, Block block){
        return getBlockRegistry().register(ArtifalityMod.newId(id), block);
    }

    private static FabricBlockSettings copyOf(Block block){
        return FabricBlockSettings.copyOf(block);
    }

    @Override
    public Identifier createModId(String name) {
        return ArtifalityMod.newId(name);
    }

    private static BlockRegistry getBlockRegistry() {
        if (BLOCK_REGISTRY == null) {
            BLOCK_REGISTRY = new ArtifalityBlocks();
        }

        return BLOCK_REGISTRY;
    }
}
