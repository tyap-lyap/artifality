package artifality.worldgen.feature;

import artifality.block.ArtifalityBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.ArrayList;
import java.util.Random;

public class CrystalFeature extends Feature<DefaultFeatureConfig> {

    private static final ArrayList<BlockState> CRYSTALS = new ArrayList<>();

    public CrystalFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        boolean any = false;
        for (int i = 0; i < 20; i++) {
            int x = pos.getX() + random.nextInt(8);
            int z = pos.getZ() + random.nextInt(8);
            int y = random.nextInt(8) + 11;
            BlockPos spawnPos = new BlockPos(x, y, z);
            BlockState crystal = ArtifalityBlocks.INCREMENTAL_CRYSTAL.getDefaultState();
            if(world.isAir(spawnPos)){

                if (isStone(world.getBlockState(spawnPos.down()).getBlock())) {
                    setBlockState(world, spawnPos, crystal);
                    any = true;

                }else if(isStone(world.getBlockState(spawnPos.add(0, 0, -1)).getBlock())){
                    setBlockState(world, spawnPos, crystal.with(Properties.FACING, Direction.SOUTH));
                    any = true;

                }else if(isStone(world.getBlockState(spawnPos.add(0, 0, 1)).getBlock())){
                    setBlockState(world, spawnPos, crystal.with(Properties.FACING, Direction.NORTH));
                    any = true;

                }else if(isStone(world.getBlockState(spawnPos.add(-1, 0, 0)).getBlock())){
                    setBlockState(world, spawnPos, crystal.with(Properties.FACING, Direction.EAST));
                    any = true;

                }else if(isStone(world.getBlockState(spawnPos.add(1, 0, 0)).getBlock())){
                    setBlockState(world, spawnPos, crystal.with(Properties.FACING, Direction.WEST));
                    any = true;

                }else if(isStone(world.getBlockState(spawnPos.up()).getBlock())){
                    setBlockState(world, spawnPos, crystal.with(Properties.FACING, Direction.DOWN));
                    any = true;
                }
            }
        }
        return any;
    }
}
