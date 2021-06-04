package artifality.worldgen.feature;

import artifality.block.ArtifalityBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class CrystalFeature extends Feature<CrystalFeatureConfig> {

    public CrystalFeature() {
        super(CrystalFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random rand, BlockPos pos, CrystalFeatureConfig config) {
        boolean any = false;
        for (int i = 0; i < config.getPatchSize(); i++) {
            int x = pos.getX() + rand.nextInt(16) + 8;
            int z = pos.getZ() + rand.nextInt(16) + 8;
            int y = rand.nextInt(26) + 4;
            BlockPos pos3 = new BlockPos(x, y, z);
            BlockState crystal = ArtifalityBlocks.INCREMENTAL_CRYSTAL.getDefaultState();
            if (world.isAir(pos3) && !world.isAir(pos3.down())) {
                world.setBlockState(pos3, crystal, 2);
                any = true;
            }
        }

        return any;
    }
}
