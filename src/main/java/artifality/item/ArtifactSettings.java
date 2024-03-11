package artifality.item;

import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public class ArtifactSettings {
    public final FabricItemSettings fabricItemSettings;
    public TrinketRenderer renderer;
    public boolean isCrateLoot = true;
    public boolean isTrinket = false;
    public boolean hasTiers = false;
    public boolean hasTwoModels = false;
    public boolean hasGlint = false;


    public ArtifactSettings() {
        fabricItemSettings = new FabricItemSettings();
    }

    public ArtifactSettings nonCrateItem() {
        this.isCrateLoot = false;
        return this;
    }

    public ArtifactSettings twoModeled() {
        this.hasTwoModels = true;
        return this;
    }

    public ArtifactSettings hasGlint() {
        this.hasGlint = true;
        return this;
    }

    public ArtifactSettings trinket() {
        this.isTrinket = true;
        return this;
    }

    public ArtifactSettings tiered() {
        this.hasTiers = true;
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
        this.renderer = renderer;
        return this;
    }
}
