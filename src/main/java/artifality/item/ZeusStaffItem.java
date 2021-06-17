package artifality.item;

import artifality.interfaces.ILightningEntity;
import artifality.item.base.TierableItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
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

public class ZeusStaffItem extends TierableItem {

    public ZeusStaffItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        BlockHitResult blockHitResult = longRaycast(world, user);

        if(!world.isClient){
            switch (TierableItem.getCurrentTier(user.getStackInHand(hand))) {
                case 1 -> {
                    createLighting(world, blockHitResult.getBlockPos(), new LightningEntity(EntityType.LIGHTNING_BOLT, world));
                    user.getItemCooldownManager().set(this, 200);
                    return TypedActionResult.success(user.getStackInHand(hand));
                }
                case 2 -> {
                    createLighting(world, blockHitResult.getBlockPos(), new LightningEntity(EntityType.LIGHTNING_BOLT, world));
                    user.getItemCooldownManager().set(this, 150);
                    return TypedActionResult.success(user.getStackInHand(hand));
                }
                case 3 -> {
                    createLighting(world, blockHitResult.getBlockPos(), new LightningEntity(EntityType.LIGHTNING_BOLT, world));
                    user.getItemCooldownManager().set(this, 100);
                    return TypedActionResult.success(user.getStackInHand(hand));
                }
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public void onZombieInit(Entity entity) {
        if(entity instanceof ZombieEntity  && ((ZombieEntity) entity).getStackInHand(Hand.MAIN_HAND).isEmpty()){
            if(entity.world.random.nextFloat() > 0.9F){
                ((ZombieEntity) entity).setStackInHand(Hand.MAIN_HAND, getDefaultStack());
            }
        }
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
        float f = player.getPitch();
        float g = player.getYaw();
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
