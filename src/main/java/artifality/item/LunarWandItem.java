package artifality.item;

import artifality.item.base.ArtifactItem;
import artifality.util.EffectsUtils;
import artifality.util.TiersUtils;
import artifality.util.TooltipAppender;
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

public class LunarWandItem extends ArtifactItem {

    public LunarWandItem(ArtifactSettings settings) {
        super(settings);
    }

    @Override
    public void appendTooltipInfo(ItemStack stack, List<Text> tooltip) {
        tooltip.add(new LiteralText(""));
        if(TiersUtils.getTier(stack) == 1) {
            tooltip.add(new LiteralText(TooltipAppender.ofKey("effect_level").replaceAll("%", "1-2")).formatted(Formatting.DARK_GREEN));
        }
        else {
            tooltip.add(new LiteralText(TooltipAppender.ofKey("effect_level").replaceAll("%", Integer.toString(TiersUtils.getTier(stack)))).formatted(Formatting.DARK_GREEN));
        }
        tooltip.add(new LiteralText(TooltipAppender.ofKey("cooldown").replaceAll("%", Integer.toString(20))).formatted(Formatting.DARK_GREEN));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient) return TypedActionResult.pass(user.getStackInHand(hand));
        int tier = TiersUtils.getTier(user.getStackInHand(hand));

        createCloudEffect(world, user, EffectsUtils.getRandomPositive(), 30 + tier * 10, 3.0F, tier);
        user.getItemCooldownManager().set(this, 20 * 20);
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public static void createCloudEffect(World world, LivingEntity entity, StatusEffect statusEffect, int durationInSec, float radius, int tier) {
        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world, entity.getX(), entity.getY(), entity.getZ());
        cloud.setRadius(radius);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(10);
        cloud.setRadiusGrowth(-cloud.getRadius() / (float)cloud.getDuration());

        int random = world.getRandom().nextInt(2);
        int amplifier = tier == 1 ? random : tier - 1;

        cloud.addEffect(new StatusEffectInstance(statusEffect, durationInSec * 20, amplifier));
        world.spawnEntity(cloud);
    }
}
