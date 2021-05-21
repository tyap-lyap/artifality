package artifality.effect;

import artifality.ArtifalityMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ArtifalityEffects {
    public static final StatusEffect REBOUND = new ArtifalityEffect(StatusEffectType.BENEFICIAL, 0xF0E68C);

    public static void register(){

        Registry.register(Registry.STATUS_EFFECT, new Identifier(ArtifalityMod.MODID, "rebound"), REBOUND);
    }
}
