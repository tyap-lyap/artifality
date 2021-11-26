package artifality.registry;

import artifality.block.UpgradingPedestalBlock;
import artifality.block.base.*;
import artifality.list.LensEffects;
import artifality.registry.base.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class ArtifalityBlocks extends BlockRegistry {
    public static final Block COMMON_CRATE = add("common_crate", new CrateBlock(copyOf(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.WOOD).luminance(7)));
    public static final Block RARE_CRATE = add("rare_crate", new CrateBlock(copyOf(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.STONE).luminance(7)));
    public static final Block LEGENDARY_CRATE = add("legendary_crate", new CrateBlock(copyOf(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.STONE).luminance(7)));
    public static final Block LUNAR_CRATE = add("lunar_crate", new CrateBlock(copyOf(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.STONE).luminance(7)));

    public static final Block INCREMENTAL_ORB = add("incremental_orb", new OrbBlock(copyOf(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));
    public static final Block LUNAR_ORB = add("lunar_orb", new OrbBlock(copyOf(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));
    public static final Block LIFE_ORB = add("life_orb", new OrbBlock(copyOf(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));
    public static final Block WRATH_ORB = add("wrath_orb", new OrbBlock(copyOf(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));

    public static final Block SMALL_INCREMENTAL_CRYSTAL_CLUSTER = cluster("small_incremental_crystal_cluster", "small");
    public static final Block MEDIUM_INCREMENTAL_CRYSTAL_CLUSTER = cluster("medium_incremental_crystal_cluster", "medium");
    public static final Block INCREMENTAL_CRYSTAL_CLUSTER = cluster("incremental_crystal_cluster", "large");
    public static final Block BUDDING_INCREMENTAL_CRYSTAL = budding("budding_incremental_crystal", SMALL_INCREMENTAL_CRYSTAL_CLUSTER, MEDIUM_INCREMENTAL_CRYSTAL_CLUSTER, INCREMENTAL_CRYSTAL_CLUSTER);
    public static final Block INCREMENTAL_CRYSTAL_BLOCK = crystalBlock("incremental_crystal_block");
    public static final Block INCREMENTAL_CRYSTAL_SLAB = crystalSlab("incremental_crystal_slab");
    public static final Block INCREMENTAL_CRYSTAL_STAIRS = crystalStairs("incremental_crystal_stairs");

    public static final Block SMALL_LUNAR_CRYSTAL_CLUSTER = cluster("small_lunar_crystal_cluster", "small");
    public static final Block MEDIUM_LUNAR_CRYSTAL_CLUSTER = cluster("medium_lunar_crystal_cluster", "medium");
    public static final Block LUNAR_CRYSTAL_CLUSTER = cluster("lunar_crystal_cluster", "large");
    public static final Block BUDDING_LUNAR_CRYSTAL = budding("budding_lunar_crystal", SMALL_LUNAR_CRYSTAL_CLUSTER, MEDIUM_LUNAR_CRYSTAL_CLUSTER, LUNAR_CRYSTAL_CLUSTER);
    public static final Block LUNAR_CRYSTAL_BLOCK = crystalBlock("lunar_crystal_block");
    public static final Block LUNAR_CRYSTAL_SLAB = crystalSlab("lunar_crystal_slab");
    public static final Block LUNAR_CRYSTAL_STAIRS = crystalStairs("lunar_crystal_stairs");

    public static final Block SMALL_LIFE_CRYSTAL_CLUSTER = cluster("small_life_crystal_cluster", "small");
    public static final Block MEDIUM_LIFE_CRYSTAL_CLUSTER = cluster("medium_life_crystal_cluster", "medium");
    public static final Block LIFE_CRYSTAL_CLUSTER = cluster("life_crystal_cluster", "large");
    public static final Block BUDDING_LIFE_CRYSTAL = budding("budding_life_crystal", SMALL_LIFE_CRYSTAL_CLUSTER, MEDIUM_LIFE_CRYSTAL_CLUSTER, LIFE_CRYSTAL_CLUSTER);
    public static final Block LIFE_CRYSTAL_BLOCK = crystalBlock("life_crystal_block");
    public static final Block LIFE_CRYSTAL_SLAB = crystalSlab("life_crystal_slab");
    public static final Block LIFE_CRYSTAL_STAIRS = crystalStairs("life_crystal_stairs");

    public static final Block SMALL_WRATH_CRYSTAL_CLUSTER = cluster("small_wrath_crystal_cluster", "small");
    public static final Block MEDIUM_WRATH_CRYSTAL_CLUSTER = cluster("medium_wrath_crystal_cluster", "medium");
    public static final Block WRATH_CRYSTAL_CLUSTER = cluster("wrath_crystal_cluster", "large");
    public static final Block BUDDING_WRATH_CRYSTAL = budding("budding_wrath_crystal", SMALL_WRATH_CRYSTAL_CLUSTER, MEDIUM_WRATH_CRYSTAL_CLUSTER, WRATH_CRYSTAL_CLUSTER);
    public static final Block WRATH_CRYSTAL_BLOCK = crystalBlock("wrath_crystal_block");
    public static final Block WRATH_CRYSTAL_SLAB = crystalSlab("wrath_crystal_slab");
    public static final Block WRATH_CRYSTAL_STAIRS = crystalStairs("wrath_crystal_stairs");

    public static final Block INCREMENTAL_CRYSTAL_LENS = lens("incremental_crystal_lens", LensEffects.INCREMENTAL);
    public static final Block LUNAR_CRYSTAL_LENS = lens("lunar_crystal_lens", LensEffects.LUNAR);
    public static final Block LIFE_CRYSTAL_LENS = lens("life_crystal_lens", LensEffects.LIFE);
    public static final Block WRATH_CRYSTAL_LENS = lens("wrath_crystal_lens", LensEffects.WRATH);
    public static final Block EMPTY_LENS = lens("empty_lens", LensEffects.EMPTY);

    public static final Block UPGRADING_PEDESTAL = add("upgrading_pedestal", new UpgradingPedestalBlock(copyOf(Blocks.COBBLESTONE).luminance(state -> state.get(UpgradingPedestalBlock.CHARGES) * 3)));
    public static final Block LUNAR_PEDESTAL = add("lunar_pedestal", new BaseBlock(copyOf(Blocks.COBBLESTONE)));

    public static void register() {
        ITEMS.forEach((id, item) -> Registry.register(Registry.ITEM, id, ITEMS.get(id)));
        BLOCKS.forEach((id, block) -> Registry.register(Registry.BLOCK, id, BLOCKS.get(id)));
    }
}
