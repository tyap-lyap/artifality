package artifality.item;

import artifality.list.ArtifactRarity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public class ArtifactSettings {
    private final FabricItemSettings settings;
    private ArtifactRarity rarity = ArtifactRarity.COMMON;
    private boolean isCrateLoot = true;

    public ArtifactSettings(){
        settings = new FabricItemSettings();
    }

    public ArtifactRarity getRarity() {
        return rarity;
    }

    public boolean isCrateLoot() {
        return isCrateLoot;
    }

    public FabricItemSettings getItemSettings() {
        return settings;
    }

    public ArtifactSettings setRarity(ArtifactRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public ArtifactSettings nonCrateItem() {
        this.isCrateLoot = false;
        return this;
    }

    public ArtifactSettings setMaxCount(int maxCount) {
        settings.maxCount(maxCount);
        return this;
    }

    public ArtifactSettings setMaxDamage(int maxDamage) {
        settings.maxDamage(maxDamage);
        return this;
    }
}
