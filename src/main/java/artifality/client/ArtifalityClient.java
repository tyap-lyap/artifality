package artifality.client;

import artifality.api.client.item.TwoModelsItemRegistry;
import artifality.block.ArtifalityBlocks;
import artifality.data.ArtifalityResources;
import artifality.event.ArtifalityEvents;
import artifality.item.ArtifalityItems;
import artifality.particle.ArtifalityParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class ArtifalityClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        ArtifalityResources.init();
        ArtifalityEvents.registerClient();
        ArtifalityParticles.register();

        TwoModelsItemRegistry.register(new Identifier("artifality:ukulele"), ArtifalityItems.UKULELE);
        TwoModelsItemRegistry.register(new Identifier("artifality:zeus_wand"), ArtifalityItems.ZEUS_WAND);

        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.INCREMENTAL_CRYSTAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.INCREMENTAL_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.LUNAR_CRYSTAL_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.INCREMENTAL_LENS, RenderLayer.getTranslucent());

    }

}
