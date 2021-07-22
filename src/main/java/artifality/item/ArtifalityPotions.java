package artifality.item;

import artifality.ArtifalityMod;
import artifality.entity.effect.ArtifalityEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ArtifalityPotions {

    private static final Map<Identifier, Potion> POTIONS = new LinkedHashMap<>();

    public static final Potion REBOUND = add("rebound", new Potion(new StatusEffectInstance(ArtifalityEffects.REBOUND, 3600)));
    public static final Potion LONG_REBOUND = add("long_rebound", new Potion(new StatusEffectInstance(ArtifalityEffects.REBOUND, 9600)));
    public static final Potion BERSERK_TIER_1 = add("berserk_tier_1", new Potion(new StatusEffectInstance(ArtifalityEffects.SUFFOCATION, 160,1),new StatusEffectInstance(StatusEffects.STRENGTH, 160, 1),new StatusEffectInstance(StatusEffects.SPEED, 160, 1)));
    public static final Potion BERSERK_TIER_2 = add("berserk_tier_2", new Potion(new StatusEffectInstance(ArtifalityEffects.SUFFOCATION, 100, 1),new StatusEffectInstance(StatusEffects.STRENGTH, 100, 2),new StatusEffectInstance(StatusEffects.SPEED, 100, 2)));
    public static final Potion SHORT_SUFFOCATION = add("short_suffocation", new Potion(new StatusEffectInstance(ArtifalityEffects.SUFFOCATION, 100)));
    public static final Potion LONG_SUFFOCATION = add("long_suffocation", new Potion(new StatusEffectInstance(ArtifalityEffects.SUFFOCATION, 170)));


    private static Potion add(String id, Potion potion) {
        POTIONS.put(ArtifalityMod.newId(id), potion);
        return potion;
    }

    public static void register() {
        for (Identifier id : POTIONS.keySet()) {
            Registry.register(Registry.POTION, id, POTIONS.get(id));
        }
    }
}
