package artifality.block;

import artifality.block.base.LensBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class CrystalHeartLensBlock extends LensBlock {

    public CrystalHeartLensBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void applyLensEffect(StatusEffectInstance effectInstance, PlayerEntity playerEntity) {
        super.applyLensEffect(effectInstance, playerEntity);

        float health = playerEntity.getHealth();
        playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, effectInstance.getDuration(), 4, true, true));
        playerEntity.setHealth(health);
    }
}
