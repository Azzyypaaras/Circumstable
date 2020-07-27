package azzy.fabric.circumstable.registry;

import azzy.fabric.circumstable.block.entity.BlastFurnaceMachine;
import azzy.fabric.circumstable.staticentities.blockentity.BlastFurnaceMachineEntity;
import net.minecraft.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.circumstable.Circumstable.MOD_ID;
import static azzy.fabric.circumstable.registry.BlockRegistry.*;

public class BlockEntityRegistry {

    public static BlockEntityType<BlastFurnaceMachineEntity> BLAST_FURNACE_ENTITY = BlockEntityType.Builder.create(BlastFurnaceMachineEntity::new, BLAST_FURNACE).build(null);

    public static void register(BlockEntityType<? extends BlockEntity> blockEntityType, String name) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, name), blockEntityType);
    }

    public static void init() {
        register(BLAST_FURNACE_ENTITY, "blast_furnace_entity");
    }
}
