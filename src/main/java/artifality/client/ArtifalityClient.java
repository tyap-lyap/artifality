package artifality.client;

import artifality.ArtifalityMod;
import artifality.util.TwoModelsItemRegistry;
import artifality.item.ArtifalityItems;
import artifality.client.particle.ArtifalityParticles;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ArtifalityClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ArtifalityParticles.register();

        TwoModelsItemRegistry.register(new Item[]{ArtifalityItems.UKULELE, ArtifalityItems.ZEUS_STAFF, ArtifalityItems.BALLOON,
                ArtifalityItems.FOREST_STAFF, ArtifalityItems.FLORAL_STAFF, ArtifalityItems.HARVEST_STAFF});

        ArtifalityItems.getRegisteredItems().forEach((id, items) -> items.forEach(item -> {
            if(item instanceof TrinketRenderer trinketRenderer) TrinketRendererRegistry.registerRenderer(item, trinketRenderer);
        }));

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> TwoModelsItemRegistry.getEntries().forEach((id, item) ->
                out.accept(new ModelIdentifier(id + "_in_hand#inventory"))));

        // Thanks Juce! :)
        ResourceManagerHelper.registerBuiltinResourcePack(ArtifalityMod.newId("fancyclusters"), FabricLoader.getInstance().getModContainer("artifality").get(), ResourcePackActivationType.NORMAL);
    }
}
