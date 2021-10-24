package artifality.mixin.client;

import artifality.util.TooltipAppender;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EnchantedBookItem.class)
public abstract class EnchantedBookItemMixin {

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    void enchantmentDescription(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci){
        TooltipAppender.appendDescription(stack, tooltip);
    }
}
