package artifality.registry;

import artifality.ArtifalityMod;
import artifality.worldgen.feature.CrystalFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;

public class ArtifalityFeatures {
    public static final Feature<DefaultFeatureConfig> CRYSTAL_FEATURE = new CrystalFeature();
    public static final RegistryKey<ConfiguredFeature<?,?>> CRYSTAL_FEATURE_CONFIG = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, ArtifalityMod.id("crystals"));
    public static final RegistryKey<PlacedFeature> CRYSTAL_FEATURE_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, ArtifalityMod.id("crystals"));

    public static void init() {
        Registry.register(Registries.FEATURE, ArtifalityMod.id("crystals"), CRYSTAL_FEATURE);

        BiomeModifications.create(ArtifalityMod.id("features"))
                .add(ModificationPhase.ADDITIONS, ctx -> {
                    var entry = ctx.getBiomeRegistryEntry();
                    return !entry.isIn(BiomeTags.IS_NETHER) && !entry.isIn(BiomeTags.IS_END);
                }, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, CRYSTAL_FEATURE_PLACED));
    }
}
