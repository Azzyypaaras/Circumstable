package azzy.fabric.circumstable.block.entity;

import azzy.fabric.circumstable.staticentities.blockentity.production.BlastFurnaceMachineEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class SpeenMachine extends BaseMachine{

    public final Supplier<BlockEntity> entity;

    public SpeenMachine(FabricBlockSettings settings, Supplier<BlockEntity> entity, VoxelShape shape) {
        super(settings, shape);
        this.entity = entity;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getSide());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return entity.get();
    }

    static {
        FACING = Properties.FACING;
    }
}
