package artifality.mixin.common;

import artifality.item.ArtifalityItems;
import artifality.item.ZeusStaffItem;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {

    ZombieEntity self = (ZombieEntity)(Object)this;

    @Inject(method = "tryAttack", at = @At("RETURN"))
    void tryAttack(Entity target, CallbackInfoReturnable<Boolean> cir){
        if(self.getStackInHand(Hand.MAIN_HAND).getItem().equals(ArtifalityItems.ZEUS_STAFF)){
            if(target.world.random.nextFloat() > 0.65F){
                ZeusStaffItem.createLighting(target.world, target.getBlockPos(), new LightningEntity(EntityType.LIGHTNING_BOLT, target.world));
            }
        }
    }

    @Inject(method = "initialize", at = @At("RETURN"))
    void initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir){

        if(!spawnReason.equals(SpawnReason.SPAWNER) && !spawnReason.equals(SpawnReason.CHUNK_GENERATION) && self.getStackInHand(Hand.MAIN_HAND).isEmpty()){
            if(self.world.random.nextFloat() > 0.9F){
                self.setStackInHand(Hand.MAIN_HAND, ArtifalityItems.ZEUS_STAFF.getDefaultStack());
            }
        }
    }
}
