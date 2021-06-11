package artifality.block.base;

import artifality.interfaces.ModelProvider;
import artifality.interfaces.Translatable;
import net.minecraft.block.Block;


public class BaseBlock extends Block implements ModelProvider, Translatable {

    private final String parentModel;
    private final String name;

    public BaseBlock(Settings settings, String parentModel, String name) {
        super(settings);
        this.parentModel = parentModel;
        this.name = name;
    }

    public BaseBlock(Settings settings, String name) {
        super(settings);
        this.parentModel = "cube_all";
        this.name = name;
    }

    @Override
    public String getParentModel() {
        return parentModel;
    }

    @Override
    public String getOriginName() {
        return name;
    }
}
