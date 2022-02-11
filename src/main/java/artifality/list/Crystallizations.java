package artifality.list;

import artifality.list.crystallization.*;

import java.util.ArrayList;

public class Crystallizations {
    public static final ArrayList<Crystallization> ELEMENTS = new ArrayList<>();

    public static final Crystallization INCREMENTAL = add(new IncrementalElement("incremental"));
    public static final Crystallization LUNAR = add(new LunarElement("lunar"));
    public static final Crystallization LIFE = add(new LifeElement("life"));
//    public static final Crystallization WRATH = add(new WrathElement("wrath"));

    public static Crystallization add(Crystallization element) {
        ELEMENTS.add(element);
        return element;
    }
}
