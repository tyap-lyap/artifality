package artifality.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface IArtifalityItem {

    default void onEntityLoad(Entity entity, World world){
    }

    default String getParentModel(){
        return "generated";
    }

    String getTranslation();

    default String getDescription(){
        return null;
    }
}
