package artifality.util;

import artifality.extension.Artifact;
import artifality.item.base.BaseItem;
import artifality.item.base.TieredItem;
import artifality.list.ArtifactRarity;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TooltipAppender {

    protected TooltipAppender(){}

    public static void append(ItemStack stack, List<Text> tooltip){
        Item item = stack.getItem();

        if(!getTooltip(Registry.ITEM.getId(item).getPath()).isEmpty() && shiftPressed(tooltip, item)){
            if(item instanceof TieredItem){
                appendTier(stack, tooltip);
            }
            appendItemTooltip(stack, tooltip);
        }else if(item instanceof EnchantedBookItem){
            appendEnchantmentTooltip(stack, tooltip);
        }
    }

    private static boolean shiftPressed(List<Text> tooltip, Item item){
        if(!Screen.hasShiftDown()){
            if(item instanceof Artifact artifact){
                ArtifactRarity rarity = artifact.getSettings().getRarity();
                tooltip.add(new LiteralText(ofKey(rarity.getName())).setStyle(Style.EMPTY.withColor(rarity.getColor().getRGB())));
            }
            tooltip.add(new LiteralText(""));
            tooltip.add(new LiteralText(ofKey("press_shift")).formatted(Formatting.GRAY));
            if(item instanceof Trinket) tooltip.add(new LiteralText(""));
            return false;
        }else{
            return true;
        }
    }

    private static void appendTier(ItemStack stack, List<Text> tooltip){
        LiteralText tierString = new LiteralText(ofKey("tier").replaceAll("%", Integer.toString(TieredItem.getCurrentTier(stack))));

        switch (TieredItem.getCurrentTier(stack)) {
            default -> tooltip.add(tierString);
            case 2 -> tooltip.add(tierString.formatted(Formatting.GREEN));
            case 3 -> tooltip.add(tierString.formatted(Formatting.LIGHT_PURPLE));
        }
    }

    private static void appendItemTooltip(ItemStack stack, List<Text> tooltip){
        tooltip.add(new LiteralText(""));
        for(String line : getTooltip(Registry.ITEM.getId(stack.getItem()).getPath())) {
            tooltip.add(new LiteralText(line.trim().replaceAll("&", "§")).formatted(Formatting.GRAY));
        }
        if(stack.getItem() instanceof BaseItem item)item.appendTooltipInfo(stack, tooltip);
        if(stack.getItem() instanceof Trinket) tooltip.add(new LiteralText(""));
    }

    private static void appendEnchantmentTooltip(ItemStack stack, List<Text> tooltip){
        NbtList enchantments = EnchantedBookItem.getEnchantmentNbt(stack);

        for(int i = 0; i < enchantments.size(); ++i) {
            NbtCompound nbtCompound = enchantments.getCompound(i);

            Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(nbtCompound.getString("id"))).ifPresent((enchantment) -> {
                if(!enchantment.getTranslationKey().contains("artifality")) return;
                if(!shiftPressed(tooltip, stack.getItem())) return;
                tooltip.add(new LiteralText(""));
                for(String line : getTooltip(Objects.requireNonNull(Registry.ENCHANTMENT.getId(enchantment)).getPath())) {
                    tooltip.add(new LiteralText(line.trim().replaceAll("&", "§")).formatted(Formatting.GRAY));
                }
                if(enchantment.getMaxLevel() > 1){
                    tooltip.add(new LiteralText(""));
                    tooltip.add(new LiteralText(ofKey("max_level").replaceAll("%", Integer.toString(enchantment.getMaxLevel()))).formatted(Formatting.DARK_GREEN));
                }
            });
        }
    }

    public static ArrayList<String> getTooltip(String id){
        ArrayList<String> strings = new ArrayList<>();
        for(int i = 0; i <= 10; i++){
            if(Language.getInstance().hasTranslation("tip." + id + "." + i)){
                strings.add(Language.getInstance().get("tip." + id + "." + i));
            }
        }
        return strings;
    }

    public static String ofKey(String name){
        return Language.getInstance().get("misc.artifality." + name).replaceAll("&", "§");
    }
}
