package artifality.block;

import artifality.block.base.LensBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;

public class LunarCrystalLensBlock extends LensBlock {

    public LunarCrystalLensBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void applyLensEffect(StatusEffectInstance effectInstance, PlayerEntity playerEntity) {
        super.applyLensEffect(effectInstance, playerEntity);

        StatusEffect randomStatusEffect = Registry.STATUS_EFFECT.getRandom(playerEntity.getRandom());
        if (randomStatusEffect != null && !randomStatusEffect.equals(StatusEffects.INSTANT_DAMAGE)) {
            playerEntity.addStatusEffect(new StatusEffectInstance(randomStatusEffect, effectInstance.getDuration(), 0, true, true));
        }
    }

    @Override
    public String getDescription() {
        return "Gives random effect to players\nin radius of beacon if placed\non top of the beacon.";
    }
}
