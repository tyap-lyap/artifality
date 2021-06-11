package artifality.event;

import artifality.api.client.item.TwoModelsItemRegistry;
import artifality.interfaces.ITrinketEffectsManager;
import artifality.item.ArtifalityItems;
import artifality.item.base.BaseItem;
import artifality.util.TrinketEffectsManager;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.client.util.ModelIdentifier;

public class ArtifalityEvents {

    public static void register(){

        ServerEntityEvents.ENTITY_LOAD.register(((entity, world) -> ArtifalityItems.getItems().forEach(((id, item) -> ((BaseItem) item).onEntityLoad(entity, world)))));

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if(entity instanceof ITrinketEffectsManager){
                ((ITrinketEffectsManager) entity).setTrinketEffectsManager(new TrinketEffectsManager());
            }
        });
    }

    public static void registerClient(){

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> TwoModelsItemRegistry.getEntries().forEach((id, item) ->
                out.accept(new ModelIdentifier(id + "_in_hand#inventory"))));
    }
}
