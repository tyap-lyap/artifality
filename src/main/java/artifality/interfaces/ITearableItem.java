package artifality.interfaces;

import net.minecraft.item.ItemStack;

public interface ITearableItem {

    default int getCurrentTier(ItemStack stack){
        return 1;
    }
}
