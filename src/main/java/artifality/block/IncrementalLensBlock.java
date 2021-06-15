package artifality.block;

import artifality.block.base.LensBlock;

public class IncrementalLensBlock extends LensBlock {

    public IncrementalLensBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public String getDescription() {
        return "Increases the level of effect\ngiven by the beacon if placed\non top of the beacon.";
    }
}
