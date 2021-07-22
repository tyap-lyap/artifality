package artifality.block;

import artifality.block.base.LensBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public class IncrementalLensBlock extends LensBlock {

    public IncrementalLensBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void applyLensEffect(StatusEffectInstance effectInstance, PlayerEntity playerEntity) {
        super.applyLensEffect(effectInstance, playerEntity);
        playerEntity.addStatusEffect(new StatusEffectInstance(effectInstance.getEffectType(), effectInstance.getDuration(), effectInstance.getAmplifier() + 1, true, true));
    }
}
