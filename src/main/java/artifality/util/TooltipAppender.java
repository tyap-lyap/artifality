package artifality.util;

import artifality.item.base.ArtifactItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TooltipAppender {

    public static void append(ItemStack stack, List<Text> tooltip) {
        Item item = stack.getItem();

        if(item instanceof ArtifactItem artifact) {
            if(artifact.artifactSettings.hasTiers) appendTier(stack, tooltip);
        }
        if(!getTooltip(Registries.ITEM.getId(item).getPath()).isEmpty() && shiftPressed(tooltip, item)) {
            appendItemTooltip(stack, tooltip);
        }
        else if(item instanceof EnchantedBookItem) {
            if(!FabricLoader.getInstance().isModLoaded("enchdesc")) {
                appendEnchantmentTooltip(stack, tooltip);
            }
        }
    }

    private static boolean shiftPressed(List<Text> tooltip, Item item) {
        if(!Screen.hasShiftDown()) {
            if(!(item instanceof EnchantedBookItem && FabricLoader.getInstance().isModLoaded("enchdesc"))) {
                tooltip.add(Text.literal(""));
                tooltip.add(Text.literal(ofKey("press_shift")).formatted(Formatting.GRAY));
            }
            if(item instanceof ArtifactItem artifact) {
                if(artifact.artifactSettings.isTrinket) tooltip.add(Text.literal(""));
            }
            return false;
        }
        else {
            return true;
        }
    }

    private static void appendTier(ItemStack stack, List<Text> tooltip) {
        MutableText tierString = Text.literal(ofKey("tier").replaceAll("%", Integer.toString(TiersUtils.getTier(stack))));

        switch (TiersUtils.getTier(stack)) {
            default -> tooltip.add(tierString);
            case 2 -> tooltip.add(tierString.formatted(Formatting.GREEN));
            case 3 -> tooltip.add(tierString.formatted(Formatting.LIGHT_PURPLE));
        }
    }

    private static void appendItemTooltip(ItemStack stack, List<Text> tooltip) {
        tooltip.add(Text.literal(""));
        for(String line : getTooltip(Registries.ITEM.getId(stack.getItem()).getPath())) {
            tooltip.add(Text.literal(line.trim().replaceAll("&", "§")).formatted(Formatting.GRAY));
        }
        if(stack.getItem() instanceof ArtifactItem artifact) {
            artifact.appendTooltipInfo(stack, tooltip);
            if(artifact.artifactSettings.isTrinket) tooltip.add(Text.literal(""));
        }
    }

    private static void appendEnchantmentTooltip(ItemStack stack, List<Text> tooltip) {
        NbtList enchantments = EnchantedBookItem.getEnchantmentNbt(stack);

        for(int i = 0; i < enchantments.size(); ++i) {
            NbtCompound nbtCompound = enchantments.getCompound(i);

            Registries.ENCHANTMENT.getOrEmpty(Identifier.tryParse(nbtCompound.getString("id"))).ifPresent((enchantment) -> {
                if(!enchantment.getTranslationKey().contains("artifality")) return;
                if(!shiftPressed(tooltip, stack.getItem())) return;
                tooltip.add(Text.literal(""));
                for(String line : getTooltip(Objects.requireNonNull(Registries.ENCHANTMENT.getId(enchantment)).getPath())) {
                    tooltip.add(Text.literal(line.trim().replaceAll("&", "§")).formatted(Formatting.GRAY));
                }
                if(enchantment.getMaxLevel() > 1){
                    tooltip.add(Text.literal(""));
                    tooltip.add(Text.literal(ofKey("max_level").replaceAll("%", Integer.toString(enchantment.getMaxLevel()))).formatted(Formatting.DARK_GREEN));
                }
            });
        }
    }

    public static ArrayList<String> getTooltip(String id) {
        ArrayList<String> strings = new ArrayList<>();
        for(int i = 0; i <= 10; i++) {
            if(Language.getInstance().hasTranslation("tooltip.artifality." + id + "." + i)){
                strings.add(Language.getInstance().get("tooltip.artifality." + id + "." + i));
            }
        }
        return strings;
    }

    public static String ofKey(String name) {
        return Language.getInstance().get("misc.artifality." + name).replaceAll("&", "§");
    }
}
