package artifality.item.base;

import artifality.extension.Artifact;
import artifality.item.ArtifactSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.awt.*;

public class ArtifactItem extends BaseTrinketItem implements Artifact {
    private final ArtifactSettings settings;

    public ArtifactItem(ArtifactSettings settings) {
        super(settings.getItemSettings());
        this.settings = settings;
    }

    @Override
    public ArtifactSettings getSettings() {
        return settings;
    }

    @Override
    public Text getName(ItemStack stack) {
        Color color = settings.getRarity().getColor();
        return new TranslatableText(this.getTranslationKey(stack)).setStyle(Style.EMPTY.withColor(color.getRGB()));
    }
}
