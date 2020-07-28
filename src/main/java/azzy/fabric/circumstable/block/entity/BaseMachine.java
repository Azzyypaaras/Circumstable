package azzy.fabric.circumstable.block.entity;

import alexiil.mc.lib.attributes.AttributeList;
import alexiil.mc.lib.attributes.AttributeProvider;
import azzy.fabric.circumstable.staticentities.blockentity.MachineEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.math.BigInteger;

import static azzy.fabric.circumstable.Circumstable.CLog;

public class BaseMachine extends HorizontalFacingBlock implements BlockEntityProvider, AttributeProvider {

    protected final VoxelShape bounds;
    private long speed;
    private long torque;

    public BaseMachine(FabricBlockSettings settings, VoxelShape bounds) {
        super(settings);
        this.bounds = bounds;
        setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity != null) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.HORIZONTAL_FACING);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return bounds;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return bounds;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return null;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    public long getSpeed() {
        return speed;
    }

    public long getTorque() {
        return torque;
    }

    public long getPower() {
        return speed * torque;
    }

    public void increaseSpeed(int factor){
        if(factor % 2 != 0){
            CLog.error("Very bad speen, so many conflicts with other speens and so many problems, trying to sneak in bad factors but I hereby block your malicious attempt.");
            return;
        }
        speed *= factor;
        torque /= factor;
    }

    public void decreaseSpeed(int factor){
        if(factor % 2 != 0){
            CLog.error("Very bad speen, so many conflicts with other speens and so many problems, trying to sneak in bad factors but I hereby block your malicious attempt.");
            return;
        }
        speed /= factor;
        torque *= factor;
    }

    public void increaseTorque(int factor){
        if(factor % 2 != 0){
            CLog.error("Very bad speen, so many conflicts with other speens and so many problems, trying to sneak in bad factors but I hereby block your malicious attempt.");
            return;
        }
        torque *= factor;
        speed /= factor;
    }

    public void decreaseTorque(int factor){
        if(factor % 2 != 0){
            CLog.error("Very bad speen, so many conflicts with other speens and so many problems, trying to sneak in bad factors but I hereby block your malicious attempt.");
            return;
        }
        torque /= factor;
        speed *= factor;
    }

    public void increasePower(int factor){
        if(factor % 2 != 0){
            CLog.error("Very bad speen, so many conflicts with other speens and so many problems, trying to sneak in bad factors but I hereby block your malicious attempt.");
            return;
        }
        torque /= factor;
        speed /= factor;
    }

    public void decreasePower(int factor){
        if(factor % 2 != 0){
            CLog.error("Very bad speen, so many conflicts with other speens and so many problems, trying to sneak in bad factors but I hereby block your malicious attempt.");
            return;
        }
        torque *= factor;
        speed *= factor;
    }

    @Override
    public void addAllAttributes(World world, BlockPos blockPos, BlockState blockState, AttributeList<?> attributeList) {
    }

}
