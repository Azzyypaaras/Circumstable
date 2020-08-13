package azzy.fabric.circumstable.staticentities.blockentity.production;

import azzy.fabric.circumstable.registry.RecipeRegistry;
import azzy.fabric.circumstable.staticentities.blockentity.MachineEntity;
import azzy.fabric.circumstable.util.interaction.HeatTransferHelper;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import static azzy.fabric.circumstable.Circumstable.CLog;
import static azzy.fabric.circumstable.block.entity.BlastFurnaceMachine.LIT;
import static azzy.fabric.circumstable.registry.BlockEntityRegistry.BLAST_FURNACE_ENTITY;

public class BlastFurnaceMachineEntity extends MachineEntity {

    public BlastFurnaceMachineEntity() {
        super(BLAST_FURNACE_ENTITY);
        inventory = DefaultedList.ofSize(10, ItemStack.EMPTY);
        material = HeatTransferHelper.HeatMaterial.STEEL;
    }

    @Override
    public void tick() {
        super.tick();

        if(!this.hasWorld())
            return;

        if(!isActive && this.getCachedState().get(LIT))
            world.setBlockState(pos, this.getCachedState().with(LIT, false));

        if(heat > 1680) {
            meltDown(false, null);
        }
    }

    @Override
    public double getArea() {
        return 1;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        if(slot == 9)
            return false;
        else if(slot > 2 && slot < 9)
            return stack.getItem() == Items.IRON_INGOT || stack.getItem() == Items.IRON_BLOCK;
        else if (slot <= 2)
            return stack.getItem() == Items.CHARCOAL || ItemTags.SAND.contains(stack.getItem()) || stack.getItem() == Items.REDSTONE;
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return slot == 9;
    }

    @Override
    public void updateIO(BlockState state, BlockPos pos, @Nullable ItemStack item) {
    }

    PropertyDelegate delegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch(index){
                case (0): return (int) heat;
                case (1): return 1680;
                case (2): return progress;
                case (3): return 200;
                default: return 0;
            }
        }

        @Override
        public void set(int index, int value) {
        }

        @Override
        public int size() {
            return 4;
        }
    };

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return delegate;
    }
}
