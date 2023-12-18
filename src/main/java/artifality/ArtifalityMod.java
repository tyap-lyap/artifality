package artifality;

import artifality.data.ArtifalityLootTables;
import artifality.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class ArtifalityMod implements ModInitializer {
    public static final String MOD_ID = "artifality";
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.artifality.items"))
            .entries((ctx, entries) -> {
                ArtifalityItems.ITEMS.forEach((id, item) -> entries.add(item.getDefaultStack()));
                ArtifalityBlocks.ITEMS.forEach((id, item) -> entries.add(item.getDefaultStack()));
                ArtifalityEnchants.ENCHANTMENTS.forEach((id, enchantment) -> {
                    for(int i = 1; i <= enchantment.getMaxLevel(); i++) {
                        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                        EnchantedBookItem.addEnchantment(book, new EnchantmentLevelEntry(enchantment, i));
                        entries.add(book);
                    }
                });
            })
            .icon(ArtifalityItems.FOREST_STAFF::getDefaultStack).build();

    @Override
    public void onInitialize() {
        ArtifalityItems.init();
        ArtifalityBlocks.init();
        ArtifalityEnchants.init();
        ArtifalityFeatures.init();
        ArtifalityEvents.init();
        ArtifalityLootTables.init();
        ArtifalityEffects.init();

        Registry.register(Registries.ITEM_GROUP, id("items"), ITEM_GROUP);
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
