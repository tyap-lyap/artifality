package artifality.block;

import artifality.block.base.LensBlock;
import artifality.item.UkuleleItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public class LunarCrystalLensBlock extends LensBlock {

    public LunarCrystalLensBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void applyLensEffect(StatusEffectInstance effectInstance, PlayerEntity playerEntity) {
        super.applyLensEffect(effectInstance, playerEntity);
        playerEntity.addStatusEffect(new StatusEffectInstance(
                UkuleleItem.POSITIVE_EFFECTS.get(playerEntity.world.random.nextInt(
                        UkuleleItem.POSITIVE_EFFECTS.size())), effectInstance.getDuration(), playerEntity.world.random.nextInt(2), true, true));
    }
}
