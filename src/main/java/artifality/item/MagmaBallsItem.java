package artifality.item;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class MagmaBallsItem extends BaseTrinketItem {

    public MagmaBallsItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {

        if(entity instanceof PlayerEntity){
            if(!entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)){
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0, false, false));
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
