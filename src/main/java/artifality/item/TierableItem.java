package artifality.item;

import artifality.interfaces.ITierableItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class TierableItem extends BaseItem implements ITierableItem {

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
    public void applyEffects(World world, PlayerEntity playerEntity, int tier) {

    }

    @Override
    public Text getName(ItemStack stack) {
        switch (TierableItem.getCurrentTier(stack)){
            case 1:
                return new TranslatableText(this.getTranslationKey(stack));
            case 2:
                return new TranslatableText(this.getTranslationKey(stack)).formatted(Formatting.YELLOW);
            case 3:
            default:
                return new TranslatableText(this.getTranslationKey(stack)).formatted(Formatting.AQUA);
        }
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if(isIn(group)){
            ItemStack itemStack = new ItemStack(this);
            stacks.add(itemStack);
        }
    }
}
