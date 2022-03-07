package artifality.client;

import artifality.ArtifalityMod;
import artifality.block.CrateBlock;
import artifality.block.base.*;
import artifality.item.base.ArtifactItem;
import artifality.registry.ArtifalityBlocks;
import artifality.api.TwoModelsItemRegistry;
import artifality.registry.ArtifalityItems;
import artifality.client.particle.ArtifalityParticles;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.RenderLayer;

public class ArtifalityClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ArtifalityParticles.register();

        ArtifalityItems.ITEMS.forEach((id, item) -> {
            if(item instanceof ArtifactItem artifact) {
                if (artifact.config.renderer != null) {
                    TrinketRendererRegistry.registerRenderer(item, artifact.config.renderer);
                }
                if(artifact.config.hasTwoModels) {
                    TwoModelsItemRegistry.register(item);
                }
            }
        });

        BlockRenderLayerMap map = BlockRenderLayerMap.INSTANCE;

        ArtifalityBlocks.BLOCKS.forEach((id, block) -> {
            if(block instanceof CrystalClusterBlock) map.putBlock(block, RenderLayer.getCutout());
            if(block instanceof CrateBlock) map.putBlock(block, RenderLayer.getCutout());
            if(block instanceof CrystalBlock) map.putBlock(block, RenderLayer.getTranslucent());
            if(block instanceof CrystalSlabBlock) map.putBlock(block, RenderLayer.getTranslucent());
            if(block instanceof CrystalStairsBlock) map.putBlock(block, RenderLayer.getTranslucent());
            if(block instanceof LensBlock) map.putBlock(block, RenderLayer.getTranslucent());
            if(block instanceof OrbBlock) map.putBlock(block, RenderLayer.getTranslucent());
        });
        map.putBlock(ArtifalityBlocks.EMPTY_LENS, RenderLayer.getCutout());

//        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
//            if(entityRenderer instanceof ZombieEntityRenderer renderer){
//                registrationHelper.register(new ElementalFeatureRenderer<>(renderer));
//            }
//            if(entityRenderer instanceof DrownedEntityRenderer renderer){
//                registrationHelper.register(new ElementalFeatureRenderer<>(renderer));
//            }
//            if(entityRenderer instanceof SkeletonEntityRenderer renderer){
//                registrationHelper.register(new ElementalFeatureRenderer<>(renderer));
//            }
//        });

        // Thanks Juce! :)
        FabricLoader.getInstance().getModContainer(ArtifalityMod.MOD_ID).ifPresent(artifality ->
                ResourceManagerHelper.registerBuiltinResourcePack(ArtifalityMod.locate("fancyclusters"), artifality, ResourcePackActivationType.NORMAL));
    }
}
