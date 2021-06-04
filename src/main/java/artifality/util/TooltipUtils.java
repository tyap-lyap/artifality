package artifality.util;

import artifality.interfaces.IArtifalityEnchantment;
import artifality.interfaces.IArtifalityItem;
import artifality.interfaces.ITierableItem;
import artifality.item.BaseBlockItem;
import artifality.item.TierableItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class TooltipUtils {

    protected TooltipUtils(){}

    public static void appendDescription(ItemStack stack, List<Text> tooltip){

        Item item = stack.getItem();

        if(hasDescription(stack) && shiftPressed(tooltip)){
            if(item instanceof IArtifalityItem || item instanceof BaseBlockItem){
                if(item instanceof ITierableItem){
                    appendTier(stack, tooltip);
                }
                appendItemDescription(stack, tooltip);

            }else if(item instanceof EnchantedBookItem){
                appendEnchantmentDesc(stack, tooltip);
            }
        }

    }

    private static boolean hasDescription(ItemStack stack){
        if(stack.getItem() instanceof IArtifalityItem){
            return ((IArtifalityItem) stack.getItem()).getDescription() != null;

        }else if(stack.getItem() instanceof BaseBlockItem){
            return ((BaseBlockItem) stack.getItem()).getDescription() != null;

        }else if(stack.getItem() instanceof EnchantedBookItem){
            ListTag enchantments = EnchantedBookItem.getEnchantmentTag(stack);
            for(int i = 0; i < enchantments.size(); ++i) {
                CompoundTag compoundTag = enchantments.getCompound(i);
                if(Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(compoundTag.getString("id"))).isPresent()){
                    if(Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(compoundTag.getString("id"))).get() instanceof IArtifalityEnchantment){
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
            tooltip.add(new LiteralText("<Press Shift>").formatted(Formatting.GRAY));
            return false;
        }else return true;
    }

    private static void appendTier(ItemStack stack, List<Text> tooltip){

        if(stack.getItem() instanceof ITierableItem){
            switch (TierableItem.getCurrentTier(stack)){
                case 1:
                    tooltip.add(new LiteralText("Tier " + TierableItem.getCurrentTier(stack)));
                    break;
                case 2:
                    tooltip.add(new LiteralText("Tier " + TierableItem.getCurrentTier(stack)).formatted(Formatting.GREEN));
                    break;
                case 3:
                default:
                    tooltip.add(new LiteralText("Tier " + TierableItem.getCurrentTier(stack)).formatted(Formatting.LIGHT_PURPLE));
                    break;
            }
        }

    }

    private static void appendItemDescription(ItemStack stack, List<Text> tooltip){

        String description = Language.getInstance().get(stack.getItem().getTranslationKey() + ".description");

        tooltip.add(new LiteralText(""));
        tooltip.add(new LiteralText("Description: ").formatted(Formatting.GRAY));
        for(String line : description.split("\n")) {
            tooltip.add(new LiteralText(line.trim()).formatted(Formatting.GRAY));
        }
    }

    private static void appendEnchantmentDesc(ItemStack stack, List<Text> tooltip){

        ListTag enchantments = EnchantedBookItem.getEnchantmentTag(stack);

        for(int i = 0; i < enchantments.size(); ++i) {
            CompoundTag compoundTag = enchantments.getCompound(i);

            Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(compoundTag.getString("id"))).ifPresent((enchantment) -> {

                if(enchantment instanceof IArtifalityEnchantment){
                    String description = Language.getInstance().get(enchantment.getTranslationKey() + ".description");

                    tooltip.add(new LiteralText(""));
                    tooltip.add(new LiteralText("Description: ").formatted(Formatting.GRAY));
                    for(String line : description.split("\n")) {
                        tooltip.add(new LiteralText(line.trim()).formatted(Formatting.GRAY));
                    }
                    if(enchantment.getMaxLevel() > 1){
                        tooltip.add(new LiteralText(""));
                        tooltip.add(new LiteralText("Max Level: " + enchantment.getMaxLevel()).formatted(Formatting.GRAY));
                    }
                }
            });
        }
    }
}
