package artifality.registry;

import static artifality.ArtifalityMod.newId;

import artifality.worldgen.feature.CrateFeature;
import artifality.worldgen.feature.CrystalFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.*;

public class ArtifalityFeatures {
    public static final Feature<DefaultFeatureConfig> CRYSTAL_FEATURE = new CrystalFeature();
    public static final ConfiguredFeature<?, ?> CRYSTAL_FEATURE_CONFIG = CRYSTAL_FEATURE
            .configure(new DefaultFeatureConfig())
            .decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT));

    public static final Feature<DefaultFeatureConfig> CRATE_FEATURE = new CrateFeature();
    public static final ConfiguredFeature<?, ?> CRATE_FEATURE_CONFIG = CRYSTAL_FEATURE
            .configure(new DefaultFeatureConfig())
            .decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT));

    public static void init(){
        Registry.register(Registry.FEATURE, newId("crystal_feature"), CRYSTAL_FEATURE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, newId("crystal_feature"), CRYSTAL_FEATURE_CONFIG);

        Registry.register(Registry.FEATURE, newId("crate_feature"), CRATE_FEATURE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, newId("crate_feature"), CRATE_FEATURE_CONFIG);

        var crystal = BuiltinRegistries.CONFIGURED_FEATURE.getKey(CRYSTAL_FEATURE_CONFIG);
        crystal.ifPresent(key -> BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() != Biome.Category.THEEND, GenerationStep.Feature.UNDERGROUND_DECORATION,
                key));

        var crate = BuiltinRegistries.CONFIGURED_FEATURE.getKey(CRATE_FEATURE_CONFIG);
        crate.ifPresent(key -> BiomeModifications.addFeature(ctx -> true, GenerationStep.Feature.UNDERGROUND_DECORATION,
                key));
    }
}
