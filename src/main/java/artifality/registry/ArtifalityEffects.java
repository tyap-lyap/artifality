package artifality.registry;

import artifality.ArtifalityMod;
import artifality.effect.BaseStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArtifalityEffects {
    public static final Map<Identifier, StatusEffect> EFFECTS = new LinkedHashMap<>();

    public static final StatusEffect FALL_DAMAGE_IMMUNITY = add("fall_damage_immunity", new BaseStatusEffect(StatusEffectCategory.BENEFICIAL, 8171462));

    private static StatusEffect add(String name, StatusEffect effect) {
        EFFECTS.put(ArtifalityMod.id(name), effect);
        return effect;
    }

    public static void init() {
        EFFECTS.forEach((id, effect) -> Registry.register(Registries.STATUS_EFFECT, id, effect));
    }
}
