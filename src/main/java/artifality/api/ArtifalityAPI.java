package artifality.api;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class ArtifalityAPI implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {

    }

    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> TwoModeledItems.ENTRIES.forEach((id, item) ->
                out.accept(new ModelIdentifier(new Identifier(id + "_in_hand"), "inventory"))
        ));
    }
}
