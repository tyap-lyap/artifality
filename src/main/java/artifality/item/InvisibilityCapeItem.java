package artifality.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InvisibilityCapeItem extends BaseItem{

    public InvisibilityCapeItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if(entity instanceof PlayerEntity){
            PlayerEntity player = ((PlayerEntity) entity);
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
    }

    @Override
    public String getDescription() {
        return "Makes the wearer invisible while sneaking,\nalso hides the worn armor.";
    }
}
