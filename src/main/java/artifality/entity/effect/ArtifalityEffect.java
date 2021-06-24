package artifality.entity.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class ArtifalityEffect extends StatusEffect {

        protected ArtifalityEffect(StatusEffectType type, int color) {
            super(type, color);
        }

        @Override
        public boolean canApplyUpdateEffect(int duration, int amplifier) {
            return true;
        }
    }