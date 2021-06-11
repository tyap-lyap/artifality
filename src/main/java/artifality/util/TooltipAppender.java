package artifality.util;

import artifality.interfaces.Translatable;
import artifality.item.base.BaseBlockItem;
import artifality.item.base.TierableItem;
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

        if(hasDescription(stack) && shiftPressed(tooltip)){
            if(item instanceof Translatable || item instanceof BaseBlockItem){
                if(item instanceof TierableItem){
                    appendTier(stack, tooltip);
                }
                appendItemDescription(stack, tooltip);

            }else if(item instanceof EnchantedBookItem){
                appendEnchantmentDesc(stack, tooltip);
            }
        }
    }

    private static boolean hasDescription(ItemStack stack){
        if(stack.getItem() instanceof Translatable){
            return ((Translatable) stack.getItem()).getDescription() != null;

        }else if(stack.getItem() instanceof BaseBlockItem){
            return ((BaseBlockItem) stack.getItem()).getDescription() != null;

        }else if(stack.getItem() instanceof EnchantedBookItem){
            NbtList enchantments = EnchantedBookItem.getEnchantmentNbt(stack);
            for(int i = 0; i < enchantments.size(); ++i) {
                NbtCompound nbtCompound = enchantments.getCompound(i);
                if(Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(nbtCompound.getString("id"))).isPresent()){
                    if(Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(nbtCompound.getString("id"))).get() instanceof Translatable){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean shiftPressed(List<Text> tooltip){
        if(!Screen.hasShiftDown()){
            tooltip.add(new LiteralText(""));
            tooltip.add(new LiteralText(ofKey("press_shift")).formatted(Formatting.GRAY));
            return false;
        }else return true;
    }

    private static void appendTier(ItemStack stack, List<Text> tooltip){

        if(stack.getItem() instanceof Translatable){
            LiteralText tierString = new LiteralText(ofKey("tier") + " " + TierableItem.getCurrentTier(stack));
            switch (TierableItem.getCurrentTier(stack)) {
                default -> tooltip.add(tierString);
                case 2 -> tooltip.add(tierString.formatted(Formatting.GREEN));
                case 3 -> tooltip.add(tierString.formatted(Formatting.LIGHT_PURPLE));
            }
        }
    }

    private static void appendItemDescription(ItemStack stack, List<Text> tooltip){

        String description = Language.getInstance().get(stack.getItem().getTranslationKey() + ".description");

        tooltip.add(new LiteralText(""));
        tooltip.add(new LiteralText(ofKey("description") + " ").formatted(Formatting.GRAY));
        for(String line : description.split("\n")) {
            tooltip.add(new LiteralText(line.trim()).formatted(Formatting.GRAY));
        }
    }

    private static void appendEnchantmentDesc(ItemStack stack, List<Text> tooltip){

        NbtList enchantments = EnchantedBookItem.getEnchantmentNbt(stack);

        for(int i = 0; i < enchantments.size(); ++i) {
            NbtCompound nbtCompound = enchantments.getCompound(i);

            Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(nbtCompound.getString("id"))).ifPresent((enchantment) -> {

                String description = Language.getInstance().get(enchantment.getTranslationKey() + ".description");

                tooltip.add(new LiteralText(""));
                tooltip.add(new LiteralText(ofKey("description") + " ").formatted(Formatting.GRAY));
                for(String line : description.split("\n")) {
                    tooltip.add(new LiteralText(line.trim()).formatted(Formatting.GRAY));
                }
                if(enchantment.getMaxLevel() > 1){
                    tooltip.add(new LiteralText(""));
                    tooltip.add(new LiteralText(ofKey("max_level") + " " + enchantment.getMaxLevel()).formatted(Formatting.GRAY));
                }
            });
        }
    }

    public static String ofKey(String name){
        return Language.getInstance().get("misc.artifality." + name);
    }
}
