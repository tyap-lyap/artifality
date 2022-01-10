package artifality.config;

import ru.pinkgoosik.goosikconfig.api.Config;

public class ArtifalityConfig extends Config {

    public ArtifalityConfig(String name) {
        super(name);
    }

    @Override
    public void init() {
        addInteger("chance", "invisibility_cape", 4);

        addInteger("chance", "ukulele", 3);

        addInteger("chance", "zeus_staff", 2);

        addInteger("chance", "forest_staff", 3);
        addInteger("chance", "harvest_staff", 3);
        addInteger("chance", "floral_staff", 3);

        addInteger("chance", "balloon", 4);
    }
}
