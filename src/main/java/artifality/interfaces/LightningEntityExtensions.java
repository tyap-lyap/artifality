package artifality.interfaces;


public interface LightningEntityExtensions {

    void setNoFire();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean canSpawnFire();
}
