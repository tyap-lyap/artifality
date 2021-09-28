package artifality.block;

import artifality.block.base.BuddingCrystalBlock;
import artifality.block.base.ClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class BuddingIncrementalBlock extends BuddingCrystalBlock {

    public BuddingIncrementalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(4) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;
            if (blockState.isAir()) {
                block = ArtifalityBlocks.SMALL_INCREMENTAL_CLUSTER;
            } else if (blockState.isOf(ArtifalityBlocks.SMALL_INCREMENTAL_CLUSTER) && blockState.get(ClusterBlock.FACING) == direction) {
                block = ArtifalityBlocks.MEDIUM_INCREMENTAL_CLUSTER;
            } else if (blockState.isOf(ArtifalityBlocks.MEDIUM_INCREMENTAL_CLUSTER) && blockState.get(ClusterBlock.FACING) == direction) {
                block = ArtifalityBlocks.INCREMENTAL_CLUSTER;
            }

            if (block != null) {
                BlockState crystal = block.getDefaultState().with(ClusterBlock.FACING, direction);
                world.setBlockState(blockPos, crystal);
            }
        }
    }
}
