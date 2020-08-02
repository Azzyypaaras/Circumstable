package azzy.fabric.circumstable.staticentities.blockentity;

import net.minecraft.block.entity.BlockEntityType;

public abstract class FailingTransferEntity extends SpeenTransferEntity{

    public FailingTransferEntity(BlockEntityType<? extends MachineEntity> entityType) {
        super(entityType);
    }

    @Override
    public void tick() {
        super.tick();
        speed = 100;
        if((speed > maxSpeed || torque > maxTorque)){
            if(heat > maxTemp)
                fail(true, true, 1);
            else
                fail(false, false, 0);
        }
        if(heat > maxTemp && maxTemp != 0 && (world.getRandom().nextInt(100) == 0 || heat > maxTemp * 1.25)){
            fail(true, false, 0);
        }
    }
}
