package artifality.item;

import artifality.item.base.ArtifactItem;
import artifality.util.TrinketsUtils;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BalloonItem extends ArtifactItem implements Trinket {

    public BalloonItem(ArtifactSettings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient) return TypedActionResult.pass(user.getStackInHand(hand));
        ItemStack stack = user.getStackInHand(hand);

        if(stack.getDamage() > 0) {
            if(user.getInventory().contains(Items.DRAGON_BREATH.getDefaultStack())) {
                stack.setDamage(0);
                user.getInventory().getStack(user.getInventory().getSlotWithStack(Items.DRAGON_BREATH.getDefaultStack())).decrement(1);
                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public static boolean hasBalloonOnHead(PlayerEntity entity) {
        for(ItemStack itemStack : TrinketsUtils.getTrinketsArray(entity)){
            if(itemStack.getItem() instanceof BalloonItem)return true;
        }
        return false;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return MathHelper.packRgb(172, 44, 123);
    }
}
