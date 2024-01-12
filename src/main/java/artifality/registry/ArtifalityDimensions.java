package artifality.registry;

import artifality.ArtifalityMod;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class ArtifalityDimensions {
    public static final RegistryKey<World> LUNAR_BAZAAR = RegistryKey.of(RegistryKeys.WORLD, ArtifalityMod.id("lunar_bazaar"));
}
