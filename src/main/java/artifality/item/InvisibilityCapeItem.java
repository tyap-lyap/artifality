package artifality.item;

import artifality.item.base.ArtifactItem;
import artifality.util.EffectsUtils;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

public class InvisibilityCapeItem extends ArtifactItem {

    public InvisibilityCapeItem(Settings settings, ArtifactSettings artifactSettings) {
        super(settings, artifactSettings);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!entity.isSneaking()) return;
        EffectsUtils.ticking(entity, StatusEffects.INVISIBILITY);
    }
}
