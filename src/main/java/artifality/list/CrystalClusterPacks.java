package artifality.list;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.Random;

import static artifality.registry.ArtifalityBlocks.*;

public class CrystalClusterPacks {
    public static final ArrayList<Pack> LIST = new ArrayList<>();

    static {
        LIST.add(new Pack(
                INCREMENTAL_CRYSTAL_GEODE,
                SMALL_INCREMENTAL_CRYSTAL_CLUSTER,
                MEDIUM_INCREMENTAL_CRYSTAL_CLUSTER,
                INCREMENTAL_CRYSTAL_CLUSTER));

        LIST.add(new Pack(
                LUNAR_CRYSTAL_GEODE,
                SMALL_LUNAR_CRYSTAL_CLUSTER,
                MEDIUM_LUNAR_CRYSTAL_CLUSTER,
                LUNAR_CRYSTAL_CLUSTER));

        LIST.add(new Pack(
                LIFE_CRYSTAL_GEODE,
                SMALL_LIFE_CRYSTAL_CLUSTER,
                MEDIUM_LIFE_CRYSTAL_CLUSTER,
                LIFE_CRYSTAL_CLUSTER));

//        LIST.add(new Pack(
//                WRATH_CRYSTAL_GEODE,
//                SMALL_WRATH_CRYSTAL_CLUSTER,
//                MEDIUM_WRATH_CRYSTAL_CLUSTER,
//                WRATH_CRYSTAL_CLUSTER));
    }

    public static Pack getRandomPack() {
        int random = new Random().nextInt(CrystalClusterPacks.LIST.size());
        return CrystalClusterPacks.LIST.get(random);
    }

    public record Pack(Block geode, Block... clusters) {
        public Block getRandomCluster() {
            int random = new Random().nextInt(clusters.length);
            return clusters[random];
        }
    }
}
