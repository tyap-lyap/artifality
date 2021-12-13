package artifality.util;

import com.google.common.collect.Lists;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import static net.minecraft.entity.effect.StatusEffects.*;

import java.util.List;
import java.util.Random;

public class EffectsUtils {
    public static final List<StatusEffect> POSITIVE_EFFECTS = Lists.newArrayList(
            FIRE_RESISTANCE, REGENERATION, STRENGTH,
            SPEED, ABSORPTION, HASTE,
            JUMP_BOOST, RESISTANCE
    );
    public static final List<StatusEffect> NEGATIVE_EFFECTS = Lists.newArrayList(
            LEVITATION, MINING_FATIGUE, SLOWNESS,
            POISON, WEAKNESS, WITHER
    );

    public static StatusEffect getRandomPositive(){
        return POSITIVE_EFFECTS.get(new Random().nextInt(POSITIVE_EFFECTS.size()));
    }

    public static StatusEffect getRandomNegative(){
        return NEGATIVE_EFFECTS.get(new Random().nextInt(NEGATIVE_EFFECTS.size()));
    }

    public static void ticking(LivingEntity entity, StatusEffect effect){
        if (!entity.hasStatusEffect(effect)) {
            entity.addStatusEffect(new StatusEffectInstance(effect, 10, 0, false, false));
        } else if (entity.getActiveStatusEffects().get(effect).getDuration() == 1) {
            entity.addStatusEffect(new StatusEffectInstance(effect, 10, 0, false, false));
        }
    }

    public static void ticking(LivingEntity entity, StatusEffect effect, int amplifier){
        if (!entity.hasStatusEffect(effect)) {
            entity.addStatusEffect(new StatusEffectInstance(effect, 10, amplifier, true, true));
        } else if (entity.getActiveStatusEffects().get(effect).getDuration() == 1) {
            entity.addStatusEffect(new StatusEffectInstance(effect, 10, amplifier, true, true));
        }
    }
}
