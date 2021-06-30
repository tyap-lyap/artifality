package artifality.item;

import artifality.item.base.TierableItem;
import artifality.util.TrinketsUtils;
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
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;

public class UkuleleItem extends TierableItem implements Trinket, TrinketRenderer {

    public static final ArrayList<StatusEffect> POSITIVE_EFFECTS = new ArrayList<>(Arrays.asList(
            StatusEffects.FIRE_RESISTANCE, StatusEffects.REGENERATION, StatusEffects.STRENGTH,
            StatusEffects.SPEED, StatusEffects.ABSORPTION, StatusEffects.HASTE,
            StatusEffects.JUMP_BOOST, StatusEffects.RESISTANCE));


    public static final ArrayList<StatusEffect> NEGATIVE_EFFECTS = new ArrayList<>(Arrays.asList(StatusEffects.LEVITATION,
            StatusEffects.MINING_FATIGUE, StatusEffects.SLOWNESS, StatusEffects.POISON,
            StatusEffects.WEAKNESS, StatusEffects.WITHER));

    public UkuleleItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient) return TypedActionResult.pass(user.getStackInHand(hand));
        int tier = TierableItem.getCurrentTier(user.getStackInHand(hand));

        createCloudEffect(world, user, POSITIVE_EFFECTS.get(world.getRandom().nextInt(POSITIVE_EFFECTS.size())), 30 + tier * 10, 3.0F, tier);

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

    @Override
    public String getDescription() {
        return "When used, creates a cloud with\na random positive effect under the\nplayer, and if equipped on the\nback, creates a cloud with a random\nnegative effect under the attacker.\nCooldown: 20 sec";
    }

    @SuppressWarnings("unchecked")
    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        if(entity.isInvisible() && TrinketsUtils.containsTrinket((PlayerEntity) entity, ArtifalityItems.INVISIBILITY_CAPE)) return;
        matrices.push();

        TrinketRenderer.translateToChest(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, (AbstractClientPlayerEntity) entity);

        matrices.translate(0.0F, -0.2F, 0.37F);
        matrices.scale(0.8F, 0.8F, 0.8F);

        itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier("artifality:ukulele_in_hand#inventory")));
        matrices.pop();
    }
}
