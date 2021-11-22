package artifality.interfaces;

import artifality.enums.CrystalElement;

public interface ElementalExtensions {
    boolean artifality$isElemental();

    CrystalElement artifality$getElement();

    void artifality$setElement(int element);
}
