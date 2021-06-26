package artifality.item;

import artifality.item.base.TierableItem;
import artifality.item.base.TierableTrinketItem;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

public class RegenRingItem extends TierableTrinketItem {

    public RegenRingItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        int tier = TierableItem.getCurrentTier(stack);

        if (entity.world.getTime() % 1200L == 0L){
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 400 + (tier - 1) * 100, tier - 1, false, false));
        }
    }

    @Override
    public String getDescription() {
        return "Gives the wearer regeneration\nonce per minute.";
    }
}
