package artifality.extension;

import artifality.list.CrystalElement;

public interface ElementalExtension {
    boolean artifality$isElemental();

    CrystalElement artifality$getElement();

    void artifality$setElement(int element);
}
