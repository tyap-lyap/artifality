package artifality.block.base;

import artifality.ArtifalityMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import ru.bclib.client.models.ModelsHelper;
import ru.bclib.client.models.PatternsHelper;
import ru.bclib.client.render.BCLRenderLayer;
import ru.bclib.interfaces.IRenderTyped;

import java.util.Optional;

public class LensBlock extends BasedBlock implements IRenderTyped {

    public final static Identifier LENS = ArtifalityMod.newId("patterns/block/lens.json");

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

    @Override
    public BCLRenderLayer getRenderLayer() {
        return BCLRenderLayer.TRANSLUCENT;
    }

    @Override
    public @Nullable JsonUnbakedModel getBlockModel(Identifier id, BlockState blockState) {
        Optional<String> pattern = PatternsHelper.createJson(LENS, id);
        return ModelsHelper.fromPattern(pattern);
    }
}
