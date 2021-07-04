package artifality.client;

import artifality.api.client.TwoModelsItemRegistry;
import artifality.block.ArtifalityBlocks;
import artifality.data.ArtifalityResources;
import artifality.item.ArtifalityItems;
import artifality.client.particle.ArtifalityParticles;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class ArtifalityClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if(FabricLoader.INSTANCE.isDevelopmentEnvironment()){
            ArtifalityResources.init();
        }
        ArtifalityParticles.register();

        TwoModelsItemRegistry.register(new Identifier("artifality:ukulele"), ArtifalityItems.UKULELE);
        TwoModelsItemRegistry.register(new Identifier("artifality:zeus_staff"), ArtifalityItems.ZEUS_STAFF);

        TwoModelsItemRegistry.register(new Identifier("artifality:balloon"), ArtifalityItems.BALLOON);

        TwoModelsItemRegistry.register(new Identifier("artifality:forest_staff"), ArtifalityItems.FOREST_STAFF);

        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.INCREMENTAL_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.LUNAR_CRYSTAL_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.CRYSTAL_HEART_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.INCREMENTAL_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.LUNAR_CRYSTAL_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.CRYSTAL_HEART_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.INCREMENTAL_LENS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.LUNAR_CRYSTAL_LENS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.CRYSTAL_HEART_LENS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.MINI_SOMIK, RenderLayer.getCutout());

        ArtifalityItems.getItems().forEach((id, item) -> {
            if(item instanceof TrinketRenderer trinketRenderer){
                TrinketRendererRegistry.registerRenderer(item, trinketRenderer);
            }
        });

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> TwoModelsItemRegistry.getEntries().forEach((id, item) ->
                out.accept(new ModelIdentifier(id + "_in_hand#inventory"))));
    }
}
