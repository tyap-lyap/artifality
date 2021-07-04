package artifality.item;

import artifality.item.base.BaseBlockItem;
import artifality.util.TrinketsUtils;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

public class MiniSomikItem extends BaseBlockItem implements Trinket, TrinketRenderer {

    public MiniSomikItem(Block block, Settings settings) {
        super(block, settings);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        if(entity.isInvisible() && TrinketsUtils.containsTrinket((PlayerEntity) entity, ArtifalityItems.INVISIBILITY_CAPE)) return;
        matrices.push();

        TrinketRenderer.translateToFace(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, (AbstractClientPlayerEntity) entity, headYaw, headPitch);

        matrices.translate(0D, -0.45D, 0.2D);

        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));

        String modelId = "artifality:mini_somik#inventory";

        itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier(modelId)));
        matrices.pop();
    }
}
