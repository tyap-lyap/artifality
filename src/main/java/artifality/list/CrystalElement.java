package artifality.list;

import artifality.util.EffectsUtils;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;

import java.util.ArrayList;

public class CrystalElement {
    public static final ArrayList<CrystalElement> ELEMENTS = new ArrayList<>();

    public static final CrystalElement INCREMENTAL = new CrystalElement(
            "incremental",
            (entity, world) -> {
                EffectsUtils.ticking(entity, StatusEffects.SPEED, 1);
                EffectsUtils.ticking(entity, StatusEffects.JUMP_BOOST, 1);
                },
            (target, world) -> {},
            (entity, world) -> {
                healthAttribute(entity, world, 15);
            });

    public static final CrystalElement LUNAR = new CrystalElement(
            "lunar",
            (entity, world) -> {
                if (world.getTime() % 100L == 0L) {
                    entity.addStatusEffect(new StatusEffectInstance(EffectsUtils.getRandomPositive(), 100, 0, true, true));
                }
            },
            (target, world) -> {
                target.addStatusEffect(new StatusEffectInstance(EffectsUtils.getRandomNegative(), 100, 0, true, true));
            },
            (entity, world) -> {
                healthAttribute(entity, world, 15);
            });

    public static final CrystalElement LIFE = new CrystalElement(
            "life",
            (entity, world) -> {},
            (target, world) -> {},
            (entity, world) -> {
                healthAttribute(entity, world, 30);
            });

    public static final CrystalElement WRATH = new CrystalElement(
            "wrath",
            (entity, world) -> {
                EffectsUtils.ticking(entity, StatusEffects.STRENGTH, 0);
                EffectsUtils.ticking(entity, StatusEffects.SPEED, 0);
                },
            (target, world) -> {},
            (entity, world) -> {
                healthAttribute(entity, world, 15);
            });

    private static void healthAttribute(LivingEntity entity, World world, float health){
        Multimap<EntityAttribute, EntityAttributeModifier> attributes;
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier("Element modifier", health, EntityAttributeModifier.Operation.ADDITION));
        attributes = builder.build();
        entity.getAttributes().addTemporaryModifiers(attributes);
        entity.heal(health);
    }

    String name;
    Ticker ticker;
    Attacker attacker;
    Initializer initializer;

    public CrystalElement(String name, Ticker ticker, Attacker attacker, Initializer initializer){
        this.name = name;
        this.ticker = ticker;
        this.attacker = attacker;
        this.initializer = initializer;
        ELEMENTS.add(this);
    }

    public void tick(LivingEntity entity, World world){
        this.ticker.tick(entity, world);
    }

    public void onAttack(LivingEntity target, World world){
        this.attacker.onAttack(target, world);
    }

    public void onInit(LivingEntity entity, World world){
        this.initializer.onInit(entity, world);
    }

    public String getName() {
        return name;
    }

    @FunctionalInterface
    public interface Ticker {
        void tick(LivingEntity entity, World world);
    }

    @FunctionalInterface
    public interface Attacker {
        void onAttack(LivingEntity target, World world);
    }

    @FunctionalInterface
    public interface Initializer {
        void onInit(LivingEntity entity, World world);
    }
}
