package artifality.mixin.common;

import artifality.block.ArtifalityBlocks;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {

    BeaconBlockEntity self = (BeaconBlockEntity)(Object)this;

    @Redirect(method = "applyPlayerEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z"))
    boolean applyPlayerEffects(PlayerEntity playerEntity, StatusEffectInstance effect){

        if(Objects.requireNonNull(self.getWorld()).getBlockState(self.getPos().up()).equals(ArtifalityBlocks.INCREMENTAL_LENS.getDefaultState())){
            playerEntity.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration(), effect.getAmplifier() + 1, effect.isAmbient(), effect.shouldShowParticles()));
        }else{
            playerEntity.addStatusEffect(effect);
        }
        return false;
    }
}
