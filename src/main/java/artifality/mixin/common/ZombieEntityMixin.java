package artifality.mixin.common;

import artifality.item.ArtifalityItems;
import artifality.item.ZeusWandItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {

    ZombieEntity self = (ZombieEntity)(Object)this;

    @Inject(method = "tryAttack", at = @At("RETURN"))
    void tryAttack(Entity target, CallbackInfoReturnable<Boolean> cir){
        if(self.getStackInHand(Hand.MAIN_HAND).getItem().equals(ArtifalityItems.ZEUS_WAND)){
            if(target.world.random.nextFloat() > 0.7F){
                ZeusWandItem.createLighting(target.world, target.getBlockPos(), new LightningEntity(EntityType.LIGHTNING_BOLT, target.world));
            }
        }
    }
}
