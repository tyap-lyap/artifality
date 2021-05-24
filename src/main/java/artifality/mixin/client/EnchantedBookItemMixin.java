package artifality.mixin.client;

import artifality.interfaces.IArtifalityEnchantment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EnchantedBookItem.class)
public abstract class EnchantedBookItemMixin {

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci){

        ListTag enchantments = EnchantedBookItem.getEnchantmentTag(stack);

        for(int i = 0; i < enchantments.size(); ++i) {
            CompoundTag compoundTag = enchantments.getCompound(i);

            Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(compoundTag.getString("id"))).ifPresent((enchantment) -> {

                if(enchantment instanceof IArtifalityEnchantment){
                    if(!Screen.hasShiftDown()){
                        tooltip.add(new TranslatableText(""));
                        tooltip.add(new LiteralText("Press Shift for More Information").formatted(Formatting.GRAY));
                    }else{
                        tooltip.add(new TranslatableText(""));
                        tooltip.add(new LiteralText("Description: ").formatted(Formatting.GRAY));
                        tooltip.add(new TranslatableText(enchantment.getTranslationKey() + ".description").formatted(Formatting.GRAY));
                    }
                }
            });
        }

    }
}
