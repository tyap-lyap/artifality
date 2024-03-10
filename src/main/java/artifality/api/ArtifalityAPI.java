package artifality.api;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class ArtifalityAPI implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {

    }

    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(context -> {
            TwoModeledItems.ENTRIES.forEach((id, item) ->
                    context.addModels(new ModelIdentifier(new Identifier(id + "_in_hand"), "inventory"))
            );
        });
    }
}
