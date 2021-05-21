package artifality.item;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class InvisibilityCapeItem extends BaseItem implements Trinket {

    public InvisibilityCapeItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void tick(PlayerEntity player, ItemStack stack) {
        if(player.isSneaking()) {

            if (!player.hasStatusEffect(StatusEffects.INVISIBILITY)) {

                player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10, 0, false, false));
            }else{
                if(player.getActiveStatusEffects().get(StatusEffects.INVISIBILITY).getDuration() == 1){

                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10, 0, false, false));
                }
            }
        }
    }

    @Override
    public boolean canWearInSlot(String group, String slot) {
        return (group.equals(SlotGroups.CHEST) && slot.equals(Slots.CAPE));
    }
}
