package artifality.client;

import artifality.api.client.TwoModelsItemRegistry;
import artifality.block.ArtifalityBlocks;
import artifality.data.ArtifalityResources;
import artifality.item.ArtifalityItems;
import artifality.client.particle.ArtifalityParticles;
import artifality.util.TrinketsUtils;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.player.PlayerEntity;
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
        TwoModelsItemRegistry.register(new Identifier("artifality:aquatic_balloon"), ArtifalityItems.AQUATIC_BALLOON);
        TwoModelsItemRegistry.register(new Identifier("artifality:cottoncandy_balloon"), ArtifalityItems.COTTONCANDY_BALLOON);
        TwoModelsItemRegistry.register(new Identifier("artifality:galactic_balloon"), ArtifalityItems.GALACTIC_BALLOON);
        TwoModelsItemRegistry.register(new Identifier("artifality:pancake_balloon"), ArtifalityItems.PANCAKE_BALLOON);
        TwoModelsItemRegistry.register(new Identifier("artifality:prismatic_balloon"), ArtifalityItems.PRISMATIC_BALLOON);
        TwoModelsItemRegistry.register(new Identifier("artifality:sherbet_balloon"), ArtifalityItems.SHERBET_BALLOON);
        TwoModelsItemRegistry.register(new Identifier("artifality:sunset_balloon"), ArtifalityItems.SUNSET_BALLOON);

        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.INCREMENTAL_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.LUNAR_CRYSTAL_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.CRYSTAL_HEART_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.INCREMENTAL_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.LUNAR_CRYSTAL_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.CRYSTAL_HEART_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.INCREMENTAL_LENS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.LUNAR_CRYSTAL_LENS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ArtifalityBlocks.CRYSTAL_HEART_LENS, RenderLayer.getTranslucent());

        TrinketRendererRegistry.registerRenderer(ArtifalityItems.UKULELE, (stack, slotReference, contextModel, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch) -> {
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

            if(entity.isInvisible() && TrinketsUtils.containsTrinket((PlayerEntity) entity, ArtifalityItems.INVISIBILITY_CAPE)) return;
            matrices.push();
            matrices.translate(0D, 0.17D, 0.17D);
            matrices.scale(0.8F, 0.8F, 0.8F);

            itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier("artifality:ukulele_in_hand#inventory")));
            matrices.pop();
        });

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> TwoModelsItemRegistry.getEntries().forEach((id, item) ->
                out.accept(new ModelIdentifier(id + "_in_hand#inventory"))));
    }
}
