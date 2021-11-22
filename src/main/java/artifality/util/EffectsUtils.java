package artifality.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EffectsUtils {
    public static final ArrayList<StatusEffect> POSITIVE_EFFECTS = new ArrayList<>(Arrays.asList(
            StatusEffects.FIRE_RESISTANCE, StatusEffects.REGENERATION, StatusEffects.STRENGTH,
            StatusEffects.SPEED, StatusEffects.ABSORPTION, StatusEffects.HASTE,
            StatusEffects.JUMP_BOOST, StatusEffects.RESISTANCE
    ));
    public static final ArrayList<StatusEffect> NEGATIVE_EFFECTS = new ArrayList<>(Arrays.asList(
            StatusEffects.LEVITATION, StatusEffects.MINING_FATIGUE, StatusEffects.SLOWNESS,
            StatusEffects.POISON, StatusEffects.WEAKNESS, StatusEffects.WITHER
    ));

    public static StatusEffect getRandomPositive(){
        return POSITIVE_EFFECTS.get(new Random().nextInt(POSITIVE_EFFECTS.size()));
    }

    public static StatusEffect getRandomNegative(){
        return NEGATIVE_EFFECTS.get(new Random().nextInt(NEGATIVE_EFFECTS.size()));
    }

    public static void ticking(LivingEntity entity, StatusEffect effect){
        if (!entity.hasStatusEffect(effect)) {
            entity.addStatusEffect(new StatusEffectInstance(effect, 10, 0, false, false));
        } else {
            if (entity.getActiveStatusEffects().get(effect).getDuration() == 1) {
                entity.addStatusEffect(new StatusEffectInstance(effect, 10, 0, false, false));
            }
        }
    }

    public static void ticking(LivingEntity entity, StatusEffect effect, int amplifier){
        if (!entity.hasStatusEffect(effect)) {
            entity.addStatusEffect(new StatusEffectInstance(effect, 10, amplifier, true, true));
        } else {
            if (entity.getActiveStatusEffects().get(effect).getDuration() == 1) {
                entity.addStatusEffect(new StatusEffectInstance(effect, 10, amplifier, true, true));
            }
        }
    }
}
