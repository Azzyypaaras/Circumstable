package azzy.fabric.circumstable.staticentities.blockentity;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import azzy.fabric.circumstable.util.InventoryWrapper;
import azzy.fabric.circumstable.util.interaction.HeatHolder;
import azzy.fabric.circumstable.util.interaction.HeatTransferHelper;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static azzy.fabric.circumstable.Circumstable.CLog;


public abstract class MachineEntity extends BlockEntity implements Tickable, InventoryWrapper, SidedInventory, PropertyDelegateHolder, BlockEntityClientSerializable, InventoryProvider, NamedScreenHandlerFactory, HeatHolder {

    //DEFAULT VALUES, DO NOT FORGET TO OVERRIDE THESE

    public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(0, ItemStack.EMPTY);
    protected final Set<Direction> inputs = new HashSet<>();
    public SimpleFixedFluidInv fluidInv;
    protected boolean isActive = false;
    protected double heat;
    protected boolean renderInit;
    protected int renderTickTime;
    private boolean heatInit;
    protected int progress = 0;
    protected HeatTransferHelper.HeatMaterial material;
    long speed;
    long torque;

    protected final PropertyDelegate entityHolder = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return 0;
        }

        @Override
        public void set(int index, int value) {
        }

        @Override
        public int size() {
            return 0;
        }
    };

    public MachineEntity(BlockEntityType<? extends MachineEntity> entityType) {
        super(entityType);
        fluidInv = new SimpleFixedFluidInv(0, new FluidAmount(0));
        heatInit = true;
        renderInit = true;
    }

    public void calcHeat(){
        HeatTransferHelper.simulateAmbientHeat(this, this.world.getBiome(pos));
        simulateSurroundingHeat(pos, this);
    }

    @Override
    public int[] getAvailableSlots(Direction var1) {
        int[] result = new int[size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    @Override
    public void tick() {
        if(heatInit) {
            heat = HeatTransferHelper.translateBiomeHeat(this.getWorld().getBiome(pos));
            heatInit = false;
        }

        for(int i = 0; i < 4; i++)
            calcHeat();

        if(renderInit){
            renderTickTime += 5;
        }
        else if(renderTickTime > 0) {
            renderTickTime -= 2;
        }

        if(renderTickTime >= 120){
            renderInit = false;
        }
        else if(renderTickTime < 0){
            renderTickTime = 0;
        }

        if (!world.isClient)
            sync();
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {

        //Inventory nbt
        Inventories.fromTag(tag, inventory);

        //Fluid nbt
        fluidInv.fromTag(tag.getCompound("fluid"));

        speed = tag.getLong("speed");
        torque = tag.getLong("torque");
        heat = tag.getDouble("heat");

        renderTickTime = tag.getInt("rendertime");
        renderInit = tag.getBoolean("renderinit");

        //State nbt
        progress = tag.getInt("progress");
        isActive = tag.getBoolean("active");
        heatInit = tag.getBoolean("init");

        for(Direction direction : Direction.values()){
            Direction value;
            value = Direction.byName(tag.getString(direction.asString() + "i"));
            if(value != null)
                inputs.add(value);
        }

        super.fromTag(state, tag);

    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return direction == Direction.UP;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN;
    }

    public HeatTransferHelper.HeatMaterial getMaterial() {
        return material;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        //Inventory nbt
        Inventories.toTag(tag, inventory);

        //Fluid nbt
        tag.put("fluid", fluidInv.toTag());

        tag.putLong("speed", speed);
        tag.putLong("torque", torque);
        tag.putDouble("heat", heat);

        tag.putInt("rendertime", renderTickTime);
        tag.putBoolean("renderinit", renderInit);

        //State nbt
        if (isActive) {
            tag.putInt("progress", progress);
            tag.putBoolean("active", isActive);
        }

        for(Direction direction : inputs){
            tag.putString(direction.asString() + "i", direction.asString());
        }

        tag.putBoolean("init", heatInit);

        return tag;
    }

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return entityHolder;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public int getRenderTickTime() {
        return renderTickTime;
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        fluidInv.fromTag(compoundTag.getCompound("fluid"));
        progress = compoundTag.getInt("progress");
        speed = compoundTag.getLong("speed");
        torque = compoundTag.getLong("torque");
        heat = compoundTag.getDouble("heat");

        renderTickTime = compoundTag.getInt("rendertime");
        renderInit = compoundTag.getBoolean("renderinit");

        for(Direction direction : Direction.values()){
            Direction value;
            value = Direction.byName(compoundTag.getString(direction.asString() + "i"));
            if(value != null)
                inputs.add(value);
        }
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        compoundTag.put("fluid", fluidInv.toTag());
        compoundTag.putInt("progress", progress);
        compoundTag.putLong("speed", speed);
        compoundTag.putLong("torque", torque);
        compoundTag.putDouble("heat", heat);

        compoundTag.putInt("rendertime", renderTickTime);
        compoundTag.putBoolean("renderinit", renderInit);

        for(Direction direction : inputs){
            compoundTag.putString(direction.asString() + "i", direction.asString());
        }

        return compoundTag;
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return this;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }

    @Override
    public double getHeat() {
        return heat;
    }

    @Override
    public void moveHeat(double change) {
        heat += change;
    }

    protected void meltDown(boolean cold, @Nullable BlockState state){
        if(cold && state != null){
            world.setBlockState(pos, state);
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f, false);
            if(!world.isClient()){
                ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE, pos.getX()+0.25, pos.up().getY(), pos.getZ()+0.25, 11, 0.25, 0, 0.25, 0);
            }
        }
        else{
            world.setBlockState(pos, Blocks.LAVA.getDefaultState());
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.2f, 0.8f, false);
            if(!world.isClient()){
                ((ServerWorld) world).spawnParticles(ParticleTypes.LARGE_SMOKE, pos.getX()+0.25, pos.up().getY(), pos.getZ()+0.25, 11, 0.25, 0, 0.25, 0);
            }
        }
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

    public void receivePower(long speed, long torque){
        this.speed = speed;
        this.torque = torque;
    }

    public Set<Direction> getInputs() {
        return inputs;
    }

    public abstract void updateIO(BlockState state, BlockPos pos, @Nullable ItemStack item);

    public void renderIO() {
        this.renderInit = true;
    }

    public static <T extends MachineEntity & HeatHolder> void simulateSurroundingHeat(BlockPos pos, T bodyA){
        BlockPos bodyB;
        World world = bodyA.getWorld();

        if(world == null)
            return;

        for(Direction direction : Direction.values()){
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

    @Override
    public double getArea() {
        return 0.75;
    }
}
