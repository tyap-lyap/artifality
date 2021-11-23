package artifality.client;

import artifality.ArtifalityMod;
import artifality.block.base.*;
import artifality.client.render.ElementalFeatureRenderer;
import artifality.enums.CrystalElement;
import artifality.registry.ArtifalityBlocks;
import artifality.util.TwoModelsItemRegistry;
import artifality.registry.ArtifalityItems;
import artifality.client.particle.ArtifalityParticles;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.DrownedEntityRenderer;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.util.ModelIdentifier;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ArtifalityClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ArtifalityParticles.register();

        TwoModelsItemRegistry.register(ArtifalityItems.UKULELE, ArtifalityItems.ZEUS_STAFF, ArtifalityItems.BALLOON,
                ArtifalityItems.FOREST_STAFF, ArtifalityItems.FLORAL_STAFF, ArtifalityItems.HARVEST_STAFF);

        ArtifalityItems.ITEMS.forEach((id, item) -> {
            if(item instanceof TrinketRenderer renderer) TrinketRendererRegistry.registerRenderer(item, renderer);
        });

        ArtifalityBlocks.BLOCKS.forEach((id, block) -> {
            if(block instanceof CrystalClusterBlock) BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
            if(block instanceof CrateBlock) BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
            if(block instanceof CrystalBlock) BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
            if(block instanceof CrystalSlabBlock) BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
            if(block instanceof LensBlock) BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
            if(block instanceof OrbBlock) BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
        });
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.EMPTY_LENS, RenderLayer.getCutout());

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            CrystalElement.ELEMENTS.forEach(element ->{
                out.accept(new ModelIdentifier("artifality:" + element.getName() + "_head_overlay#inventory"));
                out.accept(new ModelIdentifier("artifality:" + element.getName() + "_body_overlay#inventory"));
            });
            TwoModelsItemRegistry.ENTRIES.forEach((id, item) ->
                    out.accept(new ModelIdentifier(id + "_in_hand#inventory"))
            );
        });

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if(entityRenderer instanceof ZombieEntityRenderer renderer){
                registrationHelper.register(new ElementalFeatureRenderer<>(renderer));
            }
            if(entityRenderer instanceof DrownedEntityRenderer renderer){
                registrationHelper.register(new ElementalFeatureRenderer<>(renderer));
            }
            if(entityRenderer instanceof SkeletonEntityRenderer renderer){
                registrationHelper.register(new ElementalFeatureRenderer<>(renderer));
            }
        });

        // Thanks Juce! :)
        ResourceManagerHelper.registerBuiltinResourcePack(ArtifalityMod.newId("fancyclusters"), FabricLoader.getInstance().getModContainer("artifality").get(), ResourcePackActivationType.NORMAL);
    }
}
