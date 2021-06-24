package artifality;

import artifality.block.ArtifalityBlocks;
import artifality.data.ArtifalityLootTables;
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

    public static final String MODID = "artifality";

    @Override
    public void onInitialize() {
//        ArtifalityPotions.register();
//        ArtifalityEffects.register();
        ArtifalityItems.register();
        ArtifalityBlocks.register();
        ArtifalityEnchantments.register();
        ArtifalityLootTables.register();
        ArtifalityConfiguredFeatures.register();
    }

    public static final ItemGroup ITEMS = FabricItemGroupBuilder.create(new Identifier(MODID, "items")).appendItems((itemStacks) ->{
        ArtifalityItems.getItems().forEach(((id, item) -> itemStacks.add(item.getDefaultStack())));

        ArtifalityBlocks.getBlocks().forEach((id, block) -> itemStacks.add(block.asItem().getDefaultStack()));

        ArtifalityEnchantments.getEnchantments().forEach(((id, enchantment) -> {
            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantedBookItem.addEnchantment(book, new EnchantmentLevelEntry(enchantment, enchantment.getMaxLevel()));
            itemStacks.add(book);
        }));

    }).icon(ArtifalityItems.UKULELE::getDefaultStack).build();
}
