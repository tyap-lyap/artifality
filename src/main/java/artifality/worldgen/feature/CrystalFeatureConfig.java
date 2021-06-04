package artifality.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;

public class CrystalFeatureConfig implements FeatureConfig {
    public static final Codec<CrystalFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("patch_size").forGetter(CrystalFeatureConfig::getPatchSize)
    ).apply(instance, CrystalFeatureConfig::new));

    private final int patchSize;

    public CrystalFeatureConfig(int patchSize) {
        this.patchSize = patchSize;
    }

    public int getPatchSize() {
        return patchSize;
    }
}
