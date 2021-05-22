package artifality.client;

import artifality.api.client.item.TwoModelsItemRegistry;
import artifality.block.ArtifalityBlocks;
import artifality.event.ArtifalityEvents;
import artifality.item.ArtifalityItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class ArtifalityClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        ArtifalityEvents.registerClient();

        TwoModelsItemRegistry.register(new Identifier("artifality:ukulele"), ArtifalityItems.UKULELE);
        TwoModelsItemRegistry.register(new Identifier("artifality:zeus_wand"), ArtifalityItems.ZEUS_WAND);


        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.INCREMENTAL_CRYSTAL, RenderLayer.getCutout());

    }

}
