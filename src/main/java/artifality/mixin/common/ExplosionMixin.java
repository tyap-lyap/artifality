package artifality.mixin.common;

import artifality.item.ArtifalityItems;
import artifality.item.CatEarsItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Explosion.class)
public class ExplosionMixin {
    @Redirect(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    boolean damage(Entity entity, DamageSource source, float amount){
        if(entity instanceof PlayerEntity && ((PlayerEntity) entity).inventory.contains(ArtifalityItems.CAT_EARS.getDefaultStack())){
            if(source.getAttacker() instanceof CreeperEntity) {
                entity.damage(source, amount / 2);
                return false;
            }
        } else{
            entity.damage(source, amount);
        }
        return false;
    }
}
