package artifality.interfaces;


public interface LightningEntityExtensions {

    void setNoFire();

    void setCanChargeCreeper(boolean bl);

    void setDamage(float damage);

    float getDamage();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean canSpawnFire();

    boolean canChargeCreeper();
}
