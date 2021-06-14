package artifality.block;

import artifality.block.base.BaseBlock;
import artifality.client.particle.ArtifalityParticles;
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
import net.minecraft.world.World;

import java.util.Random;

public class CrystalBlock extends BaseBlock {
    public static final DirectionProperty FACING = Properties.FACING;

    protected static final VoxelShape NORTH_SHAPE = createCuboidShape(3, 3, 9, 13, 13, 16);
    protected static final VoxelShape SOUTH_SHAPE = createCuboidShape(3, 3, 0, 13, 13, 7);
    protected static final VoxelShape EAST_SHAPE = createCuboidShape(0, 3, 3, 7, 13, 13);
    protected static final VoxelShape WEST_SHAPE = createCuboidShape(9, 3, 3, 16, 13, 13);
    protected static final VoxelShape UP_SHAPE = createCuboidShape(3, 0, 3, 13, 7, 13);
    protected static final VoxelShape DOWN_SHAPE = createCuboidShape(3, 9, 3, 13, 16, 13);

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
                return UP_SHAPE;
            case DOWN:
                return DOWN_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case NORTH:
                return NORTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
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

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        if(random.nextFloat() > 0.5){

            world.addParticle(ArtifalityParticles.CRYSTAL_SPARKLE, pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(), 0, 0, 0);
        }
    }
}
