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
            switch (tier) {
                case 3 -> giveRegeneration(entity, 400, 2);
                case 2 -> giveRegeneration(entity, 400, 1);
                case 1 -> giveRegeneration(entity, 400, 0);
            }
        }
    }

    static void giveRegeneration(LivingEntity entity, int duration, int amplifier){
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, duration, amplifier, false, false));
    }

    @Override
    public String getDescription() {
        return "Gives the wearer regeneration\nonce per minute.";
    }
}
