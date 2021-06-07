package artifality.util;

import artifality.interfaces.ITierableItem;
import artifality.item.ArtifalityItems;
import artifality.item.TierableItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class TrinketEffectsManager {

    private int ticks;

    public TrinketEffectsManager(){
        this.ticks = 0;
    }

    public void tick(PlayerEntity player){

        if(ticks == 1200){
            activateEffectsPerMinute(player);
            ticks = 0;
        }

        ticks = ticks + 1;

    }

    public void activateEffectsPerMinute(PlayerEntity player){

        ArtifalityItems.getItems().forEach((id, item) -> {
            if(item instanceof ITierableItem){
                for (int i = 0; i <= player.getInventory().size(); i++){
                    ItemStack itemStack = player.getInventory().getStack(i);

                    if(!itemStack.isEmpty() && itemStack.getItem().equals(item)){
                        ((ITierableItem) item).applyEffects(player.world, player, TierableItem.getCurrentTier(itemStack));
                    }
                }
            }
        });
    }
}
