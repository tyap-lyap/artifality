package artifality.item;

import artifality.item.base.ArtifactItem;
import artifality.registry.ArtifalityEffects;
import artifality.util.TiersUtils;
import artifality.util.TooltipAppender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.List;

public class HandFanItem extends ArtifactItem {

    public HandFanItem(ArtifactSettings settings) {
        super(settings);
    }

    @Override
    public void appendTooltipInfo(ItemStack stack, List<Text> tooltip) {
        tooltip.add(new LiteralText(""));
        tooltip.add(new LiteralText(TooltipAppender.ofKey("cooldown").replaceAll("%", Integer.toString(20 - 4 * TiersUtils.getTier(stack)))).formatted(Formatting.DARK_GREEN));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        int tier = TiersUtils.getTier(user.getStackInHand(hand));
        user.getItemCooldownManager().set(this, (20 - 4 * tier) * 20);

        double radius = 6.0 + (2.0 * tier);
        int x1 = MathHelper.floor(user.getPos().x - radius);
        int y1 = MathHelper.floor(user.getPos().y - radius);
        int z1 = MathHelper.floor(user.getPos().z - radius);

        int x2 = MathHelper.floor(user.getPos().x + radius);
        int y2 = MathHelper.floor(user.getPos().y + radius);
        int z2 = MathHelper.floor(user.getPos().z + radius);

        List<Entity> list = world.getOtherEntities(user, new Box(x1, y1, z1, x2, y2, z2));
        Vec3d vec3d = new Vec3d(user.getPos().x, user.getPos().y, user.getPos().z);

        for (Entity entity : list) {
            double w = Math.sqrt(entity.squaredDistanceTo(vec3d)) / radius;
            if (w <= 1.0) {
                double x = entity.getX() - user.getPos().x;
                double y = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - user.getPos().y;
                double z = entity.getZ() - user.getPos().z;
                double aa = Math.sqrt(x * x + y * y + z * z);
                if (aa != 0.0) {
                    x /= aa;
                    y /= aa;
                    z /= aa;
                    double ab = Explosion.getExposure(vec3d, entity);
                    double ac = (1.5 - w) * ab;

                    entity.setVelocity(entity.getVelocity().add(x * ac, y * ac, z * ac));
                }
            }
        }
        user.fallDistance = 0.0F;
        Vec3d userVec = user.getVelocity();
        user.setVelocity(userVec.x, 0.75 + (0.25 * tier), userVec.z);
        user.addStatusEffect(new StatusEffectInstance(ArtifalityEffects.FALL_DAMAGE_IMMUNITY, 100));

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
