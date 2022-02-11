package artifality.list.crystallization;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public abstract class Crystallization {
    String name;

    public Crystallization(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void tick(LivingEntity entity, World world);

    public abstract void onAttack(LivingEntity target, World world);

    public abstract void onInit(LivingEntity entity, World world);
}
