package artifality.item;

import artifality.item.base.BaseItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class CrystalHeartItem extends BaseItem {

    public CrystalHeartItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        TranslatableText name = new TranslatableText(this.getTranslationKey(stack));
        return name.formatted(Formatting.YELLOW);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
