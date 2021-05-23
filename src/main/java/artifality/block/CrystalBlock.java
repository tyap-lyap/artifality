package artifality.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class CrystalBlock extends BaseBlock {
    public static final DirectionProperty FACING = Properties.FACING;

    protected static final VoxelShape SHAPE_UP = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    protected static final VoxelShape SHAPE_DOWN = Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    protected static final VoxelShape SHAPE_SOUTH = Block.createCuboidShape(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 12.0D);
    protected static final VoxelShape SHAPE_NORTH = Block.createCuboidShape(2.0D, 2.0D, 4.0D, 14.0D, 14.0D, 16.0D);

    protected static final VoxelShape SHAPE_EAST = Block.createCuboidShape(0.0D, 2.0D, 14.0D, 12.0D, 14.0D, 2.0D);
    protected static final VoxelShape SHAPE_WEST = Block.createCuboidShape(4.0D, 2.0D, 14.0D, 16.0D, 14.0D, 2.0D);

    public CrystalBlock(Settings settings, String name) {
        super(settings, "cross", name);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(FACING, mirror.apply(state.get(FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch((state.get(FACING))) {
            case UP:
            default:
                return SHAPE_UP;
            case DOWN:
                return SHAPE_DOWN;
            case SOUTH:
                return SHAPE_SOUTH;
            case NORTH:
                return SHAPE_NORTH;
            case EAST:
                return SHAPE_EAST;
            case WEST:
                return SHAPE_WEST;
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        return this.getDefaultState().with(FACING, direction);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
