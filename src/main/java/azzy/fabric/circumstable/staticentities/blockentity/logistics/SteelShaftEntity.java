package azzy.fabric.circumstable.staticentities.blockentity.logistics;

import azzy.fabric.circumstable.util.interaction.HeatTransferHelper;

import static azzy.fabric.circumstable.registry.BlockEntityRegistry.*;

public class SteelShaftEntity extends ShaftEntity{
    public SteelShaftEntity() {
        super(STEEL_SHAFT_ENTITY);
        maxSpeed = 55207;
        maxTorque = 5560;
        maxTemp = 1370;
        material = HeatTransferHelper.HeatMaterial.STEEL;
    }
}
