package artifality.event;


import artifality.item.ArtifalityItems;
import artifality.item.TierableItem;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Hand;

public class ArtifalityEvents {

    public static void register(){

        ServerEntityEvents.ENTITY_LOAD.register(((entity, world) -> {
            if(entity instanceof ZombieEntity){
                if(world.random.nextFloat() > 0.75F){
                    ((ZombieEntity) entity).setStackInHand(Hand.MAIN_HAND, TierableItem.withTier(ArtifalityItems.ZEUS_WAND, 1));
                }
            }
        }));

    }

    public static void registerClient(){

    }
}
