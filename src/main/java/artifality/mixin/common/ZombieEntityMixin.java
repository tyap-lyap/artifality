package artifality.mixin.common;

import artifality.registry.ArtifalityItems;
import artifality.item.ZeusStaffItem;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntity {

    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tryAttack", at = @At("RETURN"))
    void summonLightningOnAttack(Entity target, CallbackInfoReturnable<Boolean> cir){
        if(this.getStackInHand(Hand.MAIN_HAND).getItem().equals(ArtifalityItems.ZEUS_STAFF)){
            if(target.world.random.nextFloat() > 0.65F){
                ZeusStaffItem.createLighting(target.world, target.getBlockPos(), new LightningEntity(EntityType.LIGHTNING_BOLT, target.world), 1);
            }
        }
    }

    @Inject(method = "initialize", at = @At("RETURN"))
    void spawnWithZeusStaff(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir){
        if(!spawnReason.equals(SpawnReason.SPAWNER) && !spawnReason.equals(SpawnReason.CHUNK_GENERATION) && this.getStackInHand(Hand.MAIN_HAND).isEmpty()){
            if(this.world.random.nextFloat() > 0.95F){
                this.setStackInHand(Hand.MAIN_HAND, ArtifalityItems.ZEUS_STAFF.getDefaultStack());
            }
        }
    }
}
