package artifality.item;

import artifality.item.base.BaseItem;
import artifality.util.TrinketsUtils;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BalloonItem extends BaseItem implements Trinket, TrinketRenderer {

    private final String type;

    public BalloonItem(Settings settings, String type) {
        super(settings, "Balloon");
        this.type = type;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient) return ActionResult.PASS;

        if (context.getWorld().getBlockState(context.getBlockPos()).isOf(Blocks.WATER_CAULDRON)){
            if(type != null && context.getPlayer() != null){
                context.getPlayer().setStackInHand(context.getHand(), ArtifalityItems.BALLOON.getDefaultStack());
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    public static boolean hasBalloonOnHead(PlayerEntity entity){
        for(ItemStack itemStack : TrinketsUtils.getTrinketsAsArray(entity)){
            if(itemStack.getItem() instanceof BalloonItem)return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "When in hand increases jump\nheight and while sneaking\ngives slow falling effect,\nuse on a cauldron with\nwater to clear the color.";
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(this.type != null) tooltip.add(new TranslatableText("misc.artifality." + type));

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        if(entity.isInvisible() && TrinketsUtils.containsTrinket((PlayerEntity) entity, ArtifalityItems.INVISIBILITY_CAPE)) return;
        matrices.push();

        if(entity.isInSneakingPose()) matrices.translate(0D, -1.4D, 0D);
        else matrices.translate(0D, -1.5D, 0D);

        matrices.scale(0.8F, 0.8F, 0.8F);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));

        String modelId = "artifality:balloon_in_hand#inventory";
        if(type != null){
            modelId = "artifality:" + type + "_balloon_in_hand#inventory";
        }
        itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier(modelId)));
        matrices.pop();
    }
}
