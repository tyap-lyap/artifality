package artifality.item;

import artifality.list.ArtifactRarity;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public class ArtifactSettings {
    public final FabricItemSettings fabricItemSettings;
    public final Parameters parameters = new Parameters();

    public ArtifactSettings() {
        fabricItemSettings = new FabricItemSettings();
    }

    public ArtifactSettings rarity(ArtifactRarity rarity) {
        this.parameters.rarity = rarity;
        return this;
    }

    public ArtifactSettings nonCrateItem() {
        this.parameters.isCrateLoot = false;
        return this;
    }

    public ArtifactSettings twoModeled() {
        this.parameters.hasTwoModels = true;
        return this;
    }

    public ArtifactSettings trinket() {
        this.parameters.isTrinket = true;
        return this;
    }

    public ArtifactSettings tiered() {
        this.parameters.hasTiers = true;
        return this;
    }

    public ArtifactSettings maxCount(int maxCount) {
        this.fabricItemSettings.maxCount(maxCount);
        return this;
    }

    public ArtifactSettings maxDamage(int maxDamage) {
        this.fabricItemSettings.maxDamage(maxDamage);
        return this;
    }

    public ArtifactSettings renderer(TrinketRenderer renderer) {
        this.parameters.renderer = renderer;
        return this;
    }

    public static class Parameters {
        public ArtifactRarity rarity = ArtifactRarity.COMMON;
        public TrinketRenderer renderer;
        public boolean isCrateLoot = true;
        public boolean isTrinket = false;
        public boolean hasTiers = false;
        public boolean hasTwoModels = false;
    }
}
