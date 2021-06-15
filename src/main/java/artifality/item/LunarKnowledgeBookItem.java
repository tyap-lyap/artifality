package artifality.item;

import artifality.enchantment.ArtifalityEnchantments;
import artifality.item.base.BaseItem;
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

public class LunarKnowledgeBookItem extends BaseItem {

    public LunarKnowledgeBookItem(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient){
            if(hand.equals(Hand.MAIN_HAND)){
                user.getStackInHand(hand).decrement(1);
                ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                EnchantedBookItem.addEnchantment(book, new EnchantmentLevelEntry(ArtifalityEnchantments.LUNAR_DAMAGE, 1));
                user.setStackInHand(hand, book);
                world.playSound(null, user.getX() + 0.5D, user.getY() + 0.5D, user.getZ() + 0.5D, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public String getDescription() {
        return "Gives random Lunar Enchantment.";
    }
}
