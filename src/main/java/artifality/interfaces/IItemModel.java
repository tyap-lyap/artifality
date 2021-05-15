package artifality.interfaces;

public interface IItemModel {


    default String getParentModel(){
        return "generated";
    }
}
