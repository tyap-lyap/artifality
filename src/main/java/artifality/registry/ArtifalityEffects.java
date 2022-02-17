package artifality.registry;

import artifality.ArtifalityMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArtifalityEffects {
    public static final Map<Identifier, StatusEffect> EFFECTS = new LinkedHashMap<>();

    private static StatusEffect add(String name, StatusEffect effect) {
        EFFECTS.put(ArtifalityMod.locate(name), effect);
        return effect;
    }

    public static void init() {
        EFFECTS.forEach((id, effect) -> Registry.register(Registry.STATUS_EFFECT, id, effect));
    }
}
