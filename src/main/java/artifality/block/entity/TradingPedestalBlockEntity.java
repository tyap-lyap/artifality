package artifality.block.entity;

import artifality.registry.ArtifalityBlockEntities;
import artifality.registry.ArtifalityItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class TradingPedestalBlockEntity extends BlockEntity {
    public ItemStack sellingItem = ArtifalityItems.BALLOON.getDefaultStack();
    public ItemStack chargeItem = new ItemStack(ArtifalityItems.LUNAR_CRYSTAL, 10);

    public TradingPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ArtifalityBlockEntities.TRADING_PEDESTAL, pos, state);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        if(!sellingItem.isEmpty()) {
            NbtCompound sellingItemNbt = new NbtCompound();
            sellingItemNbt.putString("item", Registries.ITEM.getId(sellingItem.getItem()).toString());
            sellingItemNbt.put("nbt", sellingItem.getOrCreateNbt());
            sellingItemNbt.putInt("count", sellingItem.getCount());
            nbt.put("sellingItem", sellingItemNbt);
        }

        if(!chargeItem.isEmpty()) {
            NbtCompound chargeItemNbt = new NbtCompound();
            chargeItemNbt.putString("item", Registries.ITEM.getId(chargeItem.getItem()).toString());
            chargeItemNbt.put("nbt", chargeItem.getOrCreateNbt());
            chargeItemNbt.putInt("count", chargeItem.getCount());
            nbt.put("chargeItem", chargeItemNbt);
        }

    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        if(nbt.contains("sellingItem")) {
            NbtCompound sellingItemNbt = nbt.getCompound("sellingItem");
            var item = Registries.ITEM.getOrEmpty(new Identifier(sellingItemNbt.getString("item")));

            if(item.isPresent()) {
                this.sellingItem = new ItemStack(item.get(), sellingItemNbt.getInt("count"));
                this.sellingItem.setNbt((NbtCompound)sellingItemNbt.get("nbt"));
            }
        }
        else {
            this.sellingItem = ItemStack.EMPTY;
        }

        if(nbt.contains("chargeItem")) {
            NbtCompound chargeItemNbt = nbt.getCompound("chargeItem");
            var item = Registries.ITEM.getOrEmpty(new Identifier(chargeItemNbt.getString("item")));

            if(item.isPresent()) {
                this.chargeItem = new ItemStack(item.get(), chargeItemNbt.getInt("count"));
                this.chargeItem.setNbt((NbtCompound)chargeItemNbt.get("nbt"));
            }
        }
        else {
            this.chargeItem = ItemStack.EMPTY;
        }
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        this.writeNbt(nbtCompound);
        return nbtCompound;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }
}
