package artifality.client.particle;

import artifality.ArtifalityMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ArtifalityParticles {

    public static void register() {
    }

    private static DefaultParticleType add(String name) {
        return Registry.register(Registries.PARTICLE_TYPE, ArtifalityMod.id(name), FabricParticleTypes.simple());
    }
}
