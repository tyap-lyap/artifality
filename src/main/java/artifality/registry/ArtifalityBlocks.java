package artifality.registry;

import artifality.ArtifalityMod;
import artifality.block.*;
import artifality.block.base.*;
import artifality.item.base.BaseBlockItem;
import artifality.list.ArtifactRarity;
import artifality.list.LensEffects;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import static net.minecraft.block.Blocks.*;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ArtifalityBlocks {
    public static final Map<Identifier, BaseBlockItem> ITEMS = new LinkedHashMap<>();
    public static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

//    public static final Block COMMON_CRATE = add("common_crate", new CrateBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.WOOD).luminance(7), ArtifactRarity.COMMON));
//    public static final Block RARE_CRATE = add("rare_crate", new CrateBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.STONE).luminance(7), ArtifactRarity.RARE));
//    public static final Block LEGENDARY_CRATE = add("legendary_crate", new CrateBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.STONE).luminance(7), ArtifactRarity.LEGENDARY));
//    public static final Block LUNAR_CRATE = add("lunar_crate", new CrateBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.STONE).luminance(7), ArtifactRarity.RARE));

    public static final Block SMALL_INCREMENTAL_CRYSTAL_CLUSTER = cluster("small_incremental_crystal_cluster", "small");
    public static final Block MEDIUM_INCREMENTAL_CRYSTAL_CLUSTER = cluster("medium_incremental_crystal_cluster", "medium");
    public static final Block INCREMENTAL_CRYSTAL_CLUSTER = cluster("incremental_crystal_cluster", "large");

    public static final Block INCREMENTAL_CRYSTAL_GEODE = geode("incremental_crystal_geode",
            SMALL_INCREMENTAL_CRYSTAL_CLUSTER,
            MEDIUM_INCREMENTAL_CRYSTAL_CLUSTER,
            INCREMENTAL_CRYSTAL_CLUSTER
    );

    public static final Block INCREMENTAL_CRYSTAL_BLOCK = crystalBlock("incremental_crystal_block");
    public static final Block INCREMENTAL_CRYSTAL_SLAB = crystalSlab("incremental_crystal_slab");
    public static final Block INCREMENTAL_CRYSTAL_STAIRS = crystalStairs("incremental_crystal_stairs");

    public static final Block SMALL_LUNAR_CRYSTAL_CLUSTER = cluster("small_lunar_crystal_cluster", "small");
    public static final Block MEDIUM_LUNAR_CRYSTAL_CLUSTER = cluster("medium_lunar_crystal_cluster", "medium");
    public static final Block LUNAR_CRYSTAL_CLUSTER = cluster("lunar_crystal_cluster", "large");

    public static final Block LUNAR_CRYSTAL_GEODE = geode("lunar_crystal_geode",
            SMALL_LUNAR_CRYSTAL_CLUSTER,
            MEDIUM_LUNAR_CRYSTAL_CLUSTER,
            LUNAR_CRYSTAL_CLUSTER
    );

    public static final Block LUNAR_CRYSTAL_BLOCK = crystalBlock("lunar_crystal_block");
    public static final Block LUNAR_CRYSTAL_SLAB = crystalSlab("lunar_crystal_slab");
    public static final Block LUNAR_CRYSTAL_STAIRS = crystalStairs("lunar_crystal_stairs");

    public static final Block SMALL_LIFE_CRYSTAL_CLUSTER = cluster("small_life_crystal_cluster", "small");
    public static final Block MEDIUM_LIFE_CRYSTAL_CLUSTER = cluster("medium_life_crystal_cluster", "medium");
    public static final Block LIFE_CRYSTAL_CLUSTER = cluster("life_crystal_cluster", "large");

    public static final Block LIFE_CRYSTAL_GEODE = geode("life_crystal_geode",
            SMALL_LIFE_CRYSTAL_CLUSTER,
            MEDIUM_LIFE_CRYSTAL_CLUSTER,
            LIFE_CRYSTAL_CLUSTER
    );

    public static final Block LIFE_CRYSTAL_BLOCK = crystalBlock("life_crystal_block");
    public static final Block LIFE_CRYSTAL_SLAB = crystalSlab("life_crystal_slab");
    public static final Block LIFE_CRYSTAL_STAIRS = crystalStairs("life_crystal_stairs");

//    public static final Block SMALL_WRATH_CRYSTAL_CLUSTER = cluster("small_wrath_crystal_cluster", "small");
//    public static final Block MEDIUM_WRATH_CRYSTAL_CLUSTER = cluster("medium_wrath_crystal_cluster", "medium");
//    public static final Block WRATH_CRYSTAL_CLUSTER = cluster("wrath_crystal_cluster", "large");
//
//    public static final Block WRATH_CRYSTAL_GEODE = geode("wrath_crystal_geode",
//            SMALL_WRATH_CRYSTAL_CLUSTER,
//            MEDIUM_WRATH_CRYSTAL_CLUSTER,
//            WRATH_CRYSTAL_CLUSTER
//    );
//
//    public static final Block WRATH_CRYSTAL_BLOCK = crystalBlock("wrath_crystal_block");
//    public static final Block WRATH_CRYSTAL_SLAB = crystalSlab("wrath_crystal_slab");
//    public static final Block WRATH_CRYSTAL_STAIRS = crystalStairs("wrath_crystal_stairs");

    public static final Block INCREMENTAL_CRYSTAL_LENS = lens("incremental_crystal_lens", LensEffects.INCREMENTAL);
    public static final Block LUNAR_CRYSTAL_LENS = lens("lunar_crystal_lens", LensEffects.LUNAR);
    public static final Block LIFE_CRYSTAL_LENS = lens("life_crystal_lens", LensEffects.LIFE);
//    public static final Block WRATH_CRYSTAL_LENS = lens("wrath_crystal_lens", LensEffects.WRATH);
    public static final Block EMPTY_LENS = lens("empty_lens", LensEffects.EMPTY);

    public static final Block LUNASTONE = add("lunastone", new BaseBlock(copyOf(Blocks.STONE)));
    public static final Block LUNAR_PORTAL = add("lunar_portal", new LunarPortalBlock(copyOf(Blocks.STONE)));
    public static final Block LUNAR_TRADING_PEDESTAL = add("lunar_trading_pedestal", new TradingPedestalBlock(copyOf(Blocks.STONE).nonOpaque().notSolid()));

    public static final Block UPGRADING_PEDESTAL = add("upgrading_pedestal", new UpgradingPedestalBlock(copyOf(COBBLESTONE).luminance(state -> state.get(UpgradingPedestalBlock.CHARGES) * 3)));
//    public static final Block LUNAR_PEDESTAL = add("lunar_pedestal", new BaseBlock(copyOf(COBBLESTONE)));

    public static void init() {
        ITEMS.forEach((id, item) -> Registry.register(Registries.ITEM, id, ITEMS.get(id)));
        BLOCKS.forEach((id, block) -> Registry.register(Registries.BLOCK, id, BLOCKS.get(id)));
    }

    public static Block lens(String name, LensEffects.LensEffect effect) {
        return add(name, new LensBlock(effect));
    }

    public static Block geode(String name, Block small, Block medium, Block large) {
        return add(name, new CrystalGeodeBlock(small, medium, large));
    }

    public static Block cluster(String name, String type) {
        return add(name, new CrystalClusterBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).luminance(10), type));
    }

    public static Block crystalBlock(String name) {
        return add(name, new CrystalBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));
    }

    public static Block crystalSlab(String name) {
        return add(name, new CrystalSlabBlock(copyOf(Blocks.COBBLESTONE_SLAB).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));
    }

    public static Block crystalStairs(String name) {
        return add(name, new CrystalStairsBlock(copyOf(Blocks.COBBLESTONE_STAIRS).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));
    }

    public static FabricBlockSettings copyOf(Block block) {
        return FabricBlockSettings.copyOf(block);
    }

    public static Block add(String name, Block block) {
        Item.Settings settings = new Item.Settings();
        return addBlockItem(name, block, new BaseBlockItem(block, settings));
    }

    public static Block addBlockItem(String name, Block block, BaseBlockItem item) {
        addBlock(name, block);
        if (item != null) {
            item.appendBlocks(Item.BLOCK_ITEMS, item);
            ITEMS.put(ArtifalityMod.id(name), item);
        }
        return block;
    }

    public static Block addBlock(String name, Block block) {
        BLOCKS.put(ArtifalityMod.id(name), block);
        return block;
    }
}
