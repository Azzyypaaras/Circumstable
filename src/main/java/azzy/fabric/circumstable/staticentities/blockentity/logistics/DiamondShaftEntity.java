package azzy.fabric.circumstable.staticentities.blockentity.logistics;

import azzy.fabric.circumstable.util.interaction.HeatTransferHelper;

import static azzy.fabric.circumstable.registry.BlockEntityRegistry.DIAMOND_SHAFT_ENTITY;
import static azzy.fabric.circumstable.registry.BlockEntityRegistry.GRANITE_SHAFT_ENTITY;

public class DiamondShaftEntity extends ShaftEntity{
    public DiamondShaftEntity() {
        super(DIAMOND_SHAFT_ENTITY);
        maxSpeed = 755339;
        maxTorque = 69508;
        maxTemp = 700;
        material = HeatTransferHelper.HeatMaterial.DIAMOND;
    }
}
