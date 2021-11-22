package artifality.mixin.common.extension;

import artifality.interfaces.LightningExtensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningEntity.class)
public abstract class LightningExtension extends Entity implements LightningExtensions {

    @Unique private boolean canSpawnFire = true;
    @Unique private boolean canChargeCreeper = true;
    @Unique private float damage = 0;

    public LightningExtension(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    @Override
    public void artifality$setNoFire() {
        canSpawnFire = false;
    }

    @Unique
    @Override
    public void artifality$setCanChargeCreeper(boolean canChargeCreeper) {
        this.canChargeCreeper = canChargeCreeper;
    }

    @Unique
    @Override
    public void artifality$setDamage(float damage) {
        this.damage = damage;
    }

    @Unique
    @Override
    public float artifality$getDamage() {
        return this.damage;
    }

    @Unique
    @Override
    public boolean artifality$canSpawnFire() {
        return canSpawnFire;
    }

    @Unique
    @Override
    public boolean artifality$canChargeCreeper(){
        return canChargeCreeper;
    }

    @Inject(method = "spawnFire", at = @At("HEAD"), cancellable = true)
    void badLightningDoNotCreateFire(int spreadAttempts, CallbackInfo ci){
        if(!artifality$canSpawnFire()){
            ci.cancel();
        }
    }
}
