package artifality.item;

public class CatEarsItem extends BaseItem {
    public CatEarsItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public String getDescription() {
        return "Decreased explosion damage, when active all creepers in zone will run away from u.";
    }
}

