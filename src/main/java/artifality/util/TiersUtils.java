package artifality.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TiersUtils {

    /**
     * Returns a tier level of an item
     *
     * @param stack item stack to get tier out of
     * @return tier
     */

    public static int getTier(ItemStack stack) {
        if(stack.getOrCreateNbt().getInt("artifactTier") >= 2) return stack.getOrCreateNbt().getInt("artifactTier");
        else return 1;
    }

    /**
     * Creates an ItemStack with an
     * integer tag 'artifactTier'
     * @param item item to create stack of
     * @param tier tier level
     * @return ItemStack with a tier tag
     */
    public static ItemStack withTier(Item item, int tier) {
        ItemStack itemStack = new ItemStack(item);
        if (tier >= 2) {
            itemStack.getOrCreateNbt().putInt("artifactTier", tier);
        }
        return itemStack;
    }

}
