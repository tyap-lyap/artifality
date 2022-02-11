package artifality.registry;

public class ArtifalityEvents {

    public static void init() {
//        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
//            if (killedEntity instanceof ElementalExtension extension) {
//                if (world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && extension.artifality$isElemental()) {
//                    CrystalElement element = extension.artifality$getElement();
//                    ItemStack stack;
//                    int count = world.random.nextInt(4) + 1;
//                    if (element.equals(CrystalElements.LIFE)) stack = new ItemStack(ArtifalityItems.LIFE_CRYSTAL, count);
//                    else if (element.equals(CrystalElements.LUNAR)) stack = new ItemStack(ArtifalityItems.LUNAR_CRYSTAL, count);
////                    else if (element.equals(CrystalElements.WRATH)) stack = new ItemStack(ArtifalityItems.WRATH_CRYSTAL, count);
//                    else stack = new ItemStack(ArtifalityItems.INCREMENTAL_CRYSTAL, count);
//                    killedEntity.dropStack(stack);
//
//                    BlockPos pos = killedEntity.getBlockPos();
//                    ExperienceOrbEntity.spawn(world, Vec3d.ofCenter(pos), 5);
//                    ExperienceOrbEntity.spawn(world, Vec3d.ofCenter(pos), 5 + world.random.nextInt(6));
//                    ExperienceOrbEntity.spawn(world, Vec3d.ofCenter(pos), 5 + world.random.nextInt(11));
//                }
//            }
//        });
    }
}
