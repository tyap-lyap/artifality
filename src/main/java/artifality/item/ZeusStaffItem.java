package artifality.item;

import artifality.interfaces.LightningEntityExtensions;
import artifality.item.base.TieredItem;
import artifality.util.TooltipAppender;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public class ZeusStaffItem extends TieredItem {

    public ZeusStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltipInfo(ItemStack stack, List<Text> tooltip){
        tooltip.add(new LiteralText(""));
        tooltip.add(new LiteralText(TooltipAppender.ofKey("cooldown").replaceAll("%", Integer.toString((250 - getCurrentTier(stack) * 50) / 20))).formatted(Formatting.DARK_GREEN));
        tooltip.add(new LiteralText(TooltipAppender.ofKey("damage").replaceAll("%", Integer.toString(6 + getCurrentTier(stack)))).formatted(Formatting.DARK_GREEN));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient) return super.use(world, user, hand);
        int tier = getCurrentTier(user.getStackInHand(hand));

        BlockHitResult blockHitResult = longRaycast(world, user);
        createLighting(world, blockHitResult.getBlockPos(), new LightningEntity(EntityType.LIGHTNING_BOLT, world), tier);
        user.getItemCooldownManager().set(this, 250 - tier * 50);
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public static void createLighting(World world, BlockPos blockPos, LightningEntity lightningEntity, int tier){
        if (lightningEntity instanceof LightningEntityExtensions extension){
            extension.setNoFire();
            extension.setDamage(6 + tier);
            if(tier == 1){
                extension.setCanChargeCreeper(false);
            }
        }
        lightningEntity.updatePosition(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D);
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
