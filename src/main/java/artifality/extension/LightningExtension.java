package artifality.extension;


public interface LightningExtension {
    void artifality$setNoFire();

    void artifality$setCanChargeCreeper(boolean canChargeCreeper);

    void artifality$setDamage(float damage);

    float artifality$getDamage();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean artifality$canSpawnFire();

    boolean artifality$canChargeCreeper();
}
