package artifality.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TiersUtils {

    public static int getTier(ItemStack stack) {
        if(stack.getOrCreateNbt().getInt("artifactTier") >= 2) return stack.getOrCreateNbt().getInt("artifactTier");
        else return 1;
    }

    public static ItemStack withTier(Item item, int tier) {
        ItemStack itemStack = new ItemStack(item);
        if (tier >= 2) {
            itemStack.getOrCreateNbt().putInt("artifactTier", tier);
        }
        return itemStack;
    }
}
