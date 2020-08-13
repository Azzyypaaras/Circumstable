package azzy.fabric.circumstable.staticentities.blockentity.logistics;

import azzy.fabric.circumstable.util.interaction.HeatTransferHelper;

import static azzy.fabric.circumstable.registry.BlockEntityRegistry.STEEL_SHAFT_ENTITY;
import static azzy.fabric.circumstable.registry.BlockEntityRegistry.TUNGSTEN_SHAFT_ENTITY;

public class TungstenShaftEntity extends ShaftEntity{
    public TungstenShaftEntity() {
        super(TUNGSTEN_SHAFT_ENTITY);
        maxSpeed = 315507;
        maxTorque = 54075;
        maxTemp = 3422;
        material = HeatTransferHelper.HeatMaterial.TUNGSTEN;
    }
}
