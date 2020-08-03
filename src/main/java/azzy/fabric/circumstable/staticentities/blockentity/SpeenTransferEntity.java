package azzy.fabric.circumstable.staticentities.blockentity;

import azzy.fabric.circumstable.staticentities.blockentity.MachineEntity;
import azzy.fabric.circumstable.util.interaction.HeatHolder;
import azzy.fabric.circumstable.util.interaction.HeatTransferHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class SpeenTransferEntity extends MachineEntity {

    protected final Set<Direction> outputs = new HashSet<>();
    protected long maxSpeed, maxTorque;
    protected int maxTemp = 0;
    protected TransferMode mode;
    protected int modifier;

    public SpeenTransferEntity(BlockEntityType<? extends MachineEntity> entityType) {
        super(entityType);
        mode = TransferMode.SPEED;
        modifier = 1;
    }

    @Override
    public void calcHeat() {
        HeatTransferHelper.simulateAmbientHeat(this, this.world.getBiome(pos));
        simulateIoHeat(pos, this);
    }

    @Override
    public void tick() {
        super.tick();
        long outSpeed, outTorque;
        if(outputs.size() != 0) {
            outSpeed = speed / outputs.size();
            outTorque = torque / outputs.size();
            if(mode == TransferMode.SPEED){
                outSpeed *= 2;
                outTorque /= 2;
            }
            else{
                outTorque *= 2;
                outSpeed /= 2;
            }
            for(Direction direction : outputs){
                BlockEntity entity = world.getBlockEntity(pos.offset(direction));
                if(entity instanceof MachineEntity && ((MachineEntity) entity).getInputs().contains(direction.getOpposite()))
                    ((MachineEntity) world.getBlockEntity(pos.offset(direction))).receivePower(outSpeed, outTorque);
            }
        }
    }

    protected void fail(boolean heat, boolean catastrophically, int potency){
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
        if(heat){
            world.playSound(pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_CHAIN_BREAK, SoundCategory.BLOCKS, 2.75f, 0.8f, true);
            world.playSound(pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 2.5f, 0.9f, true);
            world.setBlockState(pos, Blocks.FIRE.getDefaultState());
            if(!world.isClient()){
                ((ServerWorld) world).spawnParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 30, 0, 0, 0, 0.0065);
            }
        }
        else if(!catastrophically){
            world.playSound(pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_ANVIL_BREAK, SoundCategory.BLOCKS, 4.5f, 0.9f, true);
            world.playSound(pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 2.5f, 1.15f, true);
        }

        if(catastrophically){
            world.playSound(pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.5f * potency, 1f, true);
            world.playSound(pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_CHAIN_BREAK, SoundCategory.BLOCKS, 1.75f * potency, 1f, true);
            world.createExplosion(null, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() + 0.5, 3f * potency, heat, Explosion.DestructionType.BREAK);
        }
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public static <T extends SpeenTransferEntity & HeatHolder> void simulateIoHeat(BlockPos pos, T bodyA){
        BlockPos bodyB;
        World world = bodyA.getWorld();

        Set<Direction> directions = new HashSet<>(bodyA.inputs);
        directions.addAll(bodyA.outputs);

        for(Direction direction : directions){
            bodyB = pos.offset(direction);

            if((world.getBlockEntity(bodyB) instanceof  HeatHolder)){
                if(((MachineEntity) world.getBlockEntity(bodyB)).getMaterial() != null)
                    HeatTransferHelper.simulateHeat(((MachineEntity) world.getBlockEntity(bodyB)).getMaterial(), (HeatHolder) world.getBlockEntity(bodyB), bodyA);
                else if(bodyA.getMaterial() != null)
                    HeatTransferHelper.simulateHeat(bodyA.getMaterial(), (HeatHolder) world.getBlockEntity(bodyB), bodyA);
                else
                    HeatTransferHelper.simulateHeat(HeatTransferHelper.HeatMaterial.AIR, (HeatHolder) world.getBlockEntity(bodyB), bodyA);
            }
            else if(HeatTransferHelper.isHeatSource(world.getBlockState(bodyB).getBlock())){
                HeatTransferHelper.simulateHeat(HeatTransferHelper.HeatMaterial.AIR, bodyA, world.getBlockState(bodyB).getBlock());
            }
        }
    }

    public Set<Direction> getOutputs() {
        return outputs;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        for(Direction direction : outputs){
            tag.putString(direction.asString() + "o", direction.asString());
        }
        tag.putString("mode", mode.toString());
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        for(Direction direction : Direction.values()){
            Direction value;
            value = Direction.byName(tag.getString(direction.asString() + "o"));
            if(value != null)
                outputs.add(value);
        }
        mode = TransferMode.valueOf(tag.getString("mode"));
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        for(Direction direction : outputs){
            compoundTag.putString(direction.asString() + "o", direction.asString());
        }
        compoundTag.putString("mode", mode.toString());
        return super.toClientTag(compoundTag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        super.fromClientTag(compoundTag);
        for(Direction direction : Direction.values()){
            Direction value;
            value = Direction.byName(compoundTag.getString(direction.asString() + "o"));
            if(value != null)
                outputs.add(value);
        }
        mode = TransferMode.valueOf(compoundTag.getString("mode"));
    }

    public enum TransferMode{
        SPEED,
        TORQUE
    }
}
