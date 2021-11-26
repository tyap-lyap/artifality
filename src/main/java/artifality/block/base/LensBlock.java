package artifality.block.base;

import artifality.list.LensEffects;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class LensBlock extends BaseBlock {
    private static final VoxelShape SHAPE = createCuboidShape(0, 0, 0, 16, 8, 16);
    private final LensEffects.LensEffect lensEffect;

    public LensBlock(LensEffects.LensEffect effect) {
        super(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.NETHERITE).nonOpaque());
        this.lensEffect = effect;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public void applyLensEffect(StatusEffectInstance effectInstance, PlayerEntity playerEntity){
        lensEffect.apply(effectInstance, playerEntity);
    }
}
