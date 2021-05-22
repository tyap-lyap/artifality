package artifality.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RingOfRegenerationItem extends BaseItem {
    public RingOfRegenerationItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof PlayerEntity){
            if(!((PlayerEntity) entity).hasStatusEffect(StatusEffects.REGENERATION)){
                ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0, false, false));
            }
        }
    }
}
