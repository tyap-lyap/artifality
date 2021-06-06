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

    public static final ArrayList<BlockState> CRYSTALS = new ArrayList<>();

    public CrystalFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        boolean generated = false;

        BlockState crystal = CRYSTALS.get(random.nextInt(CRYSTALS.size()));
        for (int i = 0; i < 8; i++) {
            int x = pos.getX() + random.nextInt(6);
            int z = pos.getZ() + random.nextInt(6);
            int y = random.nextInt(8) + 11;
            BlockPos spawnPos = new BlockPos(x, y, z);
            if(world.isAir(spawnPos)){

                if (isStone(world.getBlockState(spawnPos.down()).getBlock())) {
                    setBlockState(world, spawnPos, crystal);
                    generated = true;

                }else if(isStone(world.getBlockState(spawnPos.add(0, 0, -1)).getBlock())){
                    setBlockState(world, spawnPos, crystal.with(Properties.FACING, Direction.SOUTH));
                    generated = true;

                }else if(isStone(world.getBlockState(spawnPos.add(0, 0, 1)).getBlock())){
                    setBlockState(world, spawnPos, crystal.with(Properties.FACING, Direction.NORTH));
                    generated = true;

                }else if(isStone(world.getBlockState(spawnPos.add(-1, 0, 0)).getBlock())){
                    setBlockState(world, spawnPos, crystal.with(Properties.FACING, Direction.EAST));
                    generated = true;

                }else if(isStone(world.getBlockState(spawnPos.add(1, 0, 0)).getBlock())){
                    setBlockState(world, spawnPos, crystal.with(Properties.FACING, Direction.WEST));
                    generated = true;

                }else if(isStone(world.getBlockState(spawnPos.up()).getBlock())){
                    setBlockState(world, spawnPos, crystal.with(Properties.FACING, Direction.DOWN));
                    generated = true;
                }
            }
        }
        return generated;
    }

    static {
        CRYSTALS.add(ArtifalityBlocks.INCREMENTAL_CRYSTAL.getDefaultState());
        CRYSTALS.add(ArtifalityBlocks.LUNAR_CRYSTAL_CRYSTAL.getDefaultState());
    }
}
