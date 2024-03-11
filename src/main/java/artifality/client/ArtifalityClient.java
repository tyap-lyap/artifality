package artifality.client;

import artifality.ArtifalityMod;
import artifality.block.base.*;
import artifality.registry.ArtifalityParticles;
import artifality.client.particle.LunarChainParticle;
import artifality.client.render.TradingPedestalHud;
import artifality.client.render.TradingPedestalRenderer;
import artifality.item.base.ArtifactItem;
import artifality.registry.ArtifalityBlockEntities;
import artifality.registry.ArtifalityBlocks;
import artifality.api.TwoModeledItems;
import artifality.registry.ArtifalityItems;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.util.ModelIdentifier;

public class ArtifalityClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ArtifalityParticles.LUNAR_CHAIN, LunarChainParticle.Factory::new);

        BlockEntityRendererFactories.register(ArtifalityBlockEntities.TRADING_PEDESTAL, ctx -> new TradingPedestalRenderer<>());

        TradingPedestalHud.register();

        ArtifalityItems.ITEMS.forEach((id, item) -> {
            if(item instanceof ArtifactItem artifact) {
                if (artifact.artifactSettings.renderer != null) {
                    TrinketRendererRegistry.registerRenderer(item, artifact.artifactSettings.renderer);
                }
                if(artifact.artifactSettings.hasTwoModels) {
                    TwoModeledItems.register(item);
                }
            }
        });

        BlockRenderLayerMap map = BlockRenderLayerMap.INSTANCE;

        ArtifalityBlocks.BLOCKS.forEach((id, block) -> {
            if(block instanceof CrystalClusterBlock) map.putBlock(block, RenderLayer.getCutout());
            if(block instanceof CrystalBlock) map.putBlock(block, RenderLayer.getTranslucent());
            if(block instanceof CrystalSlabBlock) map.putBlock(block, RenderLayer.getTranslucent());
            if(block instanceof CrystalStairsBlock) map.putBlock(block, RenderLayer.getTranslucent());
            if(block instanceof LensBlock) map.putBlock(block, RenderLayer.getTranslucent());
            if(block instanceof OrbBlock) map.putBlock(block, RenderLayer.getTranslucent());
        });
        map.putBlock(ArtifalityBlocks.EMPTY_LENS, RenderLayer.getCutout());

        /*
        registers the lunar enchanted book model
        to be used to replace the default enchanted
        book model if a book has a lunar enchantment
        see artifality.mixin.client.ItemRendererMixin
        */
        ModelLoadingPlugin.register(pluginContext -> {
            pluginContext.addModels(new ModelIdentifier(ArtifalityMod.id("lunar_enchanted_book"), "inventory"));
        });
    }
}
