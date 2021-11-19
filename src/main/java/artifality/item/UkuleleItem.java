package artifality.item;

import artifality.item.base.TieredArtifactItem;
import artifality.item.base.TieredItem;
import artifality.util.EffectsUtils;
import artifality.util.TooltipAppender;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.client.TrinketRenderer;
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
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class UkuleleItem extends TieredArtifactItem implements Trinket, TrinketRenderer {

    public UkuleleItem(Settings settings, ArtifactSettings artifactSettings) {
        super(settings, artifactSettings);
    }

    @Override
    public void appendTooltipInfo(ItemStack stack, List<Text> tooltip){
        tooltip.add(new LiteralText(""));
        if(getCurrentTier(stack) == 1){
            tooltip.add(new LiteralText(TooltipAppender.ofKey("effect_level").replaceAll("%", "1-2")).formatted(Formatting.DARK_GREEN));
        }else {
            tooltip.add(new LiteralText(TooltipAppender.ofKey("effect_level").replaceAll("%", Integer.toString(getCurrentTier(stack)))).formatted(Formatting.DARK_GREEN));
        }
        tooltip.add(new LiteralText(TooltipAppender.ofKey("cooldown").replaceAll("%", Integer.toString(20))).formatted(Formatting.DARK_GREEN));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient) return TypedActionResult.pass(user.getStackInHand(hand));
        int tier = TieredItem.getCurrentTier(user.getStackInHand(hand));

        createCloudEffect(world, user, EffectsUtils.getRandomPositive(), 30 + tier * 10, 3.0F, tier);
        user.getItemCooldownManager().set(this, 20 * 20);
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public static void createCloudEffect(World world, LivingEntity entity, StatusEffect statusEffect, int durationInSec, float radius, int tier){
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(world, entity.getX(), entity.getY(), entity.getZ());
        areaEffectCloudEntity.setRadius(radius);
        areaEffectCloudEntity.setRadiusOnUse(-0.5F);
        areaEffectCloudEntity.setWaitTime(10);
        areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());

        int amplifier;
        if(tier == 1){
            amplifier = world.getRandom().nextInt(2);
        }else{
            amplifier = tier - 1;
        }
        areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffect, durationInSec * 20, amplifier));
        world.spawnEntity(areaEffectCloudEntity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        matrices.push();
        TrinketRenderer.translateToChest(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, (AbstractClientPlayerEntity) entity);
        matrices.translate(0.0F, -0.2F, 0.37F);
        matrices.scale(0.8F, 0.8F, 0.8F);
        itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV,
                itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier("artifality:ukulele_in_hand#inventory")));
        matrices.pop();
    }
}
