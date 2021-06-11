package artifality.data;

import artifality.ArtifalityMod;
import artifality.interfaces.ModelProvider;
import artifality.item.ArtifalityItems;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.util.Identifier;

public class ArtifalityItemResources {

    public static void init(RuntimeResourcePack pack){

        ArtifalityItems.getItems().forEach((id, item) -> {

            String parentModel = ((ModelProvider) item).getParentModel();
            if (!parentModel.equals("custom")){
                pack.addModel(JModel.model("minecraft:item/" + parentModel).textures(JModel.textures()
                                .var("layer0",  ArtifalityMod.MODID + ":item/" + id.getPath())),
                        new Identifier(ArtifalityMod.MODID + ":item/" + id.getPath()));

            }
        });
    }
}
