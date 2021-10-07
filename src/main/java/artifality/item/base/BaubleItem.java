package artifality.item.base;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class BaubleItem extends ArtifalityBaseItem {

    private final boolean hasGlint;

    public BaubleItem(Settings settings, boolean hasGlint) {
        super(settings);
        this.hasGlint = hasGlint;
    }

    @Override
    public Text getName(ItemStack stack) {
        TranslatableText name = new TranslatableText(this.getTranslationKey(stack));
        return name.formatted(Formatting.YELLOW);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return hasGlint;
    }
}
