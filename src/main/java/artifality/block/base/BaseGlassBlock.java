package artifality.block.base;

import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;
import ru.bclib.blocks.BaseBlock;
import ru.bclib.client.models.BlockModelProvider;
import ru.bclib.client.render.BCLRenderLayer;
import ru.bclib.interfaces.IRenderTyped;

public class BaseGlassBlock extends BaseBlock implements BlockModelProvider, IRenderTyped {

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
