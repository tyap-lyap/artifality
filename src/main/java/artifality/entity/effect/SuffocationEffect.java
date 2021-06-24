package artifality.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectType;

public class SuffocationEffect extends ArtifalityEffect {

    protected SuffocationEffect(StatusEffectType type, int color) {
        super(type, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);

        entity.damage(DamageSource.MAGIC, 1 + (float)0.5 * amplifier);
    }
}
