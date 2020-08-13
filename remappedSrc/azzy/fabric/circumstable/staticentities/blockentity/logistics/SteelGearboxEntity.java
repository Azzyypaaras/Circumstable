package azzy.fabric.circumstable.staticentities.blockentity.logistics;

import azzy.fabric.circumstable.util.interaction.HeatTransferHelper;
import net.minecraft.block.entity.BlockEntityType;

public class SteelGearboxEntity extends GearboxEntity {
    public SteelGearboxEntity(BlockEntityType<? extends GearboxEntity> entityType, int modifier) {
        super(entityType, modifier);
        maxSpeed = 55207;
        maxTorque = 5560;
        maxTemp = 1370;
        material = HeatTransferHelper.HeatMaterial.STEEL;
    }
}
