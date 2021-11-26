package artifality.registry.base;

import artifality.ArtifalityMod;
import artifality.block.base.*;
import artifality.list.LensEffects;
import artifality.item.base.BaseBlockItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class BlockRegistry {
    public static final Map<Identifier, BaseBlockItem> ITEMS = new LinkedHashMap<>();
    public static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    public static Block lens(String name, LensEffects.LensEffect effect){
        return add(name, new LensBlock(effect));
    }

    public static Block budding(String name, Block small, Block medium, Block large){
        return add(name, new BuddingCrystalBlock(small, medium, large));
    }

    public static Block cluster(String name, String type){
        return add(name, new CrystalClusterBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).luminance(10), type));
    }

    public static Block crystalBlock(String name){
        return add(name, new CrystalBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));
    }

    public static Block crystalSlab(String name){
        return add(name, new CrystalSlabBlock(copyOf(Blocks.COBBLESTONE_SLAB).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));
    }

    public static Block crystalStairs(String name){
        return add(name, new CrystalStairsBlock(copyOf(Blocks.COBBLESTONE_STAIRS).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(15).nonOpaque()));
    }

    public static FabricBlockSettings copyOf(Block block){
        return FabricBlockSettings.copyOf(block);
    }

    public static Block add(String name, Block block) {
        Item.Settings settings = new Item.Settings();
        return addBlockItem(name, block, new BaseBlockItem(block, settings));
    }

    public static Block addBlockItem(String name, Block block, BaseBlockItem item) {
        addBlock(name, block);
        if (item != null) {
            item.appendBlocks(Item.BLOCK_ITEMS, item);
            ITEMS.put(ArtifalityMod.newId(name), item);
        }
        return block;
    }

    public static Block addBlock(String name, Block block) {
        BLOCKS.put(ArtifalityMod.newId(name), block);
        return block;
    }
}
