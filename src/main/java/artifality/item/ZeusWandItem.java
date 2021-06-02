package artifality.item;

import artifality.interfaces.ILightningEntity;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class ZeusWandItem extends TierableItem {

    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public ZeusWandItem(Settings settings, String name) {
        super(settings, name);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 6, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.4F, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        BlockHitResult blockHitResult = longRaycast(world, user);

        if(!world.isClient){
            switch (TierableItem.getCurrentTier(user.getStackInHand(hand))){
                case 1:
                    createLighting(world, blockHitResult.getBlockPos(), new LightningEntity(EntityType.LIGHTNING_BOLT, world));
                    user.getItemCooldownManager().set(this, 200);
                    return TypedActionResult.success(user.getStackInHand(hand));
                case 2:
                    createLighting(world, blockHitResult.getBlockPos(), new LightningEntity(EntityType.LIGHTNING_BOLT, world));
                    user.getItemCooldownManager().set(this, 150);
                    return TypedActionResult.success(user.getStackInHand(hand));
                case 3:
                    createLighting(world, blockHitResult.getBlockPos(), new LightningEntity(EntityType.LIGHTNING_BOLT, world));
                    user.getItemCooldownManager().set(this, 100);
                    return TypedActionResult.success(user.getStackInHand(hand));
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public void onEntityLoad(Entity entity, World world) {
        if(entity instanceof ZombieEntity){
            if(world.random.nextFloat() > 0.75F){
                ((ZombieEntity) entity).setStackInHand(Hand.MAIN_HAND, TierableItem.withTier(this, 1));
            }
        }
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public String getDescription() {
        return "Summons lightning at the target point,\nalso neutralizes lightning damage\nwhile in the main hand.";
    }

    public static void createLighting(World world, BlockPos blockPos, LightningEntity lightningEntity){
        if (lightningEntity instanceof ILightningEntity){
            ((ILightningEntity) lightningEntity).setNoFire();
        }
        lightningEntity.updatePosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        world.spawnEntity(lightningEntity);
    }

    BlockHitResult longRaycast(World world, PlayerEntity player) {
        float f = player.pitch;
        float g = player.yaw;
        Vec3d vec3d = player.getCameraPosVec(1.0F);
        float h = MathHelper.cos(-g * 0.017453292F - 3.1415927F);
        float i = MathHelper.sin(-g * 0.017453292F - 3.1415927F);
        float j = -MathHelper.cos(-f * 0.017453292F);
        float k = MathHelper.sin(-f * 0.017453292F);
        float l = i * j;
        float n = h * j;
        Vec3d vec3d2 = vec3d.add((double)l * 15.0D, (double)k * 15.0D, (double)n * 15.0D);
        return world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));
    }
}
