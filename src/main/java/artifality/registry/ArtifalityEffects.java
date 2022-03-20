package artifality.registry;

import artifality.ArtifalityMod;
import artifality.effect.BaseStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArtifalityEffects {
    public static final Map<Identifier, StatusEffect> EFFECTS = new LinkedHashMap<>();

    public static final StatusEffect FALL_DAMAGE_IMMUNITY = add("fall_damage_immunity", new BaseStatusEffect(StatusEffectType.BENEFICIAL, 8171462));

    private static StatusEffect add(String name, StatusEffect effect) {
        EFFECTS.put(ArtifalityMod.locate(name), effect);
        return effect;
    }

    public static void init() {
        EFFECTS.forEach((id, effect) -> Registry.register(Registry.STATUS_EFFECT, id, effect));
    }
}
