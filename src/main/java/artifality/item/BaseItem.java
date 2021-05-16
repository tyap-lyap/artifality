package artifality.item;

import artifality.interfaces.IArtifalityItem;
import net.minecraft.item.Item;

public class BaseItem extends Item implements IArtifalityItem {

    private final String parentModel;

    private final String name;

    public BaseItem(Settings settings, String name) {
        super(settings);
        this.parentModel = "generated";
        this.name = name;
    }
    public BaseItem(Settings settings, String parentModel, String name) {
        super(settings);
        this.parentModel = parentModel;
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
