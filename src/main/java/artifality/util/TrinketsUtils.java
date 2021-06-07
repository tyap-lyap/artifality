package artifality.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;


public class TrinketsUtils {

    protected TrinketsUtils(){}

    public static boolean containsTrinket(PlayerEntity playerEntity, Item item){

        return playerEntity.getInventory().contains(item.getDefaultStack());

    }

//    public static boolean containsTrinket(PlayerEntity playerEntity, Item item){
//
//        for(ItemStack itemStack : getTrinketsAsArray(playerEntity)){
//
//            if(itemStack.getItem().equals(item)){
//
//                return true;
//            }
//
//        }
//        return false;
//
//    }

//    public static ArrayList<ItemStack> getTrinketsAsArray(PlayerEntity playerEntity) {
//        ArrayList<ItemStack> stacks = new ArrayList<>();
//
//        Inventory inventory = TrinketsApi.getTrinketsInventory(playerEntity);
//
//        for (int i = 0; i < inventory.size(); i++) {
//            ItemStack stack = inventory.getStack(i);
//
//            if (!stack.isEmpty() && stack.getItem() instanceof IArtifalityItem) {
//                stacks.add(stack);
//            }
//        }
//
//        return stacks;
//    }
}
