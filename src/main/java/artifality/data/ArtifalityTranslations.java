package artifality.data;

import artifality.ArtifalityMod;
import artifality.block.ArtifalityBlocks;
import artifality.enchantment.ArtifalityEnchantments;
import artifality.interfaces.Translatable;
import artifality.item.ArtifalityItems;
import artifality.item.base.BaseItem;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.lang.JLang;
import net.minecraft.util.Identifier;

public class ArtifalityTranslations {

    private static final Identifier EN_US = new Identifier(ArtifalityMod.MODID, "en_us");
    private static final JLang LANG = JLang.lang();
    private static RuntimeResourcePack pack;

    public static void init(RuntimeResourcePack pack){
        ArtifalityTranslations.pack = pack;

        ArtifalityItems.getItems().forEach((id, item) -> {
            if(((BaseItem) item).isWip()) add(LANG.itemRespect(item, ((Translatable) item).getOriginName() + " WIP"));
            else add(LANG.itemRespect(item, ((Translatable) item).getOriginName()));
            if (((Translatable) item).getDescription() != null){
                add(LANG.entry(item.getTranslationKey() + ".description", ((Translatable) item).getDescription()));
            }
        });

        ArtifalityEnchantments.getEnchantments().forEach((id, enchantment) -> {
            add(LANG.enchantmentRespect(enchantment, ((Translatable) enchantment).getOriginName()));
            add(LANG.entry(enchantment.getTranslationKey() + ".description", ((Translatable) enchantment).getDescription()));
        });

        ArtifalityBlocks.getBlocks().forEach((id, block) -> {
            add(LANG.blockRespect(block, ((Translatable) block).getOriginName()));
            add(LANG.entry(block.getTranslationKey() + ".description", ((Translatable) block).getDescription()));
        });

        miscTranslations();
    }

    private static void miscTranslations(){
        add(LANG.itemGroup(new Identifier(ArtifalityMod.MODID, "items"), "Artifality: Items"));
        add(LANG.itemGroup(new Identifier(ArtifalityMod.MODID, "tierable_items"), "Artifality: Tierable Items"));
        add(LANG.itemGroup(new Identifier(ArtifalityMod.MODID, "wip_items"), "Artifality: Work In Progress"));
    }

    static void add(JLang entry){
        pack.addLang(EN_US, entry);
    }
}
