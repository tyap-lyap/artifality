package artifality.item;

import net.minecraft.item.ItemStack;

public class CustomEnchantedBookItem extends BaseItem{

    public CustomEnchantedBookItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
