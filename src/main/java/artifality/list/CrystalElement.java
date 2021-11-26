package artifality.list;

import artifality.util.EffectsUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;

import java.util.ArrayList;

public class CrystalElement {
    public static final ArrayList<CrystalElement> ELEMENTS = new ArrayList<>();

    public static final CrystalElement INCREMENTAL = new CrystalElement(
            "incremental",
            (entity, world) -> {
                EffectsUtils.ticking(entity, StatusEffects.HEALTH_BOOST, 2);
                EffectsUtils.ticking(entity, StatusEffects.REGENERATION, 1);
                EffectsUtils.ticking(entity, StatusEffects.SPEED, 1);
                EffectsUtils.ticking(entity, StatusEffects.JUMP_BOOST, 1);
                },
            (target, world) -> {});

    public static final CrystalElement LUNAR = new CrystalElement(
            "lunar",
            (entity, world) -> {
                EffectsUtils.ticking(entity, StatusEffects.HEALTH_BOOST, 2);
                EffectsUtils.ticking(entity, StatusEffects.REGENERATION, 1);
                if (world.getTime() % 100L == 0L) {
                    entity.addStatusEffect(new StatusEffectInstance(EffectsUtils.getRandomPositive(), 100, 0, true, true));
                }
            },
            (target, world) -> {
                target.addStatusEffect(new StatusEffectInstance(EffectsUtils.getRandomNegative(), 100, 0, true, true));
            });

    public static final CrystalElement LIFE = new CrystalElement(
            "life",
            (entity, world) -> {
                EffectsUtils.ticking(entity, StatusEffects.HEALTH_BOOST, 4);
                EffectsUtils.ticking(entity, StatusEffects.REGENERATION, 2);
                },
            (target, world) -> {});

    public static final CrystalElement WRATH = new CrystalElement(
            "wrath",
            (entity, world) -> {
                EffectsUtils.ticking(entity, StatusEffects.HEALTH_BOOST, 2);
                EffectsUtils.ticking(entity, StatusEffects.REGENERATION, 1);
                EffectsUtils.ticking(entity, StatusEffects.STRENGTH, 0);
                EffectsUtils.ticking(entity, StatusEffects.SPEED, 0);
                },
            (target, world) -> {});

    String name;
    Ticker ticker;
    Attacker attacker;

    public CrystalElement(String name, Ticker ticker, Attacker attacker){
        this.name = name;
        this.ticker = ticker;
        this.attacker = attacker;
        ELEMENTS.add(this);
    }

    public void tick(LivingEntity entity, World world){
        this.ticker.tick(entity, world);
    }

    public void onAttack(LivingEntity target, World world){
        this.attacker.onAttack(target, world);
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
}
