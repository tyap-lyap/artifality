package artifality.mixin.common;

import artifality.block.base.LensBlock;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin{

    @Inject(method = "applyPlayerEffects", at = @At("TAIL"))
    private static void applyLensEffects(World world, BlockPos pos, int beaconLevel, StatusEffect primaryEffect, StatusEffect secondaryEffect, CallbackInfo ci){
        if(world.isClient || primaryEffect == null) return;

        double distance = beaconLevel * 10 + 10;
        int amplifier = 0;
        if (beaconLevel >= 4 && primaryEffect == secondaryEffect) {
            amplifier = 1;
        }
        int duration = (9 + beaconLevel * 2) * 20;

        List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, (new Box(pos)).expand(distance).stretch(0.0D, world.getHeight(), 0.0D));
        Iterator<PlayerEntity> playerIterator = list.iterator();

        PlayerEntity player;
        while(playerIterator.hasNext()) {
            player = playerIterator.next();

            if(world.getBlockState(pos.up()).getBlock() instanceof LensBlock lensBlock){
                lensBlock.applyLensEffect(new StatusEffectInstance(primaryEffect, duration, amplifier, true, true), player);
            }
        }

        if (beaconLevel >= 4 && primaryEffect != secondaryEffect && secondaryEffect != null) {
            playerIterator = list.iterator();

            while(playerIterator.hasNext()) {
                player = playerIterator.next();

                if(world.getBlockState(pos.up()).getBlock() instanceof LensBlock lensBlock){
                    lensBlock.applyLensEffect(new StatusEffectInstance(secondaryEffect, duration, 0, true, true), player);
                }
            }
        }
    }
}
