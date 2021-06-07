package artifality.mixin.common;

import artifality.block.ArtifalityBlocks;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Iterator;
import java.util.List;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin{

    /**
     * @author жопа гусика
     */
    @Overwrite
    private static void applyPlayerEffects(World world, BlockPos pos, int beaconLevel, @Nullable StatusEffect primaryEffect, @Nullable StatusEffect secondaryEffect) {
        if (!world.isClient && primaryEffect != null) {
            double d = (beaconLevel * 10 + 10);
            int i = 0;
            if (beaconLevel >= 4 && primaryEffect == secondaryEffect) {
                i = 1;
            }

            int j = (9 + beaconLevel * 2) * 20;
            Box box = (new Box(pos)).expand(d).stretch(0.0D, world.getHeight(), 0.0D);
            List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, box);
            Iterator<PlayerEntity> var11 = list.iterator();

            PlayerEntity playerEntity2;
            while(var11.hasNext()) {
                playerEntity2 = var11.next();
                if(world.getBlockState(pos.up()).getBlock().equals(ArtifalityBlocks.INCREMENTAL_LENS)){
                    playerEntity2.addStatusEffect(new StatusEffectInstance(primaryEffect, j, i + 1, true, true));
                }else playerEntity2.addStatusEffect(new StatusEffectInstance(primaryEffect, j, i, true, true));
            }

            if (beaconLevel >= 4 && primaryEffect != secondaryEffect && secondaryEffect != null) {
                var11 = list.iterator();

                while(var11.hasNext()) {
                    playerEntity2 = var11.next();
                    if(world.getBlockState(pos.up()).getBlock().equals(ArtifalityBlocks.INCREMENTAL_LENS)){
                        playerEntity2.addStatusEffect(new StatusEffectInstance(secondaryEffect, j, 1, true, true));
                    }else playerEntity2.addStatusEffect(new StatusEffectInstance(secondaryEffect, j, 0, true, true));
                }
            }

        }
    }
}
