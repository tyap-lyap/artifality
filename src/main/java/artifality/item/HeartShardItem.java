package artifality.item;

import artifality.item.base.BaseItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HeartShardItem extends BaseItem {
    public HeartShardItem(Item.Settings settings, String name) {
        super(settings, "custom", name);
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public String getDescription() {
        return "Used to create full heart.";
    }
}
