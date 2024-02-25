package artifality.compat;

import artifality.ArtifalityMod;
import artifality.registry.ArtifalityBlocks;
import artifality.registry.ArtifalityEnchants;
import artifality.registry.ArtifalityItems;
import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.gui.ItemGroupButton;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ArtifalityOwoLibIntegration {
    public static final Identifier ICONS_TEXTURE = ArtifalityMod.id("textures/gui/icons.png");

    public static ItemGroup createItemGroup() {
        return OwoItemGroup.builder(ArtifalityMod.id("items"), () -> Icon.of(ArtifalityItems.FOREST_STAFF))
                .initializer(group -> {
                    group.addButton(ItemGroupButton.link(group, Icon.of(ICONS_TEXTURE, 0, 0, 64, 64), "discord", "https://discord.gg/DcemWeskeZ"));
                }).build();
    }

    public static void initItemGroup() {
        if(ArtifalityMod.itemGroup instanceof OwoItemGroup owoItemGroup) {
            owoItemGroup.initialize();
        }

        ItemGroupEvents.modifyEntriesEvent(Registries.ITEM_GROUP.getKey(ArtifalityMod.itemGroup).get()).register(entries -> {
            ArtifalityItems.ITEMS.forEach((id, item) -> entries.add(item.getDefaultStack()));
            ArtifalityBlocks.ITEMS.forEach((id, item) -> entries.add(item.getDefaultStack()));
            ArtifalityEnchants.ENCHANTMENTS.forEach((id, enchantment) -> {
                for(int i = 1; i <= enchantment.getMaxLevel(); i++) {
                    ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                    EnchantedBookItem.addEnchantment(book, new EnchantmentLevelEntry(enchantment, i));
                    entries.add(book);
                }
            });
        });
    }
}
