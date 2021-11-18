package artifality.client.particle;

import artifality.ArtifalityMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;

public class ArtifalityParticles {

    public static void register() {
    }

    private static DefaultParticleType add(String name) {
        return Registry.register(Registry.PARTICLE_TYPE, ArtifalityMod.newId(name), FabricParticleTypes.simple());
    }
}
