package artifality.util;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.Optional;

public class TrinketsUtils {

    public static boolean hasTrinket(PlayerEntity player, Item item) {
        for(ItemStack stack : getTrinketsArray(player)) {
            if(stack.isOf(item)) return true;
        }
        return false;
    }

    public static ItemStack getTrinket(PlayerEntity player, Item item) {
        for(ItemStack stack : getTrinketsArray(player)) {
            if(stack.isOf(item)) return stack;
        }
        return ItemStack.EMPTY;
    }

    public static ArrayList<ItemStack> getTrinketsArray(PlayerEntity player) {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(player);
        if(component.isPresent()) {
            for(Pair<SlotReference, ItemStack> pair : component.get().getAllEquipped()) {
                stacks.add(pair.getRight());
            }
        }
        return stacks;
    }
}
