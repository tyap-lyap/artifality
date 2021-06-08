package artifality.event;


import artifality.interfaces.IArtifalityItem;
import artifality.interfaces.ITrinketEffectsManager;
import artifality.item.ArtifalityItems;
import artifality.util.TrinketEffectsManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.player.PlayerEntity;


public class ArtifalityEvents {

    public static void register(){

        ServerEntityEvents.ENTITY_LOAD.register(((entity, world) -> ArtifalityItems.getItems().forEach(((id, item) -> ((IArtifalityItem) item).onEntityLoad(entity, world)))));

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if(entity instanceof PlayerEntity){
                if(entity instanceof ITrinketEffectsManager){
                    ((ITrinketEffectsManager) entity).setTrinketEffectsManager(new TrinketEffectsManager());
                }
            }
        });
    }

    public static void registerClient(){

    }
}
