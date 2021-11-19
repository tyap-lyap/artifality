package artifality.item;

import artifality.item.base.BaseItem;
import net.minecraft.item.ItemStack;

public class CrystalHeartItem extends BaseItem {

    public CrystalHeartItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
