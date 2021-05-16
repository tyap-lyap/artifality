package artifality;

import artifality.data.ArtifalityResources;
import artifality.event.ArtifalityEvents;
import artifality.item.ArtifalityItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@SuppressWarnings("all")
public class ArtifalityMod implements ModInitializer {

    public static final String MODID = "artifality";

    public static final ItemGroup GENERAL = FabricItemGroupBuilder.build(new Identifier(MODID, "general"),
            ()->new ItemStack(ArtifalityItems.UKULELE));


    @Override
    public void onInitialize() {

        ArtifalityItems.register();
        ArtifalityEvents.register();

        ArtifalityResources.init();

    }
}
