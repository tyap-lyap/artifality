package artifality;

import artifality.registry.ArtifalityParticles;
import artifality.command.ArtifalityCommands;
import artifality.compat.ArtifalityOwoLibIntegration;
import artifality.data.ArtifalityLootTables;
import artifality.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
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
    public static ItemGroup itemGroup = FabricItemGroup.builder()
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
        ArtifalityBlockEntities.init();
        ArtifalityEnchants.init();
        ArtifalityFeatures.init();
        ArtifalityEvents.init();
        ArtifalityLootTables.init();
        ArtifalityEffects.init();
        ArtifalityCommands.init();
        ArtifalityParticles.init();
        itemGroup = createItemGroup();
        if(FabricLoader.getInstance().isModLoaded("owo")) {
            ArtifalityOwoLibIntegration.initItemGroup();
        }
    }

    private static ItemGroup createItemGroup() {
        if(FabricLoader.getInstance().isModLoaded("owo")) {
            return ArtifalityOwoLibIntegration.createItemGroup();
        }
        var group = FabricItemGroup.builder().displayName(Text.translatable("itemGroup.artifality.items"))
                .icon(ArtifalityItems.FOREST_STAFF::getDefaultStack)
                .entries((displayContext, entries) -> {
                    ArtifalityItems.ITEMS.forEach((id, item) -> entries.add(item.getDefaultStack()));
                    ArtifalityBlocks.ITEMS.forEach((id, item) -> entries.add(item.getDefaultStack()));
                    ArtifalityEnchants.ENCHANTMENTS.forEach((id, enchantment) -> {
                        for(int i = 1; i <= enchantment.getMaxLevel(); i++) {
                            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                            EnchantedBookItem.addEnchantment(book, new EnchantmentLevelEntry(enchantment, i));
                            entries.add(book);
                        }
                    });
                }).build();
        Registry.register(Registries.ITEM_GROUP, id("items"), group);
        return group;
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
