package artifality.util;

import artifality.item.base.TierableItem;
import artifality.item.base.TierableTrinketItem;
import net.minecraft.entity.player.PlayerEntity;

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

        TrinketsUtils.getTrinketsAsArray(player).forEach(itemStack -> {
            if(itemStack.getItem() instanceof TierableTrinketItem item){
                item.applyEffects(player.world, player, TierableItem.getCurrentTier(itemStack));
            }
        });
    }
}
