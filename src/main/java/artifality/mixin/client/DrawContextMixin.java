package artifality.mixin.client;

import artifality.ArtifalityMod;
import artifality.enchant.LunarEnchantment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Optional;

@Mixin(DrawContext.class)
abstract class DrawContextMixin {

    @Shadow @Final private MinecraftClient client;

    /*
    replaces the default enchanted book model
    with the custom one if a book has a lunar
    enchantment
    */
    @ModifyArgs(method = "drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V"))
    private void modifyArgs(Args args) {
        ItemStack stack = args.get(0);

        if(artifality$isLunar(stack)) {
            BakedModel model = client.getItemRenderer().getModels().getModelManager().getModel(new ModelIdentifier(ArtifalityMod.id("lunar_enchanted_book"), "inventory"));
            args.set(7, model);
        }
    }

    private boolean artifality$isLunar(ItemStack stack) {
        if(!stack.isEmpty() && stack.isOf(Items.ENCHANTED_BOOK)) {
            NbtList list = EnchantedBookItem.getEnchantmentNbt(stack);
            for(int i = 0; i < list.size(); ++i) {
                NbtCompound nbt = list.getCompound(i);
                Optional<Enchantment> enchantment = Registries.ENCHANTMENT.getOrEmpty(Identifier.tryParse(nbt.getString("id")));
                if(enchantment.isPresent()) {
                    if(enchantment.get() instanceof LunarEnchantment) return true;
                }
            }
        }
        return false;
    }
}
