package artifality.util;

import artifality.item.base.TieredItem;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class TooltipAppender {

    protected TooltipAppender(){}

    public static void appendDescription(ItemStack stack, List<Text> tooltip){
        Item item = stack.getItem();

        if(hasDescription(stack) && shiftPressed(tooltip, item)){
            if(item instanceof TieredItem){
                appendTier(stack, tooltip);
            }
            appendItemDescription(stack, tooltip);
        }else if(item instanceof EnchantedBookItem){
            appendEnchantmentDesc(stack, tooltip);
        }
    }

    private static boolean hasDescription(ItemStack stack){
        return Language.getInstance().hasTranslation(stack.getTranslationKey() + ".description");
    }

    private static boolean shiftPressed(List<Text> tooltip, Item item){
        if(!Screen.hasShiftDown()){
            tooltip.add(new LiteralText(""));
            tooltip.add(new LiteralText(ofKey("press_shift")).formatted(Formatting.GRAY));
            if(item instanceof Trinket) tooltip.add(new LiteralText(""));
            return false;
        }else return true;
    }

    private static void appendTier(ItemStack stack, List<Text> tooltip){
        LiteralText tierString = new LiteralText(ofKey("tier") + " " + TieredItem.getCurrentTier(stack));

        switch (TieredItem.getCurrentTier(stack)) {
            default -> tooltip.add(tierString);
            case 2 -> tooltip.add(tierString.formatted(Formatting.GREEN));
            case 3 -> tooltip.add(tierString.formatted(Formatting.LIGHT_PURPLE));
        }
    }

    private static void appendItemDescription(ItemStack stack, List<Text> tooltip){
        String description = Language.getInstance().get(stack.getItem().getTranslationKey() + ".description");

        tooltip.add(new LiteralText(""));
        tooltip.add(new LiteralText(ofKey("description") + " ").formatted(Formatting.GRAY));
        for(String line : description.split("\n")) {
            tooltip.add(new LiteralText(line.trim().replaceAll("&", "§")).formatted(Formatting.GRAY));
        }
        if(stack.getItem() instanceof Trinket) tooltip.add(new LiteralText(""));
    }

    private static void appendEnchantmentDesc(ItemStack stack, List<Text> tooltip){
        NbtList enchantments = EnchantedBookItem.getEnchantmentNbt(stack);

        for(int i = 0; i < enchantments.size(); ++i) {
            NbtCompound nbtCompound = enchantments.getCompound(i);

            Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(nbtCompound.getString("id"))).ifPresent((enchantment) -> {
                if(!enchantment.getTranslationKey().contains("artifality")) return;
                if(!shiftPressed(tooltip, stack.getItem())) return;

                String description = Language.getInstance().get(enchantment.getTranslationKey() + ".description");

                tooltip.add(new LiteralText(""));
                tooltip.add(new LiteralText(ofKey("description") + " ").formatted(Formatting.GRAY));
                for(String line : description.split("\n")) {
                    tooltip.add(new LiteralText(line.trim().replaceAll("&", "§")).formatted(Formatting.GRAY));
                }
                if(enchantment.getMaxLevel() > 1){
                    tooltip.add(new LiteralText(""));
                    tooltip.add(new LiteralText(ofKey("max_level") + " " + enchantment.getMaxLevel()).formatted(Formatting.GRAY));
                }
            });
        }
    }

    public static String ofKey(String name){
        return Language.getInstance().get("misc.artifality." + name).replaceAll("&", "§");
    }
}
