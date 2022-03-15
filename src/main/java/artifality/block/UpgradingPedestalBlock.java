package artifality.block;

import artifality.block.base.BaseBlock;
import artifality.item.base.ArtifactItem;
import artifality.registry.ArtifalityBlocks;
import artifality.util.TiersUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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
        this.setDefaultState(this.getStateManager().getDefaultState().with(CHARGES, 0));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient || hand != Hand.MAIN_HAND) return ActionResult.PASS;

        ItemStack stack = player.getStackInHand(hand);
        Item item = stack.getItem();

        if(stack.isOf(ArtifalityBlocks.INCREMENTAL_ORB.asItem()) && getCharges(state) < 3) {
            charge(world, pos, state, getCharges(state) + 1);
            stack.decrement(1);
            return ActionResult.SUCCESS;
        }
        else if(stack.isOf(Items.NETHER_STAR) && getCharges(state) != 4) {
            charge(world, pos, state, 4);
            stack.decrement(1);
            return ActionResult.SUCCESS;
        }
        else if(item instanceof ArtifactItem artifact && artifact.config.hasTiers) {
            if(getCharges(state) == 3 && TiersUtils.getTier(stack) == 1) {
                world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                player.setStackInHand(Hand.MAIN_HAND, TiersUtils.withTier(item, 2));
                world.setBlockState(pos, this.getDefaultState());
                return ActionResult.SUCCESS;
            }
            else if(getCharges(state) == 4 && TiersUtils.getTier(stack) == 2) {
                world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                player.setStackInHand(Hand.MAIN_HAND, TiersUtils.withTier(item, 3));
                world.setBlockState(pos, this.getDefaultState());
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    private static int getCharges(BlockState state) {
        return state.get(CHARGES);
    }

    public static void charge(World world, BlockPos pos, BlockState state, int level) {
        world.setBlockState(pos, state.with(CHARGES, level), 3);
        world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void onBroken(WorldAccess worldAccess, BlockPos pos, BlockState state) {
        super.onBroken(worldAccess, pos, state);
        if(worldAccess instanceof World world) {
            switch (getCharges(state)) {
                case 1 -> dropStack(world, pos, new ItemStack(ArtifalityBlocks.INCREMENTAL_ORB));
                case 2 -> dropStack(world, pos, new ItemStack(ArtifalityBlocks.INCREMENTAL_ORB, 2));
                case 3 -> dropStack(world, pos, new ItemStack(ArtifalityBlocks.INCREMENTAL_ORB, 3));
                case 4 -> dropStack(world, pos, new ItemStack(Items.NETHER_STAR));
            }
        }

    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CHARGES);
    }

}
