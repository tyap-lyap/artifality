package artifality.mixin.common.extension;

import artifality.interfaces.ITrinketEffectsManager;
import artifality.util.TrinketEffectsManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityExtension implements ITrinketEffectsManager {

    private TrinketEffectsManager trinketEffectsManager = new TrinketEffectsManager();


    PlayerEntity self = (PlayerEntity)(Object)this;

    @Inject(method = "tick", at = @At("TAIL"))
    void tick(CallbackInfo ci){

        if(self instanceof ITrinketEffectsManager){
            ((ITrinketEffectsManager) self).getTrinketEffectsManager().tick(self);
        }
    }


    @Override
    public TrinketEffectsManager getTrinketEffectsManager() {
        return trinketEffectsManager;
    }

    @Override
    public void setTrinketEffectsManager(TrinketEffectsManager trinketEffectsManager) {
        this.trinketEffectsManager = trinketEffectsManager;
    }
}
