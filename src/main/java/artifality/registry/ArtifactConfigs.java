package artifality.registry;

import artifality.client.render.BalloonRenderer;
import artifality.item.ArtifactSettings;

public class ArtifactConfigs {

    public static final ArtifactSettings LUNAR_CRYSTAL_WAND = new ArtifactSettings()
            .maxCount(1)
            .tiered();

    public static final ArtifactSettings NATURE_STAFF = new ArtifactSettings()
            .maxCount(1)
            .tiered();

    public static final ArtifactSettings ZEUS_STAFF = new ArtifactSettings()
            .maxCount(1)
            .tiered();

    public static final ArtifactSettings INVISIBILITY_CAPE = new ArtifactSettings()
            .maxCount(1)
            .trinket()
            .tiered();

    public static final ArtifactSettings BALLOON = new ArtifactSettings()
            .maxDamage(128)
            .tiered()
            .trinket()
            .twoModeled()
            .renderer(new BalloonRenderer());

    public static final ArtifactSettings HAND_FAN = new ArtifactSettings()
            .maxCount(1)
            .tiered();

    public static final ArtifactSettings HAUNTING_SOUL = new ArtifactSettings()
            .maxCount(1)
            .tiered()
            .trinket();
}
