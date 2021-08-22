package artifality.block.base;

import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;
import ru.bclib.blocks.BaseBlock;
import ru.bclib.client.render.BCLRenderLayer;
import ru.bclib.interfaces.BlockModelProvider;
import ru.bclib.interfaces.RenderLayerProvider;

public class BaseGlassBlock extends BaseBlock implements BlockModelProvider, RenderLayerProvider {

    public BaseGlassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public JsonUnbakedModel getItemModel(Identifier blockId) {
        return getBlockModel(blockId, getDefaultState());
    }

    @Override
    public BCLRenderLayer getRenderLayer() {
        return BCLRenderLayer.TRANSLUCENT;
    }
}
