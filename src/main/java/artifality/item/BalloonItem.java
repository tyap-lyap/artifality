package artifality.item;

import artifality.item.base.TieredArtifactItem;
import artifality.util.TrinketsUtils;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
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
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class BalloonItem extends TieredArtifactItem implements Trinket, TrinketRenderer {

    public BalloonItem(Settings settings, ArtifactSettings artifactSettings) {
        super(settings, artifactSettings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient) return TypedActionResult.pass(user.getStackInHand(hand));
        ItemStack stack = user.getStackInHand(hand);

        if(stack.getDamage() > 0){
            if(user.getInventory().contains(Items.DRAGON_BREATH.getDefaultStack())){
                stack.setDamage(0);
                user.getInventory().getStack(user.getInventory().getSlotWithStack(Items.DRAGON_BREATH.getDefaultStack())).decrement(1);
                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public static boolean hasBalloonOnHead(PlayerEntity entity){
        for(ItemStack itemStack : TrinketsUtils.getTrinketsAsArray(entity)){
            if(itemStack.getItem() instanceof BalloonItem)return true;
        }
        return false;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return MathHelper.packRgb(172, 44, 123);
    }

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        matrices.push();

        if(entity.isInSneakingPose()) matrices.translate(0D, -1.25D, 0D);
        else matrices.translate(0D, -1.5D, 0D);

        matrices.scale(0.8F, 0.8F, 0.8F);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));

        String modelId = "artifality:balloon_in_hand#inventory";

        itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV,
                itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier(modelId)));
        matrices.pop();
    }
}
