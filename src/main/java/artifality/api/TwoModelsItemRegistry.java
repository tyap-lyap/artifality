package artifality.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class TwoModelsItemRegistry {
    public static final Map<Identifier, Item> ENTRIES = new LinkedHashMap<>();

    /**
     * Registered items will have secondary
     * model in an entity hand loaded from
     * assets/%modid%/models/item/%item_name%_in_hand.json
     *
     * @param items items to be registered
     */
    public static void register(Item... items) {
        for(Item item : items) {
            Identifier id = Registry.ITEM.getId(item);
            ENTRIES.put(id, item);
        }
    }

}
