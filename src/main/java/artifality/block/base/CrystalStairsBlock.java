package artifality.block.base;

import artifality.registry.ArtifalityBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.block.enums.StairShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

/**
 * @author github.com/Wurst-Imperium/Mo-Glass/
 */
public class CrystalStairsBlock extends StairsBlock {

    public CrystalStairsBlock(Settings settings) {
        super(ArtifalityBlocks.INCREMENTAL_CRYSTAL_BLOCK.getDefaultState(), settings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if(stateFrom.getBlock() instanceof CrystalBlock)
            return true;

        if(stateFrom.getBlock() instanceof CrystalSlabBlock)
            if(isInvisibleToSlab(state, stateFrom, direction))
                return true;

        if(stateFrom.getBlock() == this)
            if(isInvisibleToStairs(state, stateFrom,
                    direction))
                return true;

        return super.isSideInvisible(state, stateFrom, direction);
    }

    private boolean isInvisibleToSlab(BlockState state, BlockState stateFrom, Direction direction) {
        BlockHalf half = state.get(StairsBlock.HALF);
        Direction facing = state.get(StairsBlock.FACING);
        StairShape shape = state.get(StairsBlock.SHAPE);
        SlabType type = stateFrom.get(SlabBlock.TYPE);

        if(direction == Direction.UP)
            if(type != SlabType.TOP)
                return true;

        if(direction == Direction.DOWN)
            if(type != SlabType.BOTTOM)
                return true;

        if(type == SlabType.DOUBLE)
            return true;

        // front
        if(direction == facing.getOpposite())
            if(type == SlabType.BOTTOM && half == BlockHalf.BOTTOM)
                return true;
            else if(type == SlabType.TOP && half == BlockHalf.TOP)
                return true;

        // right
        if(direction == facing.rotateYClockwise()
                && shape == StairShape.OUTER_LEFT)
            if(type == SlabType.BOTTOM && half == BlockHalf.BOTTOM)
                return true;
            else if(type == SlabType.TOP && half == BlockHalf.TOP)
                return true;

        // left
        if(direction == facing.rotateYCounterclockwise()
                && shape == StairShape.OUTER_RIGHT)
            if(type == SlabType.BOTTOM && half == BlockHalf.BOTTOM)
                return true;
            else return type == SlabType.TOP && half == BlockHalf.TOP;

        return false;
    }

    private boolean isInvisibleToStairs(BlockState state, BlockState stateFrom, Direction direction) {
        BlockHalf half = state.get(StairsBlock.HALF);
        BlockHalf halfFrom = stateFrom.get(StairsBlock.HALF);
        Direction facing = state.get(StairsBlock.FACING);
        Direction facingFrom = stateFrom.get(StairsBlock.FACING);
        StairShape shape = state.get(StairsBlock.SHAPE);
        StairShape shapeFrom = stateFrom.get(StairsBlock.SHAPE);

        // up
        if(direction == Direction.UP)
            if(halfFrom == BlockHalf.BOTTOM)
                return true;
            else if(half != halfFrom)
                if(facing == facingFrom && shape == shapeFrom)
                    return true;
                else switch(shape) {
                        case STRAIGHT:
                            if(shapeFrom == StairShape.INNER_LEFT
                                    && (facingFrom == facing
                                    || facingFrom == facing.rotateYClockwise()))
                                return true;
                            else if(shapeFrom == StairShape.INNER_RIGHT
                                    && (facingFrom == facing || facingFrom == facing
                                    .rotateYCounterclockwise()))
                                return true;
                            break;

                        case INNER_LEFT:
                            if(shapeFrom == StairShape.INNER_RIGHT
                                    && facingFrom == facing.rotateYCounterclockwise())
                                return true;
                            break;

                        case INNER_RIGHT:
                            if(shapeFrom == StairShape.INNER_LEFT
                                    && facingFrom == facing.rotateYClockwise())
                                return true;
                            break;

                        case OUTER_LEFT:
                            if(shapeFrom == StairShape.OUTER_RIGHT
                                    && facingFrom == facing.rotateYCounterclockwise())
                                return true;
                            else if(shapeFrom == StairShape.STRAIGHT
                                    && (facingFrom == facing || facingFrom == facing
                                    .rotateYCounterclockwise()))
                                return true;
                            break;

                        case OUTER_RIGHT:
                            if(shapeFrom == StairShape.OUTER_LEFT
                                    && facingFrom == facing.rotateYClockwise())
                                return true;
                            else if(shapeFrom == StairShape.STRAIGHT
                                    && (facingFrom == facing
                                    || facingFrom == facing.rotateYClockwise()))
                                return true;
                            break;
                    }

        // down
        if(direction == Direction.DOWN)
            if(halfFrom == BlockHalf.TOP)
                return true;
            else
                switch(shape)
                {
                    case STRAIGHT:
                        if(shapeFrom == StairShape.INNER_LEFT && (facingFrom == facing
                                || facingFrom == facing.rotateYClockwise()))
                            return true;
                        else if(shapeFrom == StairShape.INNER_RIGHT
                                && (facingFrom == facing
                                || facingFrom == facing.rotateYCounterclockwise()))
                            return true;
                        break;

                    case INNER_LEFT:
                        if(shapeFrom == StairShape.INNER_RIGHT
                                && facingFrom == facing.rotateYCounterclockwise())
                            return true;
                        break;

                    case INNER_RIGHT:
                        if(shapeFrom == StairShape.INNER_LEFT
                                && facingFrom == facing.rotateYClockwise())
                            return true;
                        break;

                    case OUTER_LEFT:
                        if(shapeFrom == StairShape.OUTER_RIGHT
                                && facingFrom == facing.rotateYCounterclockwise())
                            return true;
                        else if(shapeFrom == StairShape.STRAIGHT && (facingFrom == facing
                                || facingFrom == facing.rotateYCounterclockwise()))
                            return true;
                        break;

                    case OUTER_RIGHT:
                        if(shapeFrom == StairShape.OUTER_LEFT
                                && facingFrom == facing.rotateYClockwise())
                            return true;
                        else if(shapeFrom == StairShape.STRAIGHT && (facingFrom == facing
                                || facingFrom == facing.rotateYClockwise()))
                            return true;
                        break;
                }

        // other stairs rear
        if(facingFrom == direction.getOpposite())
            return true;

        // rear
        if(direction == facing)
            if(half == halfFrom && shape != StairShape.STRAIGHT)
                if(facingFrom == facing.rotateYCounterclockwise()
                        && shapeFrom != StairShape.OUTER_RIGHT)
                    return true;
                else if(facingFrom == facing.rotateYClockwise()
                        && shapeFrom != StairShape.OUTER_LEFT)
                    return true;

        // front
        if(direction == facing.getOpposite())
            if(half == halfFrom)
                if(facingFrom == facing.rotateYCounterclockwise()
                        && shapeFrom != StairShape.OUTER_LEFT)
                    return true;
                else if(facingFrom == facing.rotateYClockwise()
                        && shapeFrom != StairShape.OUTER_RIGHT)
                    return true;

        // left
        if(direction == facing.rotateYCounterclockwise())
            if(half == halfFrom)
                if(facingFrom == direction && shape != StairShape.INNER_LEFT
                        && shapeFrom == StairShape.INNER_RIGHT)
                    return true;
                else if(facingFrom == facing && shapeFrom != StairShape.OUTER_LEFT)
                    return true;
                else if(facingFrom == facing.getOpposite()
                        && shape == StairShape.OUTER_RIGHT)
                    return true;

        // right
        if(direction == facing.rotateYClockwise())
            if(half == halfFrom)
                if(facingFrom == direction && shape != StairShape.INNER_RIGHT
                        && shapeFrom == StairShape.INNER_LEFT)
                    return true;
                else if(facingFrom == facing && shapeFrom != StairShape.OUTER_RIGHT)
                    return true;
                else return facingFrom == facing.getOpposite() && shape == StairShape.OUTER_LEFT;

        return false;
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
}
