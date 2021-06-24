package artifality.entity.player;

import net.minecraft.nbt.NbtCompound;

public class SpecialHeartsManager {

    private int[] hearts = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public SpecialHeartsManager(){}

    public void setHearts() {
        this.hearts = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public int[] getHearts() {
        return hearts;
    }

    public int getHealth() {
        return getHeartAmount() * 2;
    }

    public int getHeartAmount(){
        int amount = 0;

        for (int i = 0; i <= 9; i++){
            if (hearts[i] > 0) {
                amount = amount + 1;
            }
        }
        return amount;
    }

    public boolean hasEmptySlot(){
        for (int i = 0; i <= 9; i++){
            if(hearts[i] == 0) return true;
        }
        return false;
    }

    public void addHeart(int heartId){
        for (int i = 0; i <= 9; i++){
            if(hearts[i] == 0){
                hearts[i] = heartId;
                return;
            }
        }
    }

    public void readNbt(NbtCompound nbt) {
        if(nbt.contains("specialHearts")){
            this.hearts = nbt.getIntArray("specialHearts");
        }
    }

    public void writeNbt(NbtCompound nbt) {
        nbt.putIntArray("specialHearts", this.hearts);

    }
}
