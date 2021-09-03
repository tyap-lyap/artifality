package artifality.interfaces;


public interface LightningEntityExtensions {

    void setNoFire();

    void setDamage(float damage);

    float getDamage();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean canSpawnFire();
}
