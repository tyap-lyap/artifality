package artifality.worldgen.feature;

import artifality.block.ArtifalityBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.Arrays;

public class CrystalFeature extends Feature<DefaultFeatureConfig> {

    public static final ArrayList<BlockState> CRYSTALS = new ArrayList<>(Arrays.asList(
            ArtifalityBlocks.INCREMENTAL_CLUSTER.getDefaultState(),
            ArtifalityBlocks.LUNAR_CRYSTAL_CLUSTER.getDefaultState(),
            ArtifalityBlocks.CRYSTAL_HEART_CLUSTER.getDefaultState()));

    public CrystalFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        boolean generated = false;

        BlockState crystal = CRYSTALS.get(context.getRandom().nextInt(CRYSTALS.size()));
        for (int i = 0; i < 16; i++) {
            int x = context.getOrigin().getX() + context.getRandom().nextInt(6);
            int z = context.getOrigin().getZ() + context.getRandom().nextInt(6);
            int y = context.getRandom().nextInt(8) + 11;
            BlockPos spawnPos = new BlockPos(x, y, z);
            if(context.getWorld().isAir(spawnPos)){

                if (isOverworldStone(context.getWorld().getBlockState(spawnPos.down()))) {
                    setBlockState(context.getWorld(), spawnPos, crystal);
                    generated = true;

                }else if(isOverworldStone(context.getWorld().getBlockState(spawnPos.add(0, 0, -1)))){
                    setBlockState(context.getWorld(), spawnPos, crystal.with(Properties.FACING, Direction.SOUTH));
                    generated = true;

                }else if(isOverworldStone(context.getWorld().getBlockState(spawnPos.add(0, 0, 1)))){
                    setBlockState(context.getWorld(), spawnPos, crystal.with(Properties.FACING, Direction.NORTH));
                    generated = true;

                }else if(isOverworldStone(context.getWorld().getBlockState(spawnPos.add(-1, 0, 0)))){
                    setBlockState(context.getWorld(), spawnPos, crystal.with(Properties.FACING, Direction.EAST));
                    generated = true;

                }else if(isOverworldStone(context.getWorld().getBlockState(spawnPos.add(1, 0, 0)))){
                    setBlockState(context.getWorld(), spawnPos, crystal.with(Properties.FACING, Direction.WEST));
                    generated = true;

                }else if(isOverworldStone(context.getWorld().getBlockState(spawnPos.up()))){
                    setBlockState(context.getWorld(), spawnPos, crystal.with(Properties.FACING, Direction.DOWN));
                    generated = true;
                }
            }
        }
        return generated;
    }

    public static boolean isOverworldStone(BlockState blockState){
        if(Registry.BLOCK.containsId(new Identifier("the_aether:holy_stone"))){
            Block holyStone = Registry.BLOCK.get(new Identifier("the_aether:holy_stone"));
            if(blockState.getBlock().equals(holyStone)) return false;
        }
        return isStone(blockState);
    }
}
