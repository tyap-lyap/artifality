package artifality.worldgen.feature;

import artifality.enums.CrystalClusterPack;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class CrystalFeature extends Feature<DefaultFeatureConfig> {

    public CrystalFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        boolean generated = false;
        var pack = CrystalClusterPack.LIST.get(context.getRandom().nextInt(CrystalClusterPack.LIST.size()));
        for (int i = 0; i < 24; i++) {
            int x = context.getOrigin().getX() + context.getRandom().nextInt(6);
            int z = context.getOrigin().getZ() + context.getRandom().nextInt(6);
            int y = context.getRandom().nextInt(8) + 11;
            BlockPos pos = new BlockPos(x, y, z);
            StructureWorldAccess world = context.getWorld();
            if(world.isAir(pos)){
                if (isStone(world.getBlockState(pos.down()))) {
                    BlockState cluster = pack.getRandomCluster().getDefaultState();
                    setBlockState(world, pos, cluster);
                    setBlockState(world, pos.down(), pack.budding().getDefaultState());
                    generated = true;

                }else if(isStone(world.getBlockState(pos.add(0, 0, -1)))){
                    BlockState cluster = pack.getRandomCluster().getDefaultState().with(Properties.FACING, Direction.SOUTH);
                    setBlockState(world, pos, cluster);
                    setBlockState(world, pos.add(0, 0, -1), pack.budding().getDefaultState());
                    generated = true;

                }else if(isStone(world.getBlockState(pos.add(0, 0, 1)))){
                    BlockState cluster = pack.getRandomCluster().getDefaultState().with(Properties.FACING, Direction.NORTH);
                    setBlockState(world, pos, cluster);
                    setBlockState(world, pos.add(0, 0, 1), pack.budding().getDefaultState());
                    generated = true;

                }else if(isStone(world.getBlockState(pos.add(-1, 0, 0)))){
                    BlockState cluster = pack.getRandomCluster().getDefaultState().with(Properties.FACING, Direction.EAST);
                    setBlockState(world, pos, cluster);
                    setBlockState(world, pos.add(-1, 0, 0), pack.budding().getDefaultState());
                    generated = true;

                }else if(isStone(world.getBlockState(pos.add(1, 0, 0)))){
                    BlockState cluster = pack.getRandomCluster().getDefaultState().with(Properties.FACING, Direction.WEST);
                    setBlockState(world, pos, cluster);
                    setBlockState(world, pos.add(1, 0, 0), pack.budding().getDefaultState());
                    generated = true;

                }else if(isStone(world.getBlockState(pos.up()))){
                    BlockState cluster = pack.getRandomCluster().getDefaultState().with(Properties.FACING, Direction.DOWN);
                    setBlockState(world, pos, cluster);
                    setBlockState(world, pos.up(), pack.budding().getDefaultState());
                    generated = true;
                }
            }
        }
        return generated;
    }

    public static boolean isStone(BlockState state){
        return state.isOf(Blocks.STONE);
    }
}
