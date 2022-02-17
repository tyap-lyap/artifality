package artifality.registry;

import static artifality.ArtifalityMod.locate;

import artifality.worldgen.feature.CrystalFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class ArtifalityFeatures {
    public static final Feature<DefaultFeatureConfig> CRYSTAL_FEATURE = new CrystalFeature();
    public static final ConfiguredFeature<?, ?> CRYSTAL_FEATURE_CONFIG = CRYSTAL_FEATURE
            .configure(new DefaultFeatureConfig());
    public static final PlacedFeature CRYSTAL_FEATURE_PLACED = new PlacedFeature(() -> CRYSTAL_FEATURE_CONFIG, List.of(PlacedFeatures.BOTTOM_TO_120_RANGE));

    public static void init() {
        Registry.register(Registry.FEATURE, locate("crystal_feature"), CRYSTAL_FEATURE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, locate("crystal_feature"), CRYSTAL_FEATURE_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, locate("crystal_feature"), CRYSTAL_FEATURE_PLACED);

        BiomeModifications.addFeature(ctx -> {
                    Biome.Category category = ctx.getBiome().getCategory();
                    return !category.equals(Biome.Category.NETHER) && !category.equals(Biome.Category.THEEND);
                },
                GenerationStep.Feature.UNDERGROUND_DECORATION,
                BuiltinRegistries.PLACED_FEATURE.getKey(CRYSTAL_FEATURE_PLACED).orElseThrow());
    }
}
