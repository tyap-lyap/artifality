package artifality.item.base;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class TierableItem extends BaseItem {

    public TierableItem(Settings settings, String name) {
        super(settings, name);
    }

    public TierableItem(Settings settings, String parentModel, String name) {
        super(settings, parentModel, name);
    }

    public static int getCurrentTier(ItemStack stack){
        if(stack.getOrCreateTag().getInt("ArtifactLevel") >= 2) return stack.getOrCreateTag().getInt("ArtifactLevel");
        else return 1;
    }

    public static ItemStack withTier(Item item, int tier){
        ItemStack itemStack = new ItemStack(item);
        if (tier >= 2){
            itemStack.getOrCreateTag().putInt("ArtifactLevel", tier);
        }
        return itemStack;
    }

    @Override
    public Text getName(ItemStack stack) {
        return switch (TierableItem.getCurrentTier(stack)) {
            default -> new TranslatableText(this.getTranslationKey(stack));
            case 2 -> new TranslatableText(this.getTranslationKey(stack)).formatted(Formatting.YELLOW);
            case 3 -> new TranslatableText(this.getTranslationKey(stack)).formatted(Formatting.AQUA);
        };
    }
}
