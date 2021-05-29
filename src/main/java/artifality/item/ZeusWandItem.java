package artifality.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ZeusWandItem extends TierableItem {

    public ZeusWandItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.getItemCooldownManager().set(this, 200);

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
