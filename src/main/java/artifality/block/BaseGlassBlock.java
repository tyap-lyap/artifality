package artifality.block;

import artifality.interfaces.IArtifalityBlock;
import net.minecraft.block.GlassBlock;

public class BaseGlassBlock extends GlassBlock implements IArtifalityBlock {

    private final String parentModel;
    private final String name;

    public BaseGlassBlock(Settings settings, String parentModel, String name) {
        super(settings);
        this.parentModel = parentModel;
        this.name = name;
    }

    public BaseGlassBlock(Settings settings, String name) {
        super(settings);
        this.parentModel = "cube_all";
        this.name = name;
    }

    @Override
    public String getParentModel() {
        return parentModel;
    }

    @Override
    public String getTranslation() {
        return name;
    }
}
