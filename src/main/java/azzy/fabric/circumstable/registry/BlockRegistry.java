package azzy.fabric.circumstable.registry;

import azzy.fabric.circumstable.Circumstable;
import azzy.fabric.circumstable.block.entity.BlastFurnaceMachine;
import azzy.fabric.circumstable.block.entity.GearboxMachine;
import azzy.fabric.circumstable.block.entity.ShaftMachine;
import azzy.fabric.circumstable.block.entity.SpeenMachine;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.*;
import azzy.fabric.circumstable.staticentities.blockentity.logistics.gearboxes.SteelGearboxEntity2;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.client.render.model.BakedModelManager;
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

    private static final Item.Settings MATERIAL = new Item.Settings().group(MACHINE_MATERIALS);
    private static final Item.Settings LOGISTICS = new Item.Settings().group(Circumstable.LOGISTICS);


    //Other
    public static Block BLACKSTONE_PANEL = register("blackstone_panel", new Block(FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.GILDED_BLACKSTONE).breakByTool(FabricToolTags.PICKAXES, 1)), MATERIAL);

    //misc blocks
    public static Block STEEL_BLOCK = register("hsla_steel_block", new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(6f, 8f).sounds(BlockSoundGroup.METAL).materialColor(MaterialColor.IRON).breakByTool(FabricToolTags.PICKAXES, 2)), MATERIAL);
    public static Block TITANIUM_BLOCK = register("titanium_block", new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(7f, 6f).sounds(BlockSoundGroup.METAL).materialColor(MaterialColor.WHITE).breakByTool(FabricToolTags.PICKAXES, 2)), MATERIAL);
    public static Block TUNGSTEN_BLOCK = register("tungsten_block", new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(20f, 50f).sounds(BlockSoundGroup.METAL).materialColor(MaterialColor.BLACK).breakByTool(FabricToolTags.PICKAXES, 3)), MATERIAL.fireproof());


    //Logistics
    public static Block GRANITE_SHAFT = register("granite_shaft", new ShaftMachine(FabricBlockSettings.copyOf(Blocks.GRANITE).nonOpaque(), GraniteShaftEntity::new), LOGISTICS);
    public static Block STEEL_SHAFT = register("hsla_steel_shaft", new ShaftMachine(FabricBlockSettings.of(Material.METAL).requiresTool().strength(6f, 8f).sounds(BlockSoundGroup.METAL).materialColor(MaterialColor.IRON).breakByTool(FabricToolTags.PICKAXES, 2).nonOpaque(), SteelShaftEntity::new), LOGISTICS);
    public static Block DIAMOND_SHAFT = register("diamond_shaft", new ShaftMachine(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK).nonOpaque(), DiamondShaftEntity::new), LOGISTICS);
    public static Block TUNGSTEN_SHAFT = register("tungsten_shaft", new ShaftMachine(FabricBlockSettings.copyOf(TUNGSTEN_BLOCK).nonOpaque(), TungstenShaftEntity::new), LOGISTICS);
    public static Block TITANIUM_SHAFT = register("titanium_shaft", new ShaftMachine(FabricBlockSettings.copyOf(TITANIUM_BLOCK).nonOpaque(), TitaniumShaftEntity::new), LOGISTICS);

    public static Block STEEL_GEARBOX_2 = register("steel_gearbox_2", new GearboxMachine(FabricBlockSettings.copyOf(STEEL_SHAFT), SteelGearboxEntity2::new), LOGISTICS);

    //Machines
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
