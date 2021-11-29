package artifality;

import artifality.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class ArtifalityMod implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Artifality");
    public static final String MOD_ID = "artifality";

    public static final ItemGroup ITEMS_ITEM_GROUP = FabricItemGroupBuilder.create(newId("items"))
            .appendItems(groupStacks -> {
                ArtifalityItems.ITEMS.forEach((id, item) -> groupStacks.add(item.getDefaultStack()));
                ArtifalityBlocks.ITEMS.forEach((id, item) -> groupStacks.add(item.getDefaultStack()));
                ArtifalityEnchants.ENCHANTMENTS.forEach((id, enchantment) -> {
                    ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                    EnchantedBookItem.addEnchantment(book, new EnchantmentLevelEntry(enchantment, enchantment.getMaxLevel()));
                    groupStacks.add(book);
                });
            })
            .icon(ArtifalityItems.WRATH_CRYSTAL_WAND::getDefaultStack).build();

    @Override
    public void onInitialize() {
        ArtifalityItems.init();
        ArtifalityBlocks.init();
        ArtifalityEnchants.init();
        ArtifalityFeatures.init();
    }

    public static Identifier newId(String path){
        return new Identifier(MOD_ID, path);
    }
}
