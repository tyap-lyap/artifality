package artifality.block;

import artifality.block.base.LensBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public class IncrementalLensBlock extends LensBlock {

    public IncrementalLensBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void applyLensEffect(StatusEffectInstance effectInstance, PlayerEntity playerEntity) {
        super.applyLensEffect(effectInstance, playerEntity);
        playerEntity.addStatusEffect(new StatusEffectInstance(effectInstance.getEffectType(), effectInstance.getDuration(), effectInstance.getAmplifier() + 1, true, true));
    }

    @Override
    public String getDescription() {
        return "Increases the level of effect\ngiven by the beacon if placed\non top of the beacon.";
    }
}
