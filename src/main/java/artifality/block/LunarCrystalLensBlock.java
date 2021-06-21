package artifality.block;

import artifality.block.base.LensBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class LunarCrystalLensBlock extends LensBlock {

    public LunarCrystalLensBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void applyLensEffect(StatusEffectInstance effectInstance, PlayerEntity playerEntity) {
        super.applyLensEffect(effectInstance, playerEntity);

        playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, effectInstance.getDuration(), 0, true, true));
    }

    @Override
    public String getDescription() {
        return "Gives glowing effect to players\nin radius of beacon if placed\non top of the beacon.";
    }
}
