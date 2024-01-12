package artifality.list;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.Random;

import static artifality.registry.ArtifalityBlocks.*;

public class CrystalClusterPacks {
    public static final ArrayList<Pack> LIST = new ArrayList<>();

    static {
        add(
            INCREMENTAL_CRYSTAL_GEODE,
            SMALL_INCREMENTAL_CRYSTAL_CLUSTER,
            MEDIUM_INCREMENTAL_CRYSTAL_CLUSTER,
            INCREMENTAL_CRYSTAL_CLUSTER
        );
        add(
            LUNAR_CRYSTAL_GEODE,
            SMALL_LUNAR_CRYSTAL_CLUSTER,
            MEDIUM_LUNAR_CRYSTAL_CLUSTER,
            LUNAR_CRYSTAL_CLUSTER
        );
        add(
            LIFE_CRYSTAL_GEODE,
            SMALL_LIFE_CRYSTAL_CLUSTER,
            MEDIUM_LIFE_CRYSTAL_CLUSTER,
            LIFE_CRYSTAL_CLUSTER
        );
//        add(
//            WRATH_CRYSTAL_GEODE,
//            SMALL_WRATH_CRYSTAL_CLUSTER,
//            MEDIUM_WRATH_CRYSTAL_CLUSTER,
//            WRATH_CRYSTAL_CLUSTER
//        );
    }

    public static void add(Block geode, Block... clusters) {
        LIST.add(new Pack(geode, clusters));
    }

    public static Pack getRandomPack() {
        int random = new Random().nextInt(CrystalClusterPacks.LIST.size());
        return CrystalClusterPacks.LIST.get(random);
    }

    public static class Pack {
        public Block geode;
        public Block[] clusters;

        public Pack(Block geode, Block... clusters) {
            this.geode = geode;
            this.clusters = clusters;
        }

        public Block getRandomCluster() {
            int random = new Random().nextInt(this.clusters.length);
            return this.clusters[random];
        }
    }
}
