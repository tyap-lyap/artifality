package artifality.util;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class ExpUtils {

    public static void drop(World world, BlockPos pos, int size) {
        if(world instanceof ServerWorld serverWorld) {
            if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
                ExperienceOrbEntity.spawn(serverWorld, Vec3d.ofCenter(pos), size);
            }
        }
    }
}
