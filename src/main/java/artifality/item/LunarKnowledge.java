package artifality.item;

import artifality.enchant.LunarEnchantment;
import artifality.item.base.BaseItem;
import artifality.registry.ArtifalityEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;

public class LunarKnowledge extends BaseItem {

    public LunarKnowledge(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if(!world.isClient() && hand.equals(Hand.MAIN_HAND) && user.getInventory().getEmptySlot() != -1) {
            world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1, 0);
            ArrayList<Enchantment> lunars = new ArrayList<>();
            ArtifalityEnchants.ENCHANTMENTS.forEach((identifier, enchantment) -> {
                if(enchantment instanceof LunarEnchantment) lunars.add(enchantment);
            });

            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantedBookItem.addEnchantment(book, new EnchantmentLevelEntry(lunars.get(user.getRandom().nextInt(lunars.size())), 1));
            user.getStackInHand(hand).decrement(1);
            user.giveItemStack(book);
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return super.use(world, user, hand);
    }
}
