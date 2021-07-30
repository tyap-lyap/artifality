package artifality.mixin.common.extension;

import artifality.interfaces.LightningEntityExtensions;
import net.minecraft.entity.LightningEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningEntity.class)
public class LightningEntityExtension implements LightningEntityExtensions {

    @Unique private boolean canSpawnFire = true;

    @Unique
    @Override
    public void setNoFire() {
        canSpawnFire = false;
    }

    @Unique
    @Override
    public boolean canSpawnFire() {
        return canSpawnFire;
    }

    @Inject(method = "spawnFire", at = @At("HEAD"), cancellable = true)
    void badLightningDoNotCreateFire(int spreadAttempts, CallbackInfo ci){
        if(!canSpawnFire()){
            ci.cancel();
        }
    }
}
