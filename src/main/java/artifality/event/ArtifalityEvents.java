package artifality.event;


import artifality.interfaces.IArtifalityItem;
import artifality.item.ArtifalityItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;


public class ArtifalityEvents {

    public static void register(){

        ServerEntityEvents.ENTITY_LOAD.register(((entity, world) -> ArtifalityItems.getItems().forEach(((id, item) -> ((IArtifalityItem) item).onEntityLoad(entity, world)))));

    }

    public static void registerClient(){

    }
}
