package artifality.data;

import artifality.ArtifalityMod;
import artifality.interfaces.IItemModel;
import artifality.item.ArtifalityItems;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.util.Identifier;

public class ArtifalityResources {

    public static final RuntimeResourcePack RESOURCES = RuntimeResourcePack.create(ArtifalityMod.MODID + ":resources");

    public static void init(){

        ArtifalityItems.getItems().forEach((id, item) -> {

            if(item instanceof IItemModel){
                String parentModel = ((IItemModel) item).getParentModel();
                if (!parentModel.equals("custom")){
                    RESOURCES.addModel(JModel.model("minecraft:item/" + parentModel).textures(JModel.textures()
                                    .var("layer0",  ArtifalityMod.MODID + ":item/" + id.getPath())),
                            new Identifier(ArtifalityMod.MODID + ":item/" + id.getPath()));

                }
            }
        });

        RRPCallback.EVENT.register(resources -> resources.add(RESOURCES));

    }
}
