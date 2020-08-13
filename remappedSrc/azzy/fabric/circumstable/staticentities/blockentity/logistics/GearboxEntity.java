package azzy.fabric.circumstable.staticentities.blockentity.logistics;

import azzy.fabric.circumstable.block.entity.SpeenMachine;
import azzy.fabric.circumstable.staticentities.blockentity.FailingTransferEntity;
import azzy.fabric.circumstable.staticentities.blockentity.MachineEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class GearboxEntity extends FailingTransferEntity {

    public GearboxEntity(BlockEntityType<? extends GearboxEntity> entityType, int modifier) {
        super(entityType);
        this.modifier = modifier;
    }

    @Override
    public void updateIO(BlockState state, BlockPos pos, @Nullable ItemStack item) {
        outputs.add(state.get(SpeenMachine.FACING));
        inputs.add(state.get(SpeenMachine.FACING).getOpposite());
    }

    public void switchMode(){
        switch (mode){
            case SPEED: mode = TransferMode.TORQUE; break;
            case TORQUE: mode = TransferMode.SPEED; break;
        }
    }

    public TransferMode getMode(){
        return mode;
    }

    @Override
    public double getArea() {
        return 0.65625;
    }
}
