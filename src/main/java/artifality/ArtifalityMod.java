package artifality;

import artifality.block.ArtifalityBlocks;
import artifality.data.ArtifalityLootTables;
import artifality.data.ArtifalityTags;
import artifality.enchantment.ArtifalityEnchantments;
import artifality.item.ArtifalityItems;
import artifality.worldgen.feature.ArtifalityConfiguredFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class ArtifalityMod implements ModInitializer {

    public static final String MOD_ID = "artifality";

    public static final ItemGroup ITEMS_ITEM_GROUP = FabricItemGroupBuilder
            .create(newId("items"))
            .appendItems(itemStacks -> {
                ArtifalityItems.getRegisteredItems().forEach((s, items) -> items.forEach(item -> itemStacks.add(item.getDefaultStack())));
                ArtifalityBlocks.getRegisteredBlocks().forEach((s, items) -> items.forEach(item -> itemStacks.add(item.getDefaultStack())));

                ArtifalityEnchantments.getEnchantments().forEach((id, enchantment) -> {
                    ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                    EnchantedBookItem.addEnchantment(book, new EnchantmentLevelEntry(enchantment, enchantment.getMaxLevel()));
                    itemStacks.add(book);
                });
            })
            .icon(ArtifalityItems.CRYSTAL_HEART::getDefaultStack).build();

    @Override
    public void onInitialize() {
        ArtifalityEnchantments.register();
        ArtifalityLootTables.register();
        ArtifalityConfiguredFeatures.register();
        ArtifalityTags.initTags();
    }

    public static Identifier newId(String path){
        return new Identifier(MOD_ID, path);
    }
}
