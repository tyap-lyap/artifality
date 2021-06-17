package artifality.item;

import artifality.item.base.BaseTrinketItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Hand;

public class UkuleleItem extends BaseTrinketItem {

    public UkuleleItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void onZombieInit(Entity entity) {
        if(entity instanceof ZombieEntity && ((ZombieEntity) entity).getStackInHand(Hand.MAIN_HAND).isEmpty()){
            if(entity.world.random.nextFloat() > 0.9F){
                ((ZombieEntity) entity).setStackInHand(Hand.MAIN_HAND, getDefaultStack());
            }
        }
    }
}
