package artifality;

import artifality.block.ArtifalityBlocks;
import artifality.data.ArtifalityLootTables;
import artifality.effect.ArtifalityEffects;
import artifality.enchantment.ArtifalityEnchantments;
import artifality.event.ArtifalityEvents;
import artifality.interfaces.ITierableItem;
import artifality.item.ArtifalityItems;
import artifality.item.ArtifalityPotions;
import artifality.worldgen.feature.ArtifalityConfiguredFeatures;
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

    @Override
    public void onInitialize() {
        ArtifalityPotions.register();
        ArtifalityEffects.register();
        ArtifalityItems.register();
        ArtifalityBlocks.register();
        ArtifalityEvents.register();
        ArtifalityEnchantments.register();
        ArtifalityLootTables.register();
        ArtifalityConfiguredFeatures.register();
    }

    public static final ItemGroup ITEMS = FabricItemGroupBuilder.create(new Identifier(MODID, "items")).appendItems(new Consumer<List<ItemStack>>() {
        @Override
        public void accept(List<ItemStack> itemStacks) {

            ArtifalityItems.getItems().forEach(((id, item) -> {
                item.appendStacks(ITEMS, (DefaultedList<ItemStack>) itemStacks);
            }));

            ArtifalityBlocks.getBlocks().forEach((id, block) -> {
                block.asItem().appendStacks(ITEMS, (DefaultedList<ItemStack>) itemStacks);
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

    public static final ItemGroup TIERABLE_ITEMS = FabricItemGroupBuilder.create(new Identifier(MODID, "tierable_items")).appendItems(new Consumer<List<ItemStack>>() {
        @Override
        public void accept(List<ItemStack> itemStacks) {
            ArtifalityItems.getItems().forEach(((id, item) -> {
                if(item instanceof ITierableItem){
                    for(int i = 1; i <= ((ITierableItem) item).getMaxTiers(); i++){
                        ItemStack itemStack = new ItemStack(item);
                        itemStack.getOrCreateTag().putInt("ArtifactLevel", i);
                        itemStacks.add(itemStack);
                    }
                }
            }));
        }
    }).icon(new Supplier<ItemStack>() {
        @Override
        public ItemStack get() {
            return ArtifalityItems.ZEUS_WAND.getDefaultStack();
        }
    }).build();
}
