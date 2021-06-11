package artifality.item;


import artifality.item.base.BaseTrinketItem;
import artifality.item.base.TierableItem;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class UkuleleItem extends BaseTrinketItem {

    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public UkuleleItem(Settings settings, String name) {
        super(settings, name);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 4, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.4F, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public void onEntityLoad(Entity entity, World world) {
        if(entity instanceof ZombieEntity){
            if(world.random.nextFloat() > 0.85F){
                ((ZombieEntity) entity).setStackInHand(Hand.MAIN_HAND, TierableItem.withTier(this, 1));
            }
        }
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public boolean isWip() {
        return true;
    }
}
