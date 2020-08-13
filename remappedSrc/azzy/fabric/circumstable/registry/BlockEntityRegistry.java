package azzy.fabric.circumstable.registry;

import azzy.fabric.circumstable.staticentities.blockentity.logistics.*;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.gearboxes.SteelGearboxEntity2;
import azzy.fabric.circumstable.staticentities.blockentity.production.BlastFurnaceMachineEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

import static azzy.fabric.circumstable.Circumstable.MOD_ID;
import static azzy.fabric.circumstable.registry.BlockRegistry.*;

public class BlockEntityRegistry {

    //Machines
    public static final BlockEntityType<BlastFurnaceMachineEntity> BLAST_FURNACE_ENTITY = register(BlastFurnaceMachineEntity::new, BLAST_FURNACE, "blast_furnace_entity");

    //Shafts
    public static final BlockEntityType<SteelShaftEntity> STEEL_SHAFT_ENTITY = register(SteelShaftEntity::new, STEEL_SHAFT, "steel_shaft_entity");
    public static final BlockEntityType<GraniteShaftEntity> GRANITE_SHAFT_ENTITY = register(GraniteShaftEntity::new, GRANITE_SHAFT, "granite_shaft_entity");
    public static final BlockEntityType<DiamondShaftEntity> DIAMOND_SHAFT_ENTITY = register(DiamondShaftEntity::new, DIAMOND_SHAFT, "diamond_shaft_entity");
    public static final BlockEntityType<TungstenShaftEntity> TUNGSTEN_SHAFT_ENTITY = register(TungstenShaftEntity::new, TUNGSTEN_SHAFT, "tungsten_shaft_entity");
    public static final BlockEntityType<TitaniumShaftEntity> TITANIUM_SHAFT_ENTITY = register(TitaniumShaftEntity::new, TITANIUM_SHAFT, "titanium_shaft_entity");

    //Gearboxes
    public static final BlockEntityType<SteelGearboxEntity2> STEEL_GEARBOX_ENTITY_2 = register(SteelGearboxEntity2::new, STEEL_GEARBOX_2, "steel_gearbox_2x");

    public static <T extends BlockEntity> BlockEntityType<T> register(Supplier<T> entityFactory, Block block, String name) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, name), BlockEntityType.Builder.create(entityFactory, block).build(null));
    }
}
