package artifality.block.base;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class LensBlock extends ArtifalityBaseBlock {
    private static final VoxelShape SHAPE = createCuboidShape(0, 0, 0, 16, 8, 16);

    public LensBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public void applyLensEffect(StatusEffectInstance effectInstance, PlayerEntity playerEntity){
    }
}
