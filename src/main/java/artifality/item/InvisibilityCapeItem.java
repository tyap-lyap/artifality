package artifality.item;

import artifality.item.base.BaseTrinketItem;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

public class InvisibilityCapeItem extends BaseTrinketItem {

    public InvisibilityCapeItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity.isSneaking()) {
            if (!entity.hasStatusEffect(StatusEffects.INVISIBILITY)) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10, 0, false, false));
            } else {
                if (entity.getActiveStatusEffects().get(StatusEffects.INVISIBILITY).getDuration() == 1) {
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10, 0, false, false));
                }
            }
        }
    }

    @Override
    public String getDescription()
    {
        return "Makes the wearer invisible\nwhile sneaking, also\nhides the worn armor.";
    }
}
