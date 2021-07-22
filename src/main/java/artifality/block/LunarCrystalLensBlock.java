package artifality.block;

import artifality.block.base.LensBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class LunarCrystalLensBlock extends LensBlock {

    public LunarCrystalLensBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void applyLensEffect(StatusEffectInstance effectInstance, PlayerEntity playerEntity) {
        super.applyLensEffect(effectInstance, playerEntity);

        playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, effectInstance.getDuration(), 0, true, true));
    }
}
