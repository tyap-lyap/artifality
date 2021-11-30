package artifality.list.element;

import artifality.util.EffectsUtils;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;

public class LunarElement extends CrystalElement {

    public LunarElement(String name) {
        super(name);
    }

    @Override
    public void tick(LivingEntity entity, World world) {
        if (world.getTime() % 100L == 0L) {
            entity.addStatusEffect(new StatusEffectInstance(EffectsUtils.getRandomPositive(), 100, 0, true, true));
        }
    }

    @Override
    public void onAttack(LivingEntity target, World world) {
        target.addStatusEffect(new StatusEffectInstance(EffectsUtils.getRandomNegative(), 100, 0, true, true));
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
