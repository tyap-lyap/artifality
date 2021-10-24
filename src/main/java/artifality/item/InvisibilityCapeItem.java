package artifality.item;

import artifality.item.base.BaseTrinketItem;
import artifality.util.EffectsUtils;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

public class InvisibilityCapeItem extends BaseTrinketItem {

    public InvisibilityCapeItem(Settings settings) {
        super(settings);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!entity.isSneaking()) return;
        EffectsUtils.ticking(entity, StatusEffects.INVISIBILITY);
    }
}
