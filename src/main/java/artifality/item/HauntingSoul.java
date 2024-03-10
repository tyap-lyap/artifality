package artifality.item;

import artifality.item.base.ArtifactItem;
import artifality.util.TiersUtils;
import artifality.util.TooltipAppender;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class HauntingSoul extends ArtifactItem implements Trinket {

    public HauntingSoul(ArtifactSettings settings) {
        super(settings);
    }

    @Override
    public void appendTooltipInfo(ItemStack stack, List<Text> tooltip) {
        tooltip.add(Text.empty());

        int max = 120;
        if(TiersUtils.getTier(stack) == 2) max = 160;
        else if(TiersUtils.getTier(stack) == 3) max = 240;

        tooltip.add(Text.literal(TooltipAppender.ofKey("souls").replaceAll("%", getSouls(stack) + "/" + max)).formatted(Formatting.DARK_GREEN));
        tooltip.add(Text.literal(TooltipAppender.ofKey("extra_damage").replaceAll("%", Float.toString(getDamageModifier(stack)))).formatted(Formatting.DARK_GREEN));
    }

    public static int getSouls(ItemStack stack) {
        var nbt = stack.getOrCreateNbt();

        if(nbt.contains("HauntingSouls")) {
            return nbt.getInt("HauntingSouls");
        }

        return 0;
    }

    public static float getDamageModifier(ItemStack stack) {
        int max = 120;
        if(TiersUtils.getTier(stack) == 2) max = 160;
        else if(TiersUtils.getTier(stack) == 3) max = 240;

        int souls = getSouls(stack);

        if(souls >= max) return (float) max / 40;
        else return (float)(souls / 20) * 0.5F;
    }

    public static void addSoul(ItemStack stack) {
        var nbt = stack.getOrCreateNbt();

        if(nbt.contains("HauntingSouls")) {
            int souls = nbt.getInt("HauntingSouls");
            souls++;
            nbt.putInt("HauntingSouls", souls);
        }
        else {
            nbt.putInt("HauntingSouls", 1);
        }
    }

    public static void addSouls(ItemStack stack, int increment) {
        var nbt = stack.getOrCreateNbt();

        if(nbt.contains("HauntingSouls")) {
            int souls = nbt.getInt("HauntingSouls");
            souls = souls + increment;
            nbt.putInt("HauntingSouls", souls);
        }
        else {
            nbt.putInt("HauntingSouls", increment);
        }
    }

    public static void setSouls(ItemStack stack, int count) {
        var nbt = stack.getOrCreateNbt();
        nbt.putInt("HauntingSouls", count);
    }
}
