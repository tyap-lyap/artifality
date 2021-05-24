package artifality.data;

import artifality.ArtifalityMod;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;

public class ArtifalityResources {

    public static final RuntimeResourcePack RESOURCES = RuntimeResourcePack.create(ArtifalityMod.MODID + ":resources");

    public static void init(){

        ArtifalityItemResources.init(RESOURCES);
        ArtifalityBlockResources.init(RESOURCES);
        ArtifalityTranslations.init(RESOURCES);

        RRPCallback.EVENT.register(resources -> resources.add(RESOURCES));
    }
}
