package artifality.extension;

import artifality.list.element.CrystalElement;

public interface ElementalExtension {
    boolean artifality$isElemental();

    CrystalElement artifality$getElement();

    void artifality$setElement(int element);
}
