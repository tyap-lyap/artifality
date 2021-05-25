package artifality;

import artifality.block.ArtifalityBlocks;
import artifality.data.ArtifalityResources;
import artifality.data.ArtifalityLootTables;
import artifality.effect.ArtifalityEffects;
import artifality.enchantment.ArtifalityEnchantments;
import artifality.event.ArtifalityEvents;
import artifality.item.ArtifalityItems;
import artifality.item.ArtifalityPotions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("all")
public class ArtifalityMod implements ModInitializer {

    public static final String MODID = "artifality";

    public static final ItemGroup GENERAL = FabricItemGroupBuilder.create(new Identifier(MODID, "general")).appendItems(new Consumer<List<ItemStack>>() {
        @Override
        public void accept(List<ItemStack> itemStacks) {

            ArtifalityItems.getItems().forEach(((id, item) -> {
                item.appendStacks(GENERAL, (DefaultedList<ItemStack>) itemStacks);
            }));

            ArtifalityBlocks.getBlocks().forEach((id, block) -> {
                block.asItem().appendStacks(GENERAL, (DefaultedList<ItemStack>) itemStacks);
            });

            ArtifalityEnchantments.getEnchantments().forEach(((id, enchantment) -> {
                ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                EnchantedBookItem.addEnchantment(book, new EnchantmentLevelEntry(enchantment, enchantment.getMaxLevel()));
                itemStacks.add(book);
            }));

        }
    }).icon(new Supplier<ItemStack>() {
        @Override
        public ItemStack get() {
            return ArtifalityItems.UKULELE.getDefaultStack();
        }
    }).build();


    @Override
    public void onInitialize() {
        ArtifalityPotions.register();
        ArtifalityEffects.register();
        ArtifalityItems.register();
        ArtifalityBlocks.register();
        ArtifalityEvents.register();
        ArtifalityEnchantments.register();
        ArtifalityResources.init();
        ArtifalityLootTables.register();
    }
}
