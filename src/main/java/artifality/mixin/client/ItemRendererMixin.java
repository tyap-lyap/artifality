package artifality.mixin.client;

import artifality.ArtifalityMod;
import artifality.api.TwoModelsItemRegistry;
import artifality.enchant.LunarEnchantment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow @Final private ItemModels models;
    @Shadow public abstract void renderItem(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model);

    @Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V",
            at = @At("HEAD"))
    private void twoModelsItemImplementation(LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int light, int overlay, int seed, CallbackInfo ci) {

        if(!stack.isEmpty() && entity != null) {
            TwoModelsItemRegistry.ENTRIES.forEach((id, item) -> {
                if(stack.isOf(item)) {
                    BakedModel modelInHand = models.getModelManager().getModel(new ModelIdentifier(new Identifier(id + "_in_hand"), "inventory"));

                    renderItem(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, modelInHand);
                    matrices.scale(0, 0, 0);
                }
            });
        }

        /*
        replaces the default enchanted book model
        with the custom one if a book has a lunar
        enchantment
        */
        if(artifality$isLunar(stack)) {
            BakedModel model = models.getModelManager().getModel(new ModelIdentifier(ArtifalityMod.id("lunar_enchanted_book"), "inventory"));
            renderItem(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, model);
            matrices.scale(0, 0, 0);
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
