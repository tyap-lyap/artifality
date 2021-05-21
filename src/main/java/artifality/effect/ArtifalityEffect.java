package artifality.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
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

        @Override
        public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        }

        @Override
        public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
            super.onRemoved(entity, attributes, amplifier);
        }
    }