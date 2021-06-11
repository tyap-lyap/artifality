package artifality.interfaces;

public interface Translatable {

    String getOriginName();

    default String getDescription(){
        return null;
    }
}
