package artifality.util;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class TrinketsUtils {

    protected TrinketsUtils(){}

    public static boolean containsTrinket(PlayerEntity player, Item item){
        for(ItemStack stack : getTrinketsAsArray(player)){
            if(stack.isOf(item)) return true;
        }
        return false;
    }

    public static ArrayList<ItemStack> getTrinketsAsArray(PlayerEntity player) {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        if (TrinketsApi.getTrinketComponent(player).isPresent()){
            TrinketsApi.getTrinketComponent(player).get().forEach((slotReference, itemStack) -> stacks.add(itemStack));
        }
        return stacks;
    }
}
