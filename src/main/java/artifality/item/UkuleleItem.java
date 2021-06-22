package artifality.item;

import artifality.item.base.BaseItem;
import dev.emi.trinkets.api.Trinket;
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

public class UkuleleItem extends BaseItem implements Trinket {

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

        createCloudEffect(world, user, POSITIVE_EFFECTS.get(world.getRandom().nextInt(POSITIVE_EFFECTS.size())), 40, 3.0F);

        user.getItemCooldownManager().set(this, 20 * 20);
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public static void createCloudEffect(World world, LivingEntity entity, StatusEffect statusEffect, int durationInSec, float radius){
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(world, entity.getX(), entity.getY(), entity.getZ());
        areaEffectCloudEntity.setRadius(radius);
        areaEffectCloudEntity.setRadiusOnUse(-0.5F);
        areaEffectCloudEntity.setWaitTime(10);
        areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());

        areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffect, durationInSec * 20, world.getRandom().nextInt(2)));
        world.spawnEntity(areaEffectCloudEntity);
    }

    @Override
    public String getDescription() {
        return "When used, creates a cloud with\na random positive effect under the\nplayer, and if equipped on the\nback, creates a cloud with a random\nnegative effect under the attacker.\nCooldown: 20 sec";
    }
}
