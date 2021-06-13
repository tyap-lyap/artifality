package artifality.item;

import artifality.item.base.BaseItem;

public class CatEarsItem extends BaseItem {

    public CatEarsItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public String getDescription() {
        return "Decreased explosion damage,\nwhen active all creepers in\nzone will run away from you.";
    }
}

