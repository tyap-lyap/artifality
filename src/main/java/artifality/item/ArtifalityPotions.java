package artifality.item;

import artifality.ArtifalityMod;
import artifality.effect.ArtifalityEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ArtifalityPotions {

    public static final Potion REBOUND = new Potion(new StatusEffectInstance(ArtifalityEffects.REBOUND, 3600));
    public static final Potion LONG_REBOUND = new Potion("rebound",new StatusEffectInstance(ArtifalityEffects.REBOUND, 9600));

    public static void register() {

        Registry.register(Registry.POTION, new Identifier(ArtifalityMod.MODID, "rebound"), REBOUND);
        Registry.register(Registry.POTION, new Identifier(ArtifalityMod.MODID, "long_rebound"), LONG_REBOUND);
    }
}
