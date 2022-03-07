package artifality.item;

import artifality.item.base.NatureStaffItem;
import artifality.util.TiersUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.ArrayList;
import java.util.Arrays;

import static net.minecraft.block.Blocks.*;

public class FloralStaffItem extends NatureStaffItem {
    public static final ArrayList<Block> FLOWERS = new ArrayList<>(Arrays.asList(
            CORNFLOWER, ALLIUM, DANDELION, POPPY, BLUE_ORCHID, AZURE_BLUET,
            RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY,
            LILY_OF_THE_VALLEY));

    public FloralStaffItem(ArtifactSettings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient || context.getPlayer() == null) return super.useOnBlock(context);

        BlockPos pos = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(pos);
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();

        if(!player.getInventory().contains(Items.BONE_MEAL.getDefaultStack())) return super.useOnBlock(context);
        ItemStack boneMeal = player.getInventory().getStack(player.getInventory().getSlotWithStack(Items.BONE_MEAL.getDefaultStack()));

        if(FLOWERS.contains(state.getBlock())) {
            boneMeal.decrement(1);
            dropExperience(world, pos, world.getRandom().nextInt(2) + TiersUtils.getTier(context.getStack()));
            Block.dropStacks(state, world, pos);
            world.playSound(null, pos, SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        }
        else if(Feature.isSoil(state) && context.getSide().equals(Direction.UP) && world.getBlockState(pos.up()).isAir()) {
            boneMeal.decrement(1);
            dropExperience(world, pos, world.getRandom().nextInt(2) + TiersUtils.getTier(context.getStack()));
            BlockSoundGroup blockSoundGroup = Blocks.AZALEA.getSoundGroup(Blocks.AZALEA.getDefaultState());
            world.playSound(null, pos, blockSoundGroup.getPlaceSound(), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);
            world.setBlockState(pos.up(), FLOWERS.get(world.getRandom().nextInt(FLOWERS.size())).getDefaultState());
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
