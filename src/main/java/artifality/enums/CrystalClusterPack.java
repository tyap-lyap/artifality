package artifality.enums;

import artifality.registry.ArtifalityBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.Random;

public class CrystalClusterPack {
    public static final ArrayList<Pack> LIST = new ArrayList<>();

    static {
        LIST.add(new Pack(
                ArtifalityBlocks.BUDDING_INCREMENTAL_CRYSTAL,
                ArtifalityBlocks.SMALL_INCREMENTAL_CRYSTAL_CLUSTER,
                ArtifalityBlocks.MEDIUM_INCREMENTAL_CRYSTAL_CLUSTER,
                ArtifalityBlocks.INCREMENTAL_CRYSTAL_CLUSTER));

        LIST.add(new Pack(
                ArtifalityBlocks.BUDDING_LUNAR_CRYSTAL,
                ArtifalityBlocks.SMALL_LUNAR_CRYSTAL_CLUSTER,
                ArtifalityBlocks.MEDIUM_LUNAR_CRYSTAL_CLUSTER,
                ArtifalityBlocks.LUNAR_CRYSTAL_CLUSTER));

        LIST.add(new Pack(
                ArtifalityBlocks.BUDDING_LIFE_CRYSTAL,
                ArtifalityBlocks.SMALL_LIFE_CRYSTAL_CLUSTER,
                ArtifalityBlocks.MEDIUM_LIFE_CRYSTAL_CLUSTER,
                ArtifalityBlocks.LIFE_CRYSTAL_CLUSTER));

        LIST.add(new Pack(
                ArtifalityBlocks.BUDDING_WRATH_CRYSTAL,
                ArtifalityBlocks.SMALL_WRATH_CRYSTAL_CLUSTER,
                ArtifalityBlocks.MEDIUM_WRATH_CRYSTAL_CLUSTER,
                ArtifalityBlocks.WRATH_CRYSTAL_CLUSTER));
    }

    public record Pack(Block budding, Block... clusters) {
        public Block getRandomCluster(){
            int random = new Random().nextInt(clusters.length);
            return clusters[random];
        }
    }
}
