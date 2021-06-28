package artifality.item;

import artifality.item.base.BaseItem;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BalloonItem extends BaseItem {

    private final String type;

    public BalloonItem(Settings settings, String type) {
        super(settings, "Balloon");
        this.type = type;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if(entity.isSneaking() && entity instanceof LivingEntity livingEntity){

            if(livingEntity.getStackInHand(Hand.OFF_HAND).getItem() instanceof BalloonItem || selected){

                if (!livingEntity.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 10, 0, false, false));

                }else if (livingEntity.getActiveStatusEffects().get(StatusEffects.SLOW_FALLING).getDuration() == 1) {

                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 10, 0, false, false));
                }
            }
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient) return ActionResult.PASS;

        if (context.getWorld().getBlockState(context.getBlockPos()).isOf(Blocks.WATER_CAULDRON)){
            if(type != null && context.getPlayer() != null){
                context.getPlayer().setStackInHand(context.getHand(), ArtifalityItems.BALLOON.getDefaultStack());
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public String getDescription() {
        return "When in hand increases jump\nheight and while sneaking\ngives slow falling effect,\nuse on a cauldron with\nwater to clear the color.";
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(this.type != null) tooltip.add(new TranslatableText("misc.artifality." + type));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
