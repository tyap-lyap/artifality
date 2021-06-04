package artifality.interfaces;

public interface IArtifalityBlock {

    String getParentModel();

    String getTranslation();

    default String getDescription(){
        return null;
    }

}
