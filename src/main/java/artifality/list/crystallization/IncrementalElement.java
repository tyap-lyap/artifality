package artifality.list.crystallization;

import artifality.util.EffectsUtils;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;

public class IncrementalElement extends Crystallization {

    public IncrementalElement(String name) {
        super(name);
    }

    @Override
    public void tick(LivingEntity entity, World world) {
        EffectsUtils.ticking(entity, StatusEffects.SPEED, 1);
        EffectsUtils.ticking(entity, StatusEffects.JUMP_BOOST, 1);
    }

    @Override
    public void onAttack(LivingEntity target, World world) {

    }

    @Override
    public void onInit(LivingEntity entity, World world) {
        Multimap<EntityAttribute, EntityAttributeModifier> attributes;
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Element modifier", 15, EntityAttributeModifier.Operation.ADDITION));
        attributes = builder.build();
        entity.getAttributes().addTemporaryModifiers(attributes);
        entity.heal(15);
    }
}
