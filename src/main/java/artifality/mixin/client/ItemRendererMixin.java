package artifality.mixin.client;

import artifality.api.TwoModelsItemRegistry;
import artifality.enchant.LunarEnchantment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow @Final private ItemModels models;
    @Shadow public abstract void renderItem(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model);

    @Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V",
            at = @At("HEAD"))
    void twoModelsItemImplementation(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int light, int overlay, int seed, CallbackInfo ci) {

        if(!stack.isEmpty() && entity != null) {
            TwoModelsItemRegistry.ENTRIES.forEach((id, item) -> {
                if(stack.isOf(item)) {
                    BakedModel model = models.getModelManager().getModel(new ModelIdentifier(id + "_in_hand#inventory"));
                    renderItem(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, model);
                    matrices.scale(0, 0, 0);
                }
            });
        }

        /*
        replaces the default enchanted book model
        with the custom one if a book has a lunar
        enchantment
        */
//        if(!stack.isEmpty() && stack.isOf(Items.ENCHANTED_BOOK)) {
//            NbtList enchantments = EnchantedBookItem.getEnchantmentNbt(stack);
//
//            for(int i = 0; i < enchantments.size(); ++i) {
//                NbtCompound nbtCompound = enchantments.getCompound(i);
//
//                Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(nbtCompound.getString("id"))).ifPresent((enchantment) -> {
//                    if(enchantment instanceof LunarEnchantment) {
//                        BakedModel model = models.getModelManager().getModel(new ModelIdentifier("artifality:lunar_enchanted_book#inventory"));
//                        renderItem(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, model);
//                        matrices.scale(0, 0, 0);
//                    }
//                });
//            }
//        }

    }

    /*
    replaces the default enchanted book model
    with the custom one if a book has a lunar
    enchantment
    */
//    @ModifyArgs(method = "renderGuiItemModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V"))
//    void modifyArgs(Args args) {
//        ItemStack stack = args.get(0);
//
//        if(!stack.isEmpty() && stack.isOf(Items.ENCHANTED_BOOK)) {
//            NbtList enchantments = EnchantedBookItem.getEnchantmentNbt(stack);
//
//            for(int i = 0; i < enchantments.size(); ++i) {
//                NbtCompound nbtCompound = enchantments.getCompound(i);
//
//                Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(nbtCompound.getString("id"))).ifPresent((enchantment) -> {
//                    if(enchantment instanceof LunarEnchantment) {
//                        BakedModel model = models.getModelManager().getModel(new ModelIdentifier("artifality:lunar_crystal#inventory"));
//                        args.set(7, model);
//                    }
//                });
//            }
//        }
//    }

}
