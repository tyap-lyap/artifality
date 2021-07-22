package artifality.item;

import artifality.item.base.BaseItem;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Objects;

public class LivingHeartItem extends BaseItem {

    public LivingHeartItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient){
            if(hand.equals(Hand.MAIN_HAND) && Objects.requireNonNull(user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).getValue() < 30){
                user.getStackInHand(hand).decrement(1);
                Objects.requireNonNull(user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).addPersistentModifier(new EntityAttributeModifier( "HealthUp", 2, EntityAttributeModifier.Operation.ADDITION));
                world.playSound(null, user.getX() + 0.5D, user.getY() + 0.5D, user.getZ() + 0.5D, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
