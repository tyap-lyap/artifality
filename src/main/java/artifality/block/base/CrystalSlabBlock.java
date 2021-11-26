package artifality.block.base;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

/**
 * credits to github.com/Wurst-Imperium/Mo-Glass/
 */
public final class CrystalSlabBlock extends SlabBlock {

    public CrystalSlabBlock(Settings settings) {
        super(settings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if(stateFrom.getBlock() instanceof CrystalBlock)
            return true;

        if(stateFrom.getBlock() == this)
            if(isInvisibleToGlassSlab(state, stateFrom, direction))
                return true;

        if(stateFrom.getBlock() instanceof CrystalStairsBlock)
            if(isInvisibleToGlassStairs(state, stateFrom,
                    direction))
                return true;

        return super.isSideInvisible(state, stateFrom, direction);
    }

    private boolean isInvisibleToGlassSlab(BlockState state, BlockState stateFrom, Direction direction) {
        SlabType type = state.get(SlabBlock.TYPE);
        SlabType typeFrom = stateFrom.get(SlabBlock.TYPE);

        if(typeFrom == SlabType.DOUBLE)
            return true;

        switch(direction)
        {
            case UP:
            case DOWN:
                if(type != typeFrom)
                    return true;
                break;

            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                if(type == typeFrom)
                    return true;
                break;
        }

        return false;
    }

    private boolean isInvisibleToGlassStairs(BlockState state, BlockState stateFrom, Direction direction) {
        SlabType type = state.get(SlabBlock.TYPE);
        BlockHalf halfFrom = stateFrom.get(StairsBlock.HALF);
        Direction facingFrom = stateFrom.get(StairsBlock.FACING);

        // up
        if(direction == Direction.UP)
            if(halfFrom == BlockHalf.BOTTOM)
                return true;

        // down
        if(direction == Direction.DOWN)
            if(halfFrom == BlockHalf.TOP)
                return true;

        // other stairs rear
        if(facingFrom == direction.getOpposite())
            return true;

        // sides
        if(direction.getHorizontal() != -1)
            if(type == SlabType.BOTTOM && halfFrom == BlockHalf.BOTTOM)
                return true;
            else return type == SlabType.TOP && halfFrom == BlockHalf.TOP;
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
