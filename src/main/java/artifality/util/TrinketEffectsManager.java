package artifality.util;

import artifality.interfaces.ITierableItem;
import artifality.item.ArtifalityItems;
import artifality.item.TierableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class TrinketEffectsManager {

    private int ticks;

    private static final TrinketEffectsManager trinketEffectsManager = new TrinketEffectsManager();

    public static TrinketEffectsManager getInstance(){
        return trinketEffectsManager;
    }

    public TrinketEffectsManager(){
        this.ticks = 0;
    }

    public void tick(MinecraftServer server){

        if(ticks == 1200){
            activateEffectsPerMinute(server);
            ticks = 0;
        }

        ticks = ticks + 1;

    }

    public void activateEffectsPerMinute(MinecraftServer server){

        server.getWorlds().forEach((serverWorld -> serverWorld.getPlayers().forEach((player) -> {

            ArtifalityItems.getItems().forEach((id, item) -> {
                if(item instanceof ITierableItem){
                    for(int i = 0; i <= player.inventory.size(); i++){

                        ItemStack itemStack = player.inventory.getStack(i);

                        if(!itemStack.isEmpty() && itemStack.getItem().equals(item)){
                            ((ITierableItem) item).applyEffects(serverWorld, player, TierableItem.getCurrentTier(itemStack));
                        }

                    }
                }
            });

        })));
    }
}
