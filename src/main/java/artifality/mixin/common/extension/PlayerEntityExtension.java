package artifality.mixin.common.extension;

import artifality.interfaces.ITrinketEffectsManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityExtension implements ITrinketEffectsManager {


    PlayerEntity self = (PlayerEntity)(Object)this;

    @Inject(method = "tick", at = @At("TAIL"))
    void tick(CallbackInfo ci){

        if(self instanceof ITrinketEffectsManager){
            ((ITrinketEffectsManager) self).getTrinketEffectsManager().tick(self);
        }
    }


}
