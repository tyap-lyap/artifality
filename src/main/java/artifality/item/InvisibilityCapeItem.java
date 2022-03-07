package artifality.item;

import artifality.item.base.ArtifactItem;
import artifality.util.EffectsUtils;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

public class InvisibilityCapeItem extends ArtifactItem implements Trinket {

    public InvisibilityCapeItem(ArtifactSettings settings) {
        super(settings);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!entity.isSneaking()) return;
        EffectsUtils.ticking(entity, StatusEffects.INVISIBILITY);
    }
}
