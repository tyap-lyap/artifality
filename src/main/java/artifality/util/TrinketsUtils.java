package artifality.util;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;


public class TrinketsUtils {

    protected TrinketsUtils(){}

    public static boolean containsItem(PlayerEntity playerEntity, Item item){

        return playerEntity.getInventory().contains(item.getDefaultStack());
    }

    public static boolean containsTrinket(PlayerEntity playerEntity, Item item){

        for(ItemStack itemStack : getTrinketsAsArray(playerEntity)){

            if(itemStack.getItem().equals(item)){

                return true;
            }

        }
        return false;

    }

    public static ArrayList<ItemStack> getTrinketsAsArray(PlayerEntity playerEntity) {
        ArrayList<ItemStack> stacks = new ArrayList<>();

        if (TrinketsApi.getTrinketComponent(playerEntity).isPresent()){
            TrinketsApi.getTrinketComponent(playerEntity).get().forEach((slotReference, itemStack) -> stacks.add(itemStack));
        }

        return stacks;
    }
}
