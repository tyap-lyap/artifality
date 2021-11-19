package artifality.item;

import artifality.enums.ArtifactRarity;

public class ArtifactSettings {
    private ArtifactRarity rarity = ArtifactRarity.COMMON;
    private float chance = 1;

    public ArtifactRarity getRarity() {
        return rarity;
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
}
