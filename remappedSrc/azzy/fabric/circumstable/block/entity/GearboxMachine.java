package azzy.fabric.circumstable.block.entity;

import azzy.fabric.circumstable.staticentities.blockentity.MachineEntity;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.ShaftEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class GearboxMachine extends SpeenMachine {


    public static final BooleanProperty TYPE;
    private static final VoxelShape BOTTOM_SHAPE;
    private static final VoxelShape TOP_SHAPE;

    public GearboxMachine(FabricBlockSettings settings, Supplier<BlockEntity> entity) {
        super(settings, entity, VoxelShapes.fullCube());
        setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(TYPE, false));

    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        boolean type = state.get(TYPE);
        if(type){
            return TOP_SHAPE;
        }
        return BOTTOM_SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        super.appendProperties(stateManager);
        stateManager.add(TYPE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        boolean type = state.get(TYPE);
        if(type){
            return TOP_SHAPE;
        }
        return BOTTOM_SHAPE;
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState state = this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());

        return direction != Direction.DOWN && (direction == Direction.UP || ctx.getHitPos().y - (double)blockPos.getY() <= 0.5D) ? state.with(TYPE, false) : state.with(TYPE, true);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        MachineEntity entity = (MachineEntity) world.getBlockEntity(pos);
        entity.updateIO(state, pos, null);
    }

    static {
        TYPE = BooleanProperty.of("inverted");
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
        TOP_SHAPE = Block.createCuboidShape(0.0D, 4.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }
}
