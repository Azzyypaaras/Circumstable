package azzy.fabric.circumstable.registry;

import azzy.fabric.circumstable.block.entity.BlastFurnaceMachine;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.ArrayList;
import java.util.List;

import static azzy.fabric.circumstable.Circumstable.*;

public class BlockRegistry {

    public static final VoxelShape DEFAULT_SHAPE = VoxelShapes.fullCube();

    //Other

    //misc blocks
    public static Block STEEL_BLOCK = register("hsla_steel_block", new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(6f, 8f).sounds(BlockSoundGroup.METAL).materialColor(MaterialColor.IRON).breakByTool(FabricToolTags.PICKAXES, 2)), new Item.Settings().group(MACHINE_MATERIALS));

    //Block Entities
    public static Block BLAST_FURNACE = register("blast_furnace", new BlastFurnaceMachine(FabricBlockSettings.of(Material.STONE, MaterialColor.RED).requiresTool().strength(3f, 4f).sounds(BlockSoundGroup.STONE).breakByTool(FabricToolTags.PICKAXES, 2).lightLevel(e -> e.get(BlastFurnaceMachine.LIT) ? 15 : 0), DEFAULT_SHAPE), new Item.Settings().group(MACHINES));

    //Fluid

    @Environment(EnvType.CLIENT)
    public static final List<Block> REGISTRY_TRANS = new ArrayList<>();
    @Environment(EnvType.CLIENT)
    public static final List<Block> REGISTRY_PARTIAL = new ArrayList<>();

    private static Block register(String name, Block block, Item.Settings settings) {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, settings));
        return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);
    }

    private static Block registerNoItem(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);
    }

    private static Block registerFluidBlock(String name, FlowableFluid item, AbstractBlock.Settings base) {
        return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), new FluidBlock(item, base) {
        });
    }

    @Environment(EnvType.CLIENT)
    public static void initTransparency() {
    }

    @Environment(EnvType.CLIENT)
    public static void initPartialblocks() {
    }
}
