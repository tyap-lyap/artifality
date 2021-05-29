package artifality.data;

import artifality.ArtifalityMod;
import artifality.block.ArtifalityBlocks;
import artifality.enchantment.ArtifalityEnchantments;
import artifality.interfaces.IArtifalityBlock;
import artifality.interfaces.IArtifalityEnchantment;
import artifality.interfaces.IArtifalityItem;
import artifality.item.ArtifalityItems;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.lang.JLang;
import net.minecraft.util.Identifier;

public class ArtifalityTranslations {

    private static final Identifier EN_US = new Identifier(ArtifalityMod.MODID, "en_us");
    private static final JLang LANG = JLang.lang();

    public static void init(RuntimeResourcePack pack){

        ArtifalityItems.getItems().forEach((id, item) -> pack.addLang(EN_US, LANG.item(item, ((IArtifalityItem) item).getTranslation())));
        ArtifalityEnchantments.getEnchantments().forEach((id, enchantment) -> {
            pack.addLang(EN_US, LANG.enchantment(enchantment, ((IArtifalityEnchantment) enchantment).getTranslation()));
            pack.addLang(EN_US, LANG.entry(enchantment.getTranslationKey() + ".description", ((IArtifalityEnchantment) enchantment).getDescription()));
        });
        ArtifalityBlocks.getBlocks().forEach((id, block) -> pack.addLang(EN_US, LANG.block(block, ((IArtifalityBlock) block).getTranslation())));

        miscTranslations(pack);

    }

    private static void miscTranslations(RuntimeResourcePack pack){
        pack.addLang(EN_US, LANG.itemGroup(new Identifier(ArtifalityMod.MODID, "items"), "Artifality: Items"));
        pack.addLang(EN_US, LANG.itemGroup(new Identifier(ArtifalityMod.MODID, "tierable_items"), "Artifality: Tierable Items"));
    }
}
