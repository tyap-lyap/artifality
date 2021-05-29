package artifality.interfaces;

import artifality.util.TrinketEffectsManager;

public interface ITrinketEffectsManager {

    TrinketEffectsManager trinketEffectsManager = new TrinketEffectsManager();

    default TrinketEffectsManager getTrinketEffectsManager(){
        return trinketEffectsManager;
    }
}
