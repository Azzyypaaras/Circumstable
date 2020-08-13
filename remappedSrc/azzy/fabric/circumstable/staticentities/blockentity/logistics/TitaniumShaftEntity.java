package azzy.fabric.circumstable.staticentities.blockentity.logistics;

import azzy.fabric.circumstable.util.interaction.HeatTransferHelper;

import static azzy.fabric.circumstable.registry.BlockEntityRegistry.STEEL_SHAFT_ENTITY;
import static azzy.fabric.circumstable.registry.BlockEntityRegistry.TITANIUM_SHAFT_ENTITY;

public class TitaniumShaftEntity extends ShaftEntity{
    public TitaniumShaftEntity() {
        super(TITANIUM_SHAFT_ENTITY);
        maxSpeed = 1450797;
        maxTorque = 42588;
        maxTemp = 1660;
        material = HeatTransferHelper.HeatMaterial.TITANIUM;
    }
}
