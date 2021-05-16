package artifality.client;

import artifality.api.client.item.TwoModelsItemRegistry;
import artifality.event.ArtifalityEvents;
import artifality.item.ArtifalityItems;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

public class ArtifalityClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        ArtifalityEvents.registerClient();

        TwoModelsItemRegistry.register(new Identifier("artifality:ukulele"), ArtifalityItems.UKULELE);

    }

}
