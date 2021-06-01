package artifality.item;

import artifality.ArtifalityMod;
import artifality.effect.ArtifalityEffects;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArtifalityPotions {

    private static final Map<Identifier, Potion> POTIONS = new LinkedHashMap<>();


    public static final Potion REBOUND = add("rebound", ArtifalityEffects.REBOUND, 3600);
    public static final Potion LONG_REBOUND = add("long_rebound", ArtifalityEffects.REBOUND, 9600);


    private static Potion add(String id, StatusEffect statusEffect, int duration) {
        Potion potion = new Potion(id, new StatusEffectInstance(statusEffect, duration));
        POTIONS.put(new Identifier(ArtifalityMod.MODID, id), potion);
        return potion;
    }

    public static void register() {
        for (Identifier id : POTIONS.keySet()) {
            Registry.register(Registry.POTION, id, POTIONS.get(id));
        }
    }
}
