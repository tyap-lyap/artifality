package artifality.item;

import artifality.interfaces.ITearableItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BaseTearableItem extends BaseItem implements ITearableItem {

    public BaseTearableItem(Settings settings, String name) {
        super(settings, name);
    }

    public BaseTearableItem(Settings settings, String parentModel, String name) {
        super(settings, parentModel, name);
    }

    @Override
    public void applyEffects(World world, PlayerEntity playerEntity, int tier) {

    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            for(int i = 1; i <= getMaxTears(); i++){
                ItemStack itemStack = new ItemStack(this);
                itemStack.getOrCreateTag().putInt("ArtifactLevel", i);
                stacks.add(itemStack);
            }
        }
    }
}
