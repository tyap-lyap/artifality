package artifality.block;

import artifality.block.base.BaseBlock;
import artifality.block.entity.TradingPedestalBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TradingPedestalBlock extends BaseBlock implements BlockEntityProvider {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public TradingPedestalBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TradingPedestalBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof TradingPedestalBlockEntity be) {
//                player.sendMessage(Text.literal(be.sellingItem.getName().getString() + " x" + be.sellingItem.getCount()));
//                player.sendMessage(Text.literal(be.chargeItem.getName().getString() + " x" + be.chargeItem.getCount()));

            if(!be.sellingItem.isEmpty()) {
                if(!world.isClient()) {
                    dropStack(world, pos.up(), be.sellingItem.copy());
                    be.sellingItem = ItemStack.EMPTY;
                    be.updateListeners();
                }
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
