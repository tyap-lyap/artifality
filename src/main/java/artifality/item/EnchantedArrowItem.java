package artifality.item;

import artifality.item.base.BaseItem;
import net.minecraft.item.ItemStack;

public class EnchantedArrowItem extends BaseItem {


    public EnchantedArrowItem(Settings settings, String name) {
        super(settings, "custom", name);
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public String getDescription() {
        return "Used to create a rebound effect\nthat gives protection against projectiles.";
    }
}
