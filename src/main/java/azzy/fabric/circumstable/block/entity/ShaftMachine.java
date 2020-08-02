package azzy.fabric.circumstable.block.entity;

import azzy.fabric.circumstable.staticentities.blockentity.logistics.ShaftEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class ShaftMachine extends SpeenMachine{

    private static final VoxelShape UD = VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 1, 0.6875);
    private static final VoxelShape WE = VoxelShapes.cuboid(0, 0.3125, 0.3125, 1, 0.6875, 0.6875);
    private static final VoxelShape NS = VoxelShapes.cuboid(0.3125, 0.3125, 0, 0.6875, 0.6875, 1);

    public ShaftMachine(FabricBlockSettings settings, Supplier<BlockEntity> entity) {
        super(settings, entity, null);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return calcShape(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return calcShape(state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        ShaftEntity entity = (ShaftEntity) world.getBlockEntity(pos);
        entity.updateIO(state, pos, null);
    }

    protected VoxelShape calcShape(BlockState state){
        switch(state.get(FACING)){
            case UP:
            case DOWN: return UD;
            case EAST:
            case WEST: return WE;
            default: return NS;
        }
    }
}
