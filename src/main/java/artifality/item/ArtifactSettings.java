package artifality.item;

import artifality.list.ArtifactRarity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public class ArtifactSettings {
    private final FabricItemSettings settings;
    private ArtifactRarity rarity = ArtifactRarity.COMMON;
    private float chance = 1;

    public ArtifactSettings(){
        settings = new FabricItemSettings();
    }

    public ArtifactRarity getRarity() {
        return rarity;
    }

    public FabricItemSettings getItemSettings() {
        return settings;
    }

    public float getChance() {
        return chance;
    }

    public ArtifactSettings setRarity(ArtifactRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public ArtifactSettings setChance(float chance) {
        this.chance = chance;
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
