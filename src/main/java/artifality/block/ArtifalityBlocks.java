package artifality.block;

import artifality.ArtifalityMod;
import artifality.block.base.ClusterBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import ru.bclib.blocks.BaseBlock;
import ru.bclib.registry.BlockRegistry;

@SuppressWarnings("unused")
public class ArtifalityBlocks extends BlockRegistry {

    public static final Block SMALL_INCREMENTAL_CLUSTER = addCluster("small_incremental_cluster", "small");
    public static final Block MEDIUM_INCREMENTAL_CLUSTER = addCluster("medium_incremental_cluster", "medium");
    public static final Block INCREMENTAL_CLUSTER = addCluster("incremental_cluster", "large");
    public static final Block BUDDING_INCREMENTAL = add("budding_incremental", new BuddingIncrementalBlock(copyOf(Blocks.STONE).ticksRandomly()));
    public static final Block INCREMENTAL_BLOCK = addLitBlock("incremental_block");
    public static final Block INCREMENTAL_LENS = add("incremental_lens", new IncrementalLensBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.NETHERITE).nonOpaque()));

    public static final Block SMALL_LUNAMENTAL_CLUSTER = addCluster("small_lunamental_cluster", "small");
    public static final Block MEDIUM_LUNAMENTAL_CLUSTER = addCluster("medium_lunamental_cluster", "medium");
    public static final Block LUNAMENTAL_CLUSTER = addCluster("lunamental_cluster", "large");
    public static final Block BUDDING_LUNAMENTAL = add("budding_lunamental", new BuddingLunamentalBlock(copyOf(Blocks.STONE).ticksRandomly()));
    public static final Block LUNAMENTAL_BLOCK = addLitBlock("lunamental_block");
    public static final Block LUNAMENTAL_LENS = add("lunamental_lens", new LunamentalLensBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.NETHERITE).nonOpaque()));

    public static final Block SMALL_LOVEMENTAL_CLUSTER = addCluster("small_lovemental_cluster", "small");
    public static final Block MEDIUM_LOVEMENTAL_CLUSTER = addCluster("medium_lovemental_cluster", "medium");
    public static final Block LOVEMENTAL_CLUSTER = addCluster("lovemental_cluster", "large");
    public static final Block BUDDING_LOVEMENTAL = add("budding_lovemental", new BuddingLovementalBlock(copyOf(Blocks.STONE).ticksRandomly()));
    public static final Block LOVEMENTAL_BLOCK = addLitBlock("lovemental_block");
    public static final Block LOVEMENTAL_LENS = add("lovemental_lens", new LovementalLensBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.NETHERITE).nonOpaque()));

    public static final Block UPGRADING_PEDESTAL = add("upgrading_pedestal", new UpgradingPedestalBlock(copyOf(Blocks.COBBLESTONE)));
//    public static final Block LUNAR_PEDESTAL = add("lunar_pedestal", new UpgradingPedestalBlock(copyOf(Blocks.COBBLESTONE)));

    private static BlockRegistry BLOCK_REGISTRY;

    private ArtifalityBlocks() {
        super(ArtifalityMod.ITEMS_ITEM_GROUP);
    }

    private static Block addCluster(String name, String type){
        return add(name, new ClusterBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).luminance(10), type));
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
        if(BLOCK_REGISTRY == null) BLOCK_REGISTRY = new ArtifalityBlocks();
        return BLOCK_REGISTRY;
    }
}
