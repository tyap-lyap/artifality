package artifality.item;

import artifality.registry.ArtifalityBlocks;
import artifality.registry.ArtifalityEnchants;
import artifality.registry.ArtifalityItems;
import com.glisco.owo.itemgroup.OwoItemGroup;
import com.glisco.owo.itemgroup.gui.ItemGroupButton;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class ArtifalityItemGroup extends OwoItemGroup {

    public ArtifalityItemGroup(Identifier id) {
        super(id);
    }

    @Override
    protected void setup() {
        addButton(ItemGroupButton.discord("https://discord.gg/DcemWeskeZ"));
    }

    @Override
    public ItemStack createIcon() {
        return ArtifalityItems.WRATH_CRYSTAL_WAND.getDefaultStack();
    }

    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        ArtifalityItems.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
        ArtifalityBlocks.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
        ArtifalityEnchants.ENCHANTMENTS.forEach((id, enchantment) -> {
            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantedBookItem.addEnchantment(book, new EnchantmentLevelEntry(enchantment, enchantment.getMaxLevel()));
            stacks.add(book);
        });
    }
}

