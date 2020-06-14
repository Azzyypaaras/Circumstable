package azzy.fabric.azzyfruits.block.BEBlocks;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import azzy.fabric.azzyfruits.block.BaseMachine;
import azzy.fabric.azzyfruits.tileentities.blockentity.BarrelEntity;
import azzy.fabric.azzyfruits.tileentities.blockentity.MachineEntity;
import azzy.fabric.azzyfruits.tileentities.blockentity.PressEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;

public class BarrelBlock extends BaseMachine {

    public static final Identifier GID = new Identifier(MODID, "barrel_gui");

    public BarrelBlock(Settings settings, String identifier, Material material, BlockSoundGroup sound, int glow, VoxelShape bounds, ParticleEffect... effects) {
        super(settings, identifier, material, sound, glow, bounds, effects);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return ActionResult.SUCCESS;
    }

    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BarrelEntity) {
            ItemScatterer.spawn(world, (BlockPos)pos, (Inventory)((BarrelEntity)blockEntity));
            world.updateHorizontalAdjacent(pos, this);
        }
        super.onBlockRemoved(state, world, pos, newState, moved);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BarrelEntity();
    }
}