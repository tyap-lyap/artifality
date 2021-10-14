package artifality.item.base;

import artifality.block.ArtifalityBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;

public class TieredItem extends ArtifalityBaseItem {

    public TieredItem(Settings settings) {
        super(settings);
    }

    public static int getCurrentTier(ItemStack stack){
        if(stack.getOrCreateNbt().getInt("artifactTier") >= 2) return stack.getOrCreateNbt().getInt("artifactTier");
        else return 1;
    }

    public static ItemStack withTier(Item item, int tier){
        ItemStack itemStack = new ItemStack(item);
        if (tier >= 2){
            itemStack.getOrCreateNbt().putInt("artifactTier", tier);
        }
        return itemStack;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return getCurrentTier(stack) == 3;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().getBlockState(context.getBlockPos()).isOf(ArtifalityBlocks.UPGRADING_PEDESTAL)) return ActionResult.FAIL;
        return super.useOnBlock(context);
    }

    @Override
    public Text getName(ItemStack stack) {
        TranslatableText name = new TranslatableText(this.getTranslationKey(stack));
        return switch (getCurrentTier(stack)) {
            default -> name;
            case 2 -> name.formatted(Formatting.YELLOW);
            case 3 -> name.formatted(Formatting.AQUA);
        };
    }
}
