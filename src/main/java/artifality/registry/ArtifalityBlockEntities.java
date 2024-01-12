package artifality.registry;

import artifality.ArtifalityMod;
import artifality.block.TradingPedestalBlock;
import artifality.block.entity.TradingPedestalBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;

public class ArtifalityBlockEntities {

    public static final BlockEntityType<TradingPedestalBlockEntity> TRADING_PEDESTAL = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            ArtifalityMod.id("trading_pedestal"),
            FabricBlockEntityTypeBuilder.create(TradingPedestalBlockEntity::new, collectPedestals()).build()
    );

    public static void init() {

    }

    public static TradingPedestalBlock[] collectPedestals() {
        ArrayList<TradingPedestalBlock> pedestals = new ArrayList<>();

        ArtifalityBlocks.BLOCKS.forEach((id, block) -> {
            if(block instanceof TradingPedestalBlock pedestal) {
                pedestals.add(pedestal);
            }
        });

        return pedestals.toArray(new TradingPedestalBlock[0]);
    }
}
