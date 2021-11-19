package artifality.item.base;

import artifality.item.ArtifactSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.awt.*;

public class TieredArtifactItem extends TieredItem {
    private final ArtifactSettings artifactSettings;

    public TieredArtifactItem(Settings settings, ArtifactSettings artifactSettings) {
        super(settings);
        this.artifactSettings = artifactSettings;
    }

    @Override
    public Text getName(ItemStack stack) {
        Color color = artifactSettings.getRarity().getColor();
        return new TranslatableText(this.getTranslationKey(stack)).setStyle(Style.EMPTY.withColor(color.getRGB()));
    }
}
