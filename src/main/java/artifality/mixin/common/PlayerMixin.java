package artifality.mixin.common;

import artifality.client.particle.ArtifalityParticles;
import artifality.extension.PlayerExtension;
import artifality.registry.ArtifalityDimensions;
import artifality.registry.ArtifalityEnchants;
import artifality.registry.ArtifalityItems;
import artifality.item.BalloonItem;
import artifality.util.EffectsUtils;
import artifality.util.TiersUtils;
import artifality.util.TrinketsUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(PlayerEntity.class)
abstract class PlayerMixin extends LivingEntity implements PlayerExtension {
    PlayerEntity self = (PlayerEntity)(Object)this;

    public PlayerExtension.PlayerPosition prevPosition = null;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getAttackCooldownProgressPerTick", at = @At("HEAD"), cancellable = true)
    void getAttackCooldownProgressPerTick(CallbackInfoReturnable<Float> cir) {
        if(EnchantmentHelper.get(self.getStackInHand(Hand.MAIN_HAND)).containsKey(ArtifalityEnchants.LUNAR_DAMAGE)) {
            int level = EnchantmentHelper.getLevel(ArtifalityEnchants.LUNAR_DAMAGE, self.getStackInHand(Hand.MAIN_HAND));
            cir.setReturnValue((float)(1.0D / self.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED) * 20.0D) + level + 2);
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(!self.getWorld().isClient()) {
            if(source.getAttacker() != null && source.getAttacker() instanceof LivingEntity attacker) {
//                if(TrinketsUtils.containsTrinket(self, ArtifalityItems.UKULELE)) {
//                    if(!self.getItemCooldownManager().isCoolingDown(ArtifalityItems.UKULELE)) {
//                        UkuleleItem.createCloudEffect(attacker.world, attacker,
//                                EffectsUtils.getRandomNegative(),
//                                10, 1.5F, 1);
//                        self.getItemCooldownManager().set(ArtifalityItems.UKULELE, 20 * 20);
//                    }
//                }
            }
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    void volatileCurse(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(!self.getWorld().isClient()) {
            if(!self.isInvulnerableTo(source) && source.getAttacker() != null){
                for (ItemStack stack : self.getItemsEquipped()){
                    if (EnchantmentHelper.get(stack).containsKey(ArtifalityEnchants.VOLATILE_CURSE)) {
                        if(!self.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                            self.getWorld().createExplosion(self, self.getX(), self.getY(), self.getZ(), 1F, World.ExplosionSourceType.NONE);
                            self.getItemCooldownManager().set(stack.getItem(), 20 * 20);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "jump", at = @At("TAIL"))
    void jump(CallbackInfo ci) {
        if(!self.getWorld().isClient()) {
            if(self.getStackInHand(Hand.MAIN_HAND).isOf(ArtifalityItems.BALLOON)||
                    self.getStackInHand(Hand.OFF_HAND).isOf(ArtifalityItems.BALLOON) || BalloonItem.hasBalloonOnHead(self)) {
                self.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 14, 2, false, false));
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    void tick(CallbackInfo ci) {
        if(!self.getWorld().isClient()) {
            if(!self.isOnGround() && !self.isFallFlying() && !self.isTouchingWater() && !self.hasStatusEffect(StatusEffects.LEVITATION)) {
                useBalloon();
            }

            if (!self.isCreative() && self.getWorld().getDimensionKey().getValue().equals(ArtifalityDimensions.LUNAR_BAZAAR.getValue())) {

                if(self.isOnGround() && (self.age % 40) == 0) {
                    ((ServerWorld)self.getWorld()).spawnParticles(ArtifalityParticles.LUNAR_CHAIN, self.getX(), self.getY() + 0.05D, self.getZ(), 0, 0, 0, 0, 0);
                }
            }
        }
    }

    @Unique
    void useBalloon() {
        TrinketsUtils.getTrinketsArray(self).forEach(stack -> {
            if(stack.isOf(ArtifalityItems.BALLOON) && stack.getDamage() != stack.getMaxDamage()) {
                EffectsUtils.ticking(self, StatusEffects.SLOW_FALLING);
                if(self.getRandom().nextInt(30 * TiersUtils.getTier(stack)) == 0) {
                    stack.setDamage(stack.getDamage() + 1);
                }
            }
        });
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {

        if(prevPosition != null) {
            NbtCompound compound = new NbtCompound();
            compound.putDouble("x", prevPosition.pos.x);
            compound.putDouble("y", prevPosition.pos.y);
            compound.putDouble("z", prevPosition.pos.z);
            compound.putFloat("yaw", prevPosition.yaw);
            compound.putFloat("pitch", prevPosition.pitch);
            compound.putString("dimension", prevPosition.dimension.toString());

            nbt.put("PrevPositionBeforeBazaar", compound);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.contains("PrevPositionBeforeBazaar")) {
            NbtCompound compound = nbt.getCompound("PrevPositionBeforeBazaar");
            prevPosition = new PlayerPosition(
                    new Vec3d(compound.getDouble("x"), compound.getDouble("y"), compound.getDouble("z")),
                    compound.getFloat("yaw"),
                    compound.getFloat("pitch"),
                    new Identifier(compound.getString("dimension"))
            );
        }
    }

    @Override
    public void savePrevPosition() {
        prevPosition = new PlayerPosition(
                getPos(),
                getYaw(),
                getPitch(),
                getWorld().getDimensionKey().getValue()
        );
    }

    @Override
    public void teleportToPrevPosition() {
        if(prevPosition != null) {
            for(var world : getServer().getWorlds()) {
                if(world.getDimensionKey().getValue().equals(prevPosition.dimension)) {
                    teleport(world, prevPosition.pos.x, prevPosition.pos.y + 0.5, prevPosition.pos.z, Set.of(), prevPosition.yaw, prevPosition.pitch);
                    resetPrevPosition();
                    break;
                }
            }
        }
    }

    @Override
    public void resetPrevPosition() {
        prevPosition = null;
    }

    @Override
    public void setPrevPosition(PlayerPosition pos) {
        this.prevPosition = pos;
    }

    @Override
    public PlayerPosition getPrevPosition() {
        return this.prevPosition;
    }
}
