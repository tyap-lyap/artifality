package artifality.enums;

import java.awt.*;

public enum ArtifactRarity {
    COMMON(new Color(255, 255, 255), new Color(85, 85, 85)),
    RARE(new Color(210, 255, 85), new Color(76, 255, 169)),
    LEGENDARY(new Color(255, 72, 168), new Color(255, 123, 63)),
    LUNAR(new Color(67, 186, 255), new Color(206, 67, 255));

    private final Color first;
    private final Color last;

    ArtifactRarity(Color first, Color last) {
        this.first = first;
        this.last = last;
    }

    public Color getFirstColor() {
        return first;
    }

    public Color getLastColor() {
        return last;
    }
}
