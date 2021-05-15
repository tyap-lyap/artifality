package artifality.item;

import artifality.interfaces.IItemModel;
import net.minecraft.item.Item;

public class BaseItem extends Item implements IItemModel {

    private final String parentModel;

    public BaseItem(Settings settings) {
        super(settings);
        this.parentModel = "generated";
    }
    public BaseItem(Settings settings, String parentModel) {
        super(settings);
        this.parentModel = parentModel;
    }

    @Override
    public String getParentModel() {
        return parentModel;
    }
}
