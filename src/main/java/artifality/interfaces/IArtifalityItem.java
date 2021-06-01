package artifality.interfaces;

public interface IArtifalityItem {


    default String getParentModel(){
        return "generated";
    }

    String getTranslation();

    default String getDescription(){
        return null;
    }
}
