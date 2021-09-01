package artifality.client;

import artifality.api.client.TwoModelsItemRegistry;
import artifality.client.render.CosmeticFeatureRenderer;
import artifality.item.ArtifalityItems;
import artifality.client.particle.ArtifalityParticles;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;

public class ArtifalityClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ArtifalityParticles.register();
//        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
//            if(entityType.equals(EntityType.PLAYER))registrationHelper.register(new CosmeticFeatureRenderer((PlayerEntityRenderer)entityRenderer));
//        });

        TwoModelsItemRegistry.register(new Item[]{ArtifalityItems.UKULELE, ArtifalityItems.ZEUS_STAFF,
                ArtifalityItems.BALLOON, ArtifalityItems.FOREST_STAFF});

        ArtifalityItems.getRegisteredItems().forEach((id, items) -> items.forEach(item -> {
            if(item instanceof TrinketRenderer trinketRenderer) TrinketRendererRegistry.registerRenderer(item, trinketRenderer);
        }));

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> TwoModelsItemRegistry.getEntries().forEach((id, item) ->
                out.accept(new ModelIdentifier(id + "_in_hand#inventory"))));
    }
}
