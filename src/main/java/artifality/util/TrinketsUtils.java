package artifality.util;

import artifality.interfaces.IArtifalityItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class TrinketsUtils {

    protected TrinketsUtils(){

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

        Inventory inventory = TrinketsApi.getTrinketsInventory(playerEntity);

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);

            if (!stack.isEmpty() && stack.getItem() instanceof IArtifalityItem) {
                stacks.add(stack);
            }
        }

        return stacks;
    }
}
