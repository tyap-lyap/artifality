package artifality.registry;

import artifality.ArtifalityMod;
import artifality.block.UpgradingPedestalBlock;
import artifality.block.base.*;
import artifality.util.EffectsUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ArtifalityBlocks {
    public static final Map<Identifier, BlockItem> ITEMS = new LinkedHashMap<>();
    public static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    public static final Block INCREMENTAL_ORB = add("incremental_orb", new OrbBlock(copyOf(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));
    public static final Block LUNAR_ORB = add("lunar_orb", new OrbBlock(copyOf(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));
    public static final Block LIFE_ORB = add("life_orb", new OrbBlock(copyOf(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));
    public static final Block WRATH_ORB = add("wrath_orb", new OrbBlock(copyOf(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));

    public static final Block SMALL_INCREMENTAL_CRYSTAL_CLUSTER = addCluster("small_incremental_crystal_cluster", "small");
    public static final Block MEDIUM_INCREMENTAL_CRYSTAL_CLUSTER = addCluster("medium_incremental_crystal_cluster", "medium");
    public static final Block INCREMENTAL_CRYSTAL_CLUSTER = addCluster("incremental_crystal_cluster", "large");
    public static final Block BUDDING_INCREMENTAL_CRYSTAL = addBudCrystal("budding_incremental_crystal", SMALL_INCREMENTAL_CRYSTAL_CLUSTER, MEDIUM_INCREMENTAL_CRYSTAL_CLUSTER, INCREMENTAL_CRYSTAL_CLUSTER);
    public static final Block INCREMENTAL_CRYSTAL_BLOCK = addCrystalBlock("incremental_crystal_block");

    public static final Block SMALL_LUNAR_CRYSTAL_CLUSTER = addCluster("small_lunar_crystal_cluster", "small");
    public static final Block MEDIUM_LUNAR_CRYSTAL_CLUSTER = addCluster("medium_lunar_crystal_cluster", "medium");
    public static final Block LUNAR_CRYSTAL_CLUSTER = addCluster("lunar_crystal_cluster", "large");
    public static final Block BUDDING_LUNAR_CRYSTAL = addBudCrystal("budding_lunar_crystal", SMALL_LUNAR_CRYSTAL_CLUSTER, MEDIUM_LUNAR_CRYSTAL_CLUSTER, LUNAR_CRYSTAL_CLUSTER);
    public static final Block LUNAR_CRYSTAL_BLOCK = addCrystalBlock("lunar_crystal_block");

    public static final Block SMALL_LIFE_CRYSTAL_CLUSTER = addCluster("small_life_crystal_cluster", "small");
    public static final Block MEDIUM_LIFE_CRYSTAL_CLUSTER = addCluster("medium_life_crystal_cluster", "medium");
    public static final Block LIFE_CRYSTAL_CLUSTER = addCluster("life_crystal_cluster", "large");
    public static final Block BUDDING_LIFE_CRYSTAL = addBudCrystal("budding_life_crystal", SMALL_LIFE_CRYSTAL_CLUSTER, MEDIUM_LIFE_CRYSTAL_CLUSTER, LIFE_CRYSTAL_CLUSTER);
    public static final Block LIFE_CRYSTAL_BLOCK = addCrystalBlock("life_crystal_block");

    public static final Block SMALL_WRATH_CRYSTAL_CLUSTER = addCluster("small_wrath_crystal_cluster", "small");
    public static final Block MEDIUM_WRATH_CRYSTAL_CLUSTER = addCluster("medium_wrath_crystal_cluster", "medium");
    public static final Block WRATH_CRYSTAL_CLUSTER = addCluster("wrath_crystal_cluster", "large");
    public static final Block BUDDING_WRATH_CRYSTAL = addBudCrystal("budding_wrath_crystal", SMALL_WRATH_CRYSTAL_CLUSTER, MEDIUM_WRATH_CRYSTAL_CLUSTER, WRATH_CRYSTAL_CLUSTER);
    public static final Block WRATH_CRYSTAL_BLOCK = addCrystalBlock("wrath_crystal_block");

    public static final Block INCREMENTAL_CRYSTAL_LENS = addLens("incremental", (effect, player) -> player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration(), effect.getAmplifier() + 1, true, true)));

    public static final Block LUNAR_CRYSTAL_LENS = addLens("lunar", (effect, player) -> player.addStatusEffect(new StatusEffectInstance(
            EffectsUtils.getRandomPositive(), effect.getDuration(), player.world.random.nextInt(2), true, true)));

    public static final Block LIFE_CRYSTAL_LENS = addLens("life", (effect, player) -> {
        float health = player.getHealth();
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, effect.getDuration(), 4, true, true));
        player.setHealth(health);
    });

    public static final Block WRATH_CRYSTAL_LENS = addLens("wrath", (effect, player) -> {});

    public static final Block UPGRADING_PEDESTAL = add("upgrading_pedestal", new UpgradingPedestalBlock(copyOf(Blocks.COBBLESTONE).luminance(state -> state.get(UpgradingPedestalBlock.CHARGES) * 3)));
//    public static final Block LUNAR_PEDESTAL = addDummy("lunar_pedestal", new UpgradingPedestalBlock(copyOf(Blocks.COBBLESTONE)));

    private static Block addLens(String type, LensBlock.LensEffect effect){
        return add(type + "_crystal_lens", new LensBlock(effect));
    }

    private static Block addBudCrystal(String name, Block small, Block medium, Block large){
        return add(name, new BuddingCrystalBlock(small, medium, large));
    }

    private static Block addCluster(String name, String type){
        return add(name, new CrystalClusterBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).luminance(10), type));
    }

    private static Block addCrystalBlock(String name){
        return add(name, new CrystalBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));
    }

    private static FabricBlockSettings copyOf(Block block){
        return FabricBlockSettings.copyOf(block);
    }

    private static Block add(String name, Block block) {
        Item.Settings settings = new Item.Settings();
        return addBlockItem(name, block, new BlockItem(block, settings));
    }

    private static Block addBlockItem(String name, Block block, BlockItem item) {
        addBlock(name, block);
        if (item != null) {
            item.appendBlocks(Item.BLOCK_ITEMS, item);
            ITEMS.put(ArtifalityMod.newId(name), item);
        }
        return block;
    }

    private static Block addBlock(String name, Block block) {
        BLOCKS.put(ArtifalityMod.newId(name), block);
        return block;
    }

    public static void register() {
        ITEMS.forEach((id, item) -> Registry.register(Registry.ITEM, id, ITEMS.get(id)));
        BLOCKS.forEach((id, block) -> Registry.register(Registry.BLOCK, id, BLOCKS.get(id)));
    }
}
