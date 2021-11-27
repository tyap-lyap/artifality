package artifality.list;

import java.awt.*;

public enum ArtifactRarity {
    COMMON("common", new Color(188, 188, 188)),
    RARE("rare", new Color(72, 255, 130)),
    LEGENDARY("legendary", new Color(255, 98, 114)),
    LUNAR("lunar", new Color(131, 98, 255));

    private final String name;
    private final Color color;

    ArtifactRarity(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
