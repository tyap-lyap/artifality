package artifality.list;

import artifality.list.element.*;

import java.util.ArrayList;

public class CrystalElements {
    public static final ArrayList<CrystalElement> ELEMENTS = new ArrayList<>();

    public static final CrystalElement INCREMENTAL = add(new IncrementalElement("incremental"));
    public static final CrystalElement LUNAR = add(new LunarElement("lunar"));
    public static final CrystalElement LIFE = add(new LifeElement("life"));
//    public static final CrystalElement WRATH = add(new WrathElement("wrath"));

    public static CrystalElement add(CrystalElement element) {
        ELEMENTS.add(element);
        return element;
    }
}
