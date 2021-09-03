package artifality.item;

import artifality.item.base.TieredItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.gen.feature.Feature;

import java.util.LinkedHashMap;
import java.util.Map;

public class ForestStaffItem extends TieredItem implements Trinket, TrinketRenderer {

    private static final Map<Item, Block> SAPLINGS = new LinkedHashMap<>();

    public ForestStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient || context.getPlayer() == null) return super.useOnBlock(context);

        BlockPos pos = context.getBlockPos();
        BlockState block = context.getWorld().getBlockState(pos);
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();

        if(block.getBlock() instanceof SaplingBlock && player.getInventory().contains(Items.BONE_MEAL.getDefaultStack())){
            if(BoneMealItem.useOnFertilizable(player.getInventory().getStack(player.getInventory().getSlotWithStack(Items.BONE_MEAL.getDefaultStack())), world, pos)){

                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
                dropExperience((ServerWorld)world, pos, world.getRandom().nextInt(3) + getCurrentTier(context.getStack()));
                return ActionResult.SUCCESS;
            }
        }else if(Feature.isSoil(block) && context.getSide().equals(Direction.UP)){
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

    protected void dropExperience(ServerWorld world, BlockPos pos, int size) {
        if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
            ExperienceOrbEntity.spawn(world, Vec3d.ofCenter(pos), size);
        }
    }

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

    @SuppressWarnings("unchecked")
    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        matrices.push();

        TrinketRenderer.translateToChest(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, (AbstractClientPlayerEntity) entity);

        matrices.translate(0.2F, 0.15F, 0.37F);
        matrices.scale(0.8F, 0.8F, 0.8F);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(45.0F));

        itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV,
                itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier("artifality:forest_staff_in_hand#inventory")));
        matrices.pop();
    }
}
