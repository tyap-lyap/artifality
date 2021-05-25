package artifality.event;


import artifality.util.TrinketEffectsManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ArtifalityEvents {

    public static void register(){

        ServerTickEvents.START_SERVER_TICK.register(server -> TrinketEffectsManager.getInstance().tick(server));

    }

    public static void registerClient(){


    }
}
