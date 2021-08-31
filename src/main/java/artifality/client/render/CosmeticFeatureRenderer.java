package artifality.client.render;

import artifality.item.ArtifalityItems;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;

@Deprecated
public class CosmeticFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public CosmeticFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(!player.isInvisible() && player.getName().asString().equals("PinkGoosik")){
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            matrices.push();

            TrinketRenderer.translateToChest(matrices, getContextModel(), player);

            matrices.translate(0.0F, -0.2F, 0.37F);
            matrices.scale(0.8F, 0.8F, 0.8F);

            itemRenderer.renderItem(ArtifalityItems.UKULELE.getDefaultStack(), ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier("artifality:ukulele_in_hand#inventory")));
            matrices.pop();
        }
    }
}
