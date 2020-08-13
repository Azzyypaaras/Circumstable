package azzy.fabric.circumstable.staticentities.blockentity.logistics;

import azzy.fabric.circumstable.util.interaction.HeatTransferHelper;
import net.minecraft.sound.SoundCategory;

import static azzy.fabric.circumstable.registry.BlockEntityRegistry.*;

public class GraniteShaftEntity extends ShaftEntity{
    public GraniteShaftEntity() {
        super(GRANITE_SHAFT_ENTITY);
        maxSpeed = 3489;
        maxTorque = 5560;
        maxTemp = 1215;
        material = HeatTransferHelper.HeatMaterial.GRANITE;
    }
}
