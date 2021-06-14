package artifality.client.particle;

import artifality.ArtifalityMod;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ArtifalityParticles {

    public static final DefaultParticleType CRYSTAL_SPARKLE = register("crystal_sparkle");

    public static void register() {

        ParticleFactoryRegistry.getInstance().register(CRYSTAL_SPARKLE, CrystalSparkleParticle.Factory::new);
    }

    private static DefaultParticleType register(String name) {
        return Registry.register(Registry.PARTICLE_TYPE, new Identifier(ArtifalityMod.MODID, name), FabricParticleTypes.simple());
    }
}
