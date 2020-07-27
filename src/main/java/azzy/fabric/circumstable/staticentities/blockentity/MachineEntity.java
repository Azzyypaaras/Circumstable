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

import java.util.List;


public class MachineEntity extends BlockEntity implements Tickable, InventoryWrapper, SidedInventory, PropertyDelegateHolder, BlockEntityClientSerializable, InventoryProvider, NamedScreenHandlerFactory, HeatHolder {

    //DEFAULT VALUES, DO NOT FORGET TO OVERRIDE THESE

    public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(0, ItemStack.EMPTY);
    public SimpleFixedFluidInv fluidInv;
    protected boolean isActive = false;
    protected double heat;
    private boolean heatInit;
    protected int progress = 0;
    protected HeatTransferHelper.HeatMaterial material;

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

    //ALSO OVERRIDE THIS

    public MachineEntity(BlockEntityType<? extends MachineEntity> entityType) {
        super(entityType);
        fluidInv = new SimpleFixedFluidInv(0, new FluidAmount(0));
        heatInit = true;
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

        if (!world.isClient)
            sync();
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {

        //Inventory nbt
        Inventories.fromTag(tag, inventory);

        //Fluid nbt
        fluidInv.fromTag(tag.getCompound("fluid"));


        //State nbt
        progress = tag.getInt("progress");
        isActive = tag.getBoolean("active");
        heatInit = tag.getBoolean("init");
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

        //State nbt
        if (isActive) {
            tag.putInt("progress", progress);
            tag.putBoolean("active", isActive);
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

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        fluidInv.fromTag(compoundTag.getCompound("fluid"));
        progress = compoundTag.getInt("progress");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        compoundTag.put("fluid", fluidInv.toTag());
        compoundTag.putInt("progress", progress);
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

    public static <T extends MachineEntity & HeatHolder> void simulateSurroundingHeat(BlockPos pos, T bodyA){
        List<HeatHolder> sorroundings;
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

}
