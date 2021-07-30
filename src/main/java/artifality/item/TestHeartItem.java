package artifality.item;

import artifality.interfaces.PlayerEntityExtensions;
import artifality.item.base.BaseItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Arrays;

public class TestHeartItem extends BaseItem {

    public TestHeartItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient) return TypedActionResult.pass(user.getStackInHand(hand));

        if(user instanceof PlayerEntityExtensions manager){
            var heartsManager = manager.getSpecialHeartsManager();

            if(heartsManager.hasEmptySlot()){
                heartsManager.addHeart(1);

                user.sendMessage(new LiteralText("+1 heart, health: " + heartsManager.getHealth() + ", hearts: " + Arrays.toString(heartsManager.getHearts())), false);
            }else{
                user.sendMessage(new LiteralText("health: " + heartsManager.getHealth() + ", hearts: " + Arrays.toString(heartsManager.getHearts())), false);
            }
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
