package artifality.item;

import artifality.item.base.NatureStaffItem;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.gen.feature.Feature;

import java.util.LinkedHashMap;
import java.util.Map;

public class ForestStaffItem extends NatureStaffItem {

    private static final Map<Item, Block> SAPLINGS = new LinkedHashMap<>();

    static {
        SAPLINGS.put(Items.FLOWERING_AZALEA, Blocks.FLOWERING_AZALEA);
        SAPLINGS.put(Items.AZALEA, Blocks.AZALEA);
        SAPLINGS.put(Items.OAK_SAPLING, Blocks.OAK_SAPLING);
        SAPLINGS.put(Items.SPRUCE_SAPLING, Blocks.SPRUCE_SAPLING);
        SAPLINGS.put(Items.BIRCH_SAPLING, Blocks.BIRCH_SAPLING);
        SAPLINGS.put(Items.JUNGLE_SAPLING, Blocks.JUNGLE_SAPLING);
        SAPLINGS.put(Items.ACACIA_SAPLING, Blocks.ACACIA_SAPLING);
        SAPLINGS.put(Items.DARK_OAK_SAPLING, Blocks.DARK_OAK_SAPLING);
    }

    public ForestStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient || context.getPlayer() == null) return super.useOnBlock(context);

        BlockPos pos = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(pos);
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();

        if(state.getBlock() instanceof SaplingBlock && player.getInventory().contains(Items.BONE_MEAL.getDefaultStack())){
            if(BoneMealItem.useOnFertilizable(player.getInventory().getStack(player.getInventory().getSlotWithStack(Items.BONE_MEAL.getDefaultStack())), world, pos)){
                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
                dropExperience(world, pos, world.getRandom().nextInt(2) + getCurrentTier(context.getStack()));
                return ActionResult.SUCCESS;
            }
        }else if(Feature.isSoil(state) && context.getSide().equals(Direction.UP)){
            if(hasSapling(player) && world.isAir(pos.up())){
                BlockSoundGroup blockSoundGroup = Blocks.AZALEA.getSoundGroup(Blocks.AZALEA.getDefaultState());
                world.playSound(null, pos, blockSoundGroup.getPlaceSound(), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);
                world.setBlockState(pos.up(), SAPLINGS.get(consumeSapling(player)).getDefaultState());
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    private static boolean hasSapling(PlayerEntity playerEntity){
        Inventory inventory = playerEntity.getInventory();

        for (int i = 0; i <= inventory.size(); i++){
            ItemStack itemStack = inventory.getStack(i);
            if(SAPLINGS.containsKey(itemStack.getItem())) return true;
        }

        return false;
    }

    private static Item consumeSapling(PlayerEntity playerEntity){
        Inventory inventory = playerEntity.getInventory();

        for (int i = 0; i <= inventory.size(); i++){
            ItemStack itemStack = inventory.getStack(i);

            if(SAPLINGS.containsKey(itemStack.getItem())){
                inventory.removeStack(i, 1);
                return inventory.getStack(i).getItem();
            }
        }
        return Items.AIR;
    }
}
