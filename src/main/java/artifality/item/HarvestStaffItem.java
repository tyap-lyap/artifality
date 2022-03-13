package artifality.item;

import artifality.item.base.ArtifactItem;
import artifality.util.ExpUtils;
import artifality.util.TiersUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class HarvestStaffItem extends ArtifactItem {

    public HarvestStaffItem(ArtifactSettings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient || context.getPlayer() == null) return super.useOnBlock(context);

        BlockPos pos = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(pos);
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();

        if(player.getInventory().contains(Items.BONE_MEAL.getDefaultStack())) {
            ItemStack boneMeal = player.getInventory().getStack(player.getInventory().getSlotWithStack(Items.BONE_MEAL.getDefaultStack()));
            if(BoneMealItem.useOnFertilizable(boneMeal, world, pos)) {
                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
                ExpUtils.drop(world, pos, world.getRandom().nextInt(2) + TiersUtils.getTier(context.getStack()));
                return ActionResult.SUCCESS;
            }
        }
        if(state.getBlock() instanceof CropBlock crop && crop.isMature(state)) {
            Block.dropStacks(state, world, pos);
            world.setBlockState(pos, crop.withAge(0));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
