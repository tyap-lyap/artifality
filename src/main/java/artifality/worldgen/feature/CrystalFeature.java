package artifality.worldgen.feature;

import artifality.block.ArtifalityBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CrystalFeature extends Feature<DefaultFeatureConfig> {

    public static final ArrayList<BlockState> CRYSTALS = new ArrayList<>(Arrays.asList(
            ArtifalityBlocks.INCREMENTAL_CLUSTER.getDefaultState(),
            ArtifalityBlocks.LUNAMENTAL_CLUSTER.getDefaultState(),
            ArtifalityBlocks.LOVEMENTAL_CLUSTER.getDefaultState()));

    public CrystalFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        boolean generated = false;

        BlockState crystal = CRYSTALS.get(context.getRandom().nextInt(CRYSTALS.size()));
        for (int i = 0; i < 24; i++) {
            int x = context.getOrigin().getX() + context.getRandom().nextInt(6);
            int z = context.getOrigin().getZ() + context.getRandom().nextInt(6);
            int y = context.getRandom().nextInt(8) + 11;
            BlockPos spawnPos = new BlockPos(x, y, z);
            StructureWorldAccess world = context.getWorld();
            if(world.isAir(spawnPos)){
                if (isStone(world.getBlockState(spawnPos.down()))) {
                    setCrystal(world, spawnPos, crystal, context.getRandom());
                    setBlockState(world, spawnPos.down(), getBuddingCrystal(crystal));
                    generated = true;

                }else if(isStone(world.getBlockState(spawnPos.add(0, 0, -1)))){
                    setCrystal(world, spawnPos, crystal.with(Properties.FACING, Direction.SOUTH), context.getRandom());
                    setBlockState(world, spawnPos.add(0, 0, -1), getBuddingCrystal(crystal));
                    generated = true;

                }else if(isStone(world.getBlockState(spawnPos.add(0, 0, 1)))){
                    setCrystal(world, spawnPos, crystal.with(Properties.FACING, Direction.NORTH), context.getRandom());
                    setBlockState(world, spawnPos.add(0, 0, 1), getBuddingCrystal(crystal));
                    generated = true;

                }else if(isStone(world.getBlockState(spawnPos.add(-1, 0, 0)))){
                    setCrystal(world, spawnPos, crystal.with(Properties.FACING, Direction.EAST), context.getRandom());
                    setBlockState(world, spawnPos.add(-1, 0, 0), getBuddingCrystal(crystal));
                    generated = true;

                }else if(isStone(world.getBlockState(spawnPos.add(1, 0, 0)))){
                    setCrystal(world, spawnPos, crystal.with(Properties.FACING, Direction.WEST), context.getRandom());
                    setBlockState(world, spawnPos.add(1, 0, 0), getBuddingCrystal(crystal));
                    generated = true;

                }else if(isStone(world.getBlockState(spawnPos.up()))){
                    setCrystal(world, spawnPos, crystal.with(Properties.FACING, Direction.DOWN), context.getRandom());
                    setBlockState(world, spawnPos.up(), getBuddingCrystal(crystal));
                    generated = true;
                }
            }
        }
        return generated;
    }

    public static void setCrystal(StructureWorldAccess world, BlockPos pos, BlockState state, Random random){
        if(state.isOf(ArtifalityBlocks.INCREMENTAL_CLUSTER)){
            int randomInt = random.nextInt(3);
            if(randomInt == 0){
                world.setBlockState(pos, state, 3);
            }else if(randomInt == 1){
                world.setBlockState(pos, ArtifalityBlocks.MEDIUM_INCREMENTAL_CLUSTER.getStateWithProperties(state), 3);
            }else{
                world.setBlockState(pos, ArtifalityBlocks.SMALL_INCREMENTAL_CLUSTER.getStateWithProperties(state), 3);
            }
        }
        else if(state.isOf(ArtifalityBlocks.LOVEMENTAL_CLUSTER)){
            int randomInt = random.nextInt(3);
            if(randomInt == 0){
                world.setBlockState(pos, state, 3);
            }else if(randomInt == 1){
                world.setBlockState(pos, ArtifalityBlocks.MEDIUM_LOVEMENTAL_CLUSTER.getStateWithProperties(state), 3);
            }else{
                world.setBlockState(pos, ArtifalityBlocks.SMALL_LOVEMENTAL_CLUSTER.getStateWithProperties(state), 3);
            }
        }
        else if(state.isOf(ArtifalityBlocks.LUNAMENTAL_CLUSTER)){
            int randomInt = random.nextInt(3);
            if(randomInt == 0){
                world.setBlockState(pos, state, 3);
            }else if(randomInt == 1){
                world.setBlockState(pos, ArtifalityBlocks.MEDIUM_LUNAMENTAL_CLUSTER.getStateWithProperties(state), 3);
            }else{
                world.setBlockState(pos, ArtifalityBlocks.SMALL_LUNAMENTAL_CLUSTER.getStateWithProperties(state), 3);
            }
        }
    }

    public static BlockState getBuddingCrystal(BlockState state){
        if(state.isOf(ArtifalityBlocks.INCREMENTAL_CLUSTER)){
            return ArtifalityBlocks.BUDDING_INCREMENTAL.getDefaultState();
        }
        else if(state.isOf(ArtifalityBlocks.LOVEMENTAL_CLUSTER)){
            return ArtifalityBlocks.BUDDING_LOVEMENTAL.getDefaultState();
        }
        else if(state.isOf(ArtifalityBlocks.LUNAMENTAL_CLUSTER)){
            return ArtifalityBlocks.BUDDING_LUNAMENTAL.getDefaultState();
        }else return Blocks.STONE.getDefaultState();
    }

    public static boolean isStone(BlockState state){
        return state.isOf(Blocks.STONE);
    }
}
