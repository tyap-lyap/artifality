package artifality.item.base;

import artifality.item.ArtifactSettings;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class NatureStaffItem extends ArtifactItem {

    public NatureStaffItem(ArtifactSettings settings) {
        super(settings);
    }

    public static void dropExperience(World world, BlockPos pos, int size) {
        if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
            ExperienceOrbEntity.spawn((ServerWorld)world, Vec3d.ofCenter(pos), size);
        }
    }
}
