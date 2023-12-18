package artifality.list;

import artifality.util.EffectsUtils;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class LensEffects {
    public static final LensEffect EMPTY = (effect, player) -> {};

    public static final LensEffect INCREMENTAL = (effect, player) -> player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration(), effect.getAmplifier() + 1, true, true));

    public static final LensEffect LUNAR = (effect, player) -> player.addStatusEffect(new StatusEffectInstance(
            EffectsUtils.getRandomPositive(), effect.getDuration(), player.getWorld().getRandom().nextInt(2), true, true));

    public static final LensEffect LIFE = (effect, player) -> {
        float health = player.getHealth();
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, effect.getDuration(), 4, true, true));
        player.setHealth(health);
    };

    public static final LensEffect WRATH = (effect, player) -> {};

    @FunctionalInterface
    public interface LensEffect {
        void apply(StatusEffectInstance effectInstance, PlayerEntity playerEntity);
    }
}
