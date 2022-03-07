package artifality;

import artifality.data.ArtifalityLootTables;
import artifality.registry.*;
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
    public static final ItemGroup ITEM_GROUP = createItemGroup();

    @Override
    public void onInitialize() {
        ArtifalityItems.init();
        ArtifalityBlocks.init();
        ArtifalityEnchants.init();
        ArtifalityFeatures.init();
        ArtifalityEvents.init();
        ArtifalityLootTables.init();
        ArtifalityEffects.init();
    }

    private static ItemGroup createItemGroup() {
        return FabricItemGroupBuilder.create(locate("items"))
                .appendItems(stacks -> {
                    ArtifalityItems.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
                    ArtifalityBlocks.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
                    ArtifalityEnchants.ENCHANTMENTS.forEach((id, enchantment) -> {
                        for(int i = 1; i <= enchantment.getMaxLevel(); i++) {
                            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                            EnchantedBookItem.addEnchantment(book, new EnchantmentLevelEntry(enchantment, i));
                            stacks.add(book);
                        }
                    });
                })
                .icon(ArtifalityItems.LUNAR_CRYSTAL_WAND::getDefaultStack).build();
    }

    public static Identifier locate(String path) {
        return new Identifier(MOD_ID, path);
    }
}
