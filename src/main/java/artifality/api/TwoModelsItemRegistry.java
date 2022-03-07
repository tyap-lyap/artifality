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

    public static void register(Item... items) {
        for(Item item : items) {
            Identifier id = Registry.ITEM.getId(item);
            ENTRIES.put(id, item);
        }
    }
}
