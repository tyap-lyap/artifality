package artifality.item;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.Trinket;


public class UkuleleItem extends BaseItem implements Trinket {

    public UkuleleItem(Settings settings, String name) {
        super(settings, name);
    }


    @Override
    public boolean canWearInSlot(String group, String slot) {
        return (group.equals(SlotGroups.CHEST) && slot.equals(Slots.CAPE));
    }

}
