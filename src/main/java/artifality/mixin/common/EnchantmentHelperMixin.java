package artifality.mixin.common;

import artifality.registry.ArtifalityEnchants;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Inject(method = "getEfficiency", at = @At("HEAD"), cancellable = true)
    private static void getEfficiency(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);

        if(EnchantmentHelper.get(stack).containsKey(ArtifalityEnchants.LUNAR_MINER)) {
            int level = EnchantmentHelper.getLevel(ArtifalityEnchants.LUNAR_MINER, stack);
            int height = entity.getBlockPos().getY();
            int value;

            if(height <= -32) value = level * 2 + 2;
            else if(height <= 0) value = level * 2;
            else if(height <= 64) value = level + 2;
            else value = level;

            cir.setReturnValue(value);
        }
    }
}
