package artifality.registry;

import artifality.ArtifalityMod;
import artifality.worldgen.feature.CrystalFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.*;

public class ArtifalityConfiguredFeatures {
    public static final Feature<DefaultFeatureConfig> PATCH_CRYSTAL = new CrystalFeature();
    public static final ConfiguredFeature<?, ?> PATCH_CRYSTAL_CONFIG = PATCH_CRYSTAL
            .configure(new DefaultFeatureConfig())
            .decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT));

    public static void register(){

        Registry.register(Registry.FEATURE, ArtifalityMod.newId("patch_crystal"), PATCH_CRYSTAL);

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ArtifalityMod.newId("patch_crystal"), PATCH_CRYSTAL_CONFIG);

        if(BuiltinRegistries.CONFIGURED_FEATURE.getKey(PATCH_CRYSTAL_CONFIG).isPresent()){
            BiomeModifications.addFeature(ctx -> ctx.getBiome().getCategory() != Biome.Category.THEEND, GenerationStep.Feature.UNDERGROUND_DECORATION,
                    BuiltinRegistries.CONFIGURED_FEATURE.getKey(PATCH_CRYSTAL_CONFIG).get());
        }
    }
}
