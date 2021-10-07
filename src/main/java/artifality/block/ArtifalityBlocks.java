package artifality.block;

import artifality.ArtifalityMod;
import artifality.block.base.BuddingCrystalBlock;
import artifality.block.base.CrystalBlock;
import artifality.block.base.LensBlock;
import artifality.item.UkuleleItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import ru.bclib.blocks.BaseBlock;
import ru.bclib.registry.BlockRegistry;

@SuppressWarnings("unused")
public class ArtifalityBlocks extends BlockRegistry {

    public static final Block SMALL_INCREMENTAL_CRYSTAL_CLUSTER = addCluster("small_incremental_crystal_cluster", "small");
    public static final Block MEDIUM_INCREMENTAL_CRYSTAL_CLUSTER = addCluster("medium_incremental_crystal_cluster", "medium");
    public static final Block INCREMENTAL_CRYSTAL_CLUSTER = addCluster("incremental_crystal_cluster", "large");
    public static final Block BUDDING_INCREMENTAL_CRYSTAL = addBudCrystal("budding_incremental_crystal", SMALL_INCREMENTAL_CRYSTAL_CLUSTER, MEDIUM_INCREMENTAL_CRYSTAL_CLUSTER, INCREMENTAL_CRYSTAL_CLUSTER);
    public static final Block INCREMENTAL_CRYSTAL_BLOCK = addLitBlock("incremental_crystal_block");

    public static final Block SMALL_LUNAR_CRYSTAL_CLUSTER = addCluster("small_lunar_crystal_cluster", "small");
    public static final Block MEDIUM_LUNAR_CRYSTAL_CLUSTER = addCluster("medium_lunar_crystal_cluster", "medium");
    public static final Block LUNAR_CRYSTAL_CLUSTER = addCluster("lunar_crystal_cluster", "large");
    public static final Block BUDDING_LUNAR_CRYSTAL = addBudCrystal("budding_lunar_crystal", SMALL_LUNAR_CRYSTAL_CLUSTER, MEDIUM_LUNAR_CRYSTAL_CLUSTER, LUNAR_CRYSTAL_CLUSTER);
    public static final Block LUNAR_CRYSTAL_BLOCK = addLitBlock("lunar_crystal_block");

    public static final Block SMALL_LIFE_CRYSTAL_CLUSTER = addCluster("small_life_crystal_cluster", "small");
    public static final Block MEDIUM_LIFE_CRYSTAL_CLUSTER = addCluster("medium_life_crystal_cluster", "medium");
    public static final Block LIFE_CRYSTAL_CLUSTER = addCluster("life_crystal_cluster", "large");
    public static final Block BUDDING_LIFE_CRYSTAL = addBudCrystal("budding_life_crystal", SMALL_LIFE_CRYSTAL_CLUSTER, MEDIUM_LIFE_CRYSTAL_CLUSTER, LIFE_CRYSTAL_CLUSTER);
    public static final Block LIFE_CRYSTAL_BLOCK = addLitBlock("life_crystal_block");

    public static final Block INCREMENTAL_CRYSTAL_LENS = addLens("incremental", (effect, player) -> player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration(), effect.getAmplifier() + 1, true, true)));

    public static final Block LUNAR_CRYSTAL_LENS = addLens("lunar", (effect, player) -> player.addStatusEffect(new StatusEffectInstance(
            UkuleleItem.POSITIVE_EFFECTS.get(player.world.random.nextInt(
                    UkuleleItem.POSITIVE_EFFECTS.size())), effect.getDuration(), player.world.random.nextInt(2), true, true)));

    public static final Block LIFE_CRYSTAL_LENS = addLens("life", (effect, playerEntity) -> {
        float health = playerEntity.getHealth();
        playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, effect.getDuration(), 4, true, true));
        playerEntity.setHealth(health);
    });

    public static final Block UPGRADING_PEDESTAL = add("upgrading_pedestal", new UpgradingPedestalBlock(copyOf(Blocks.COBBLESTONE).luminance(state -> state.get(UpgradingPedestalBlock.CHARGES) * 3)));
//    public static final Block LUNAR_PEDESTAL = addDummy("lunar_pedestal", new UpgradingPedestalBlock(copyOf(Blocks.COBBLESTONE)));

    private static BlockRegistry BLOCK_REGISTRY;

    private ArtifalityBlocks() {
        super(ArtifalityMod.ITEMS_ITEM_GROUP);
    }

    private static Block addLens(String type, LensBlock.LensEffect effect){
        return add(type + "_crystal_lens", new LensBlock(effect));
    }

    private static Block addBudCrystal(String name, Block small, Block medium, Block large){
        return add(name, new BuddingCrystalBlock(small, medium, large));
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
