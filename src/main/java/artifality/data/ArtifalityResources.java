package artifality.data;

import artifality.ArtifalityMod;
import artifality.enchantment.ArtifalityEnchantments;
import artifality.interfaces.IArtifalityEnchantment;
import artifality.interfaces.IArtifalityItem;
import artifality.item.ArtifalityItems;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.lang.JLang;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.util.Identifier;

public class ArtifalityResources {

    public static final RuntimeResourcePack RESOURCES = RuntimeResourcePack.create(ArtifalityMod.MODID + ":resources");

    private static final Identifier EN_US = new Identifier(ArtifalityMod.MODID, "en_us");
    private static final JLang LANG = JLang.lang();

    public static void init(){

        ArtifalityItems.getItems().forEach((id, item) -> {

            if(item instanceof IArtifalityItem){
                String parentModel = ((IArtifalityItem) item).getParentModel();
                if (!parentModel.equals("custom")){
                    RESOURCES.addModel(JModel.model("minecraft:item/" + parentModel).textures(JModel.textures()
                                    .var("layer0",  ArtifalityMod.MODID + ":item/" + id.getPath())),
                            new Identifier(ArtifalityMod.MODID + ":item/" + id.getPath()));

                }
                RESOURCES.addLang(EN_US, LANG.item(item, ((IArtifalityItem) item).getTranslation()));
            }
        });
        ArtifalityEnchantments.getEnchantments().forEach((id, enchantment) -> {

            if(enchantment instanceof IArtifalityEnchantment){
                RESOURCES.addLang(EN_US, LANG.enchantment(enchantment, ((IArtifalityEnchantment) enchantment).getTranslation()));
            }

        });
        miscTranslations();

        RRPCallback.EVENT.register(resources -> resources.add(RESOURCES));

    }

    private static void miscTranslations(){
        RESOURCES.addLang(EN_US, LANG.itemGroup(new Identifier(ArtifalityMod.MODID, "general"), "Artifality Items"));
    }
}
