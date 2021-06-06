package artifality.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MagmaBallsItem extends BaseItem {

    public MagmaBallsItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if(entity instanceof PlayerEntity){
            if(!((PlayerEntity) entity).hasStatusEffect(StatusEffects.FIRE_RESISTANCE)){
                ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0, false, false));
            }
        }
    }

    @Override
    public String getDescription() {
        return "Gives the wearer infinite\nfire resistance.";
    }

    @Override
    public boolean isWip() {
        return true;
    }
}
