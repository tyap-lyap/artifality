package artifality.enums;

import java.awt.*;

public enum ArtifactRarity {
    COMMON(new Color(188, 188, 188)),
    RARE(new Color(98, 255, 148)),
    LEGENDARY(new Color(255, 98, 114)),
    LUNAR(new Color(131, 98, 255));

    private final Color color;

    ArtifactRarity(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
