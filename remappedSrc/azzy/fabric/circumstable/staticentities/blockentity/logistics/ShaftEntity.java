package azzy.fabric.circumstable.staticentities.blockentity.logistics;

import azzy.fabric.circumstable.block.entity.SpeenMachine;
import azzy.fabric.circumstable.staticentities.blockentity.FailingTransferEntity;
import azzy.fabric.circumstable.staticentities.blockentity.MachineEntity;
import azzy.fabric.circumstable.staticentities.blockentity.SpeenTransferEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ShaftEntity extends FailingTransferEntity {

    public ShaftEntity(BlockEntityType<? extends MachineEntity> entityType) {
        super(entityType);
    }

    @Override
    public void updateIO(BlockState state, BlockPos pos, @Nullable ItemStack item) {
        outputs.add(state.get(SpeenMachine.FACING));
        inputs.add(state.get(SpeenMachine.FACING).getOpposite());
    }

    @Override
    public double getArea() {
        return 0.140625;
    }
}
