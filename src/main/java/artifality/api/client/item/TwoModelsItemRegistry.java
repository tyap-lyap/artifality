package artifality.api.client.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class TwoModelsItemRegistry {

    private static final Map<Identifier, Item> ENTRIES = new LinkedHashMap<>();

    public static void register(Identifier id, Item item){

        ENTRIES.put(id, item);

    }

    public static Map<Identifier, Item> getEntries(){
        return ENTRIES;
    }
}
