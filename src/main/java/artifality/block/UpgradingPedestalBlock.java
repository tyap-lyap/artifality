package artifality.block;

import artifality.block.base.BaseBlock;
import artifality.item.base.ArtifactItem;
import artifality.registry.ArtifalityBlocks;
import artifality.util.TiersUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@SuppressWarnings("deprecation")
public class UpgradingPedestalBlock extends BaseBlock {
    public static final IntProperty CHARGES = Properties.CHARGES;

    public UpgradingPedestalBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CHARGES, 0));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient) return ActionResult.PASS;
        ItemStack stack = player.getStackInHand(hand);
        if(hand == Hand.MAIN_HAND && stack.isOf(ArtifalityBlocks.INCREMENTAL_ORB.asItem())) {
            if(getCharges(state) < 3) {
                chargeWithIncremental(world, pos, state);
                stack.decrement(1);
                return ActionResult.SUCCESS;
            }
        }
        else if(hand == Hand.MAIN_HAND && stack.isOf(Items.NETHER_STAR)) {
            if(getCharges(state) != 4) {
                chargeWithNetherStar(world, pos, state);
                stack.decrement(1);
                return ActionResult.SUCCESS;
            }
        }
        else if(hand == Hand.MAIN_HAND && stack.getItem() instanceof ArtifactItem artifact && artifact.config.hasTiers) {
            if(getCharges(state) == 3 && TiersUtils.getTier(stack) == 1) {
                world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                player.setStackInHand(Hand.MAIN_HAND, TiersUtils.withTier(stack.getItem(), 2));
                world.setBlockState(pos, this.getDefaultState());
                return ActionResult.SUCCESS;
            }
            else if(getCharges(state) == 4 && TiersUtils.getTier(stack) == 2) {
                world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                player.setStackInHand(Hand.MAIN_HAND, TiersUtils.withTier(stack.getItem(), 3));
                world.setBlockState(pos, this.getDefaultState());
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    private static int getCharges(BlockState state) {
        return state.get(CHARGES);
    }

    public static void chargeWithIncremental(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(CHARGES, state.get(CHARGES) + 1), 3);
        world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    public static void chargeWithNetherStar(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(CHARGES, 4), 3);
        world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);
        switch (getCharges(state)) {
            case 1 -> dropStack((World) world, pos, new ItemStack(ArtifalityBlocks.INCREMENTAL_ORB));
            case 2 -> dropStack((World) world, pos, new ItemStack(ArtifalityBlocks.INCREMENTAL_ORB, 2));
            case 3 -> dropStack((World) world, pos, new ItemStack(ArtifalityBlocks.INCREMENTAL_ORB, 3));
            case 4 -> dropStack((World) world, pos, new ItemStack(Items.NETHER_STAR));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CHARGES);
    }
}
