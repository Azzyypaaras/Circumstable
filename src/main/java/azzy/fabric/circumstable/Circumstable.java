package azzy.fabric.circumstable;

import azzy.fabric.circumstable.config.ConfigGen;
import azzy.fabric.circumstable.registry.*;
import azzy.fabric.circumstable.util.interaction.HeatTransferHelper;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static azzy.fabric.circumstable.registry.BlockRegistry.BLAST_FURNACE;
import static azzy.fabric.circumstable.registry.ItemRegistry.STEEL_INGOT;

public class Circumstable implements ModInitializer {
    public static final String MOD_ID = "circumstable";

    public static final Logger CLog = LogManager.getLogger(MOD_ID);
    public static volatile ConfigGen config;
    public static final ItemGroup MACHINE_MATERIALS = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "material")).icon(() -> new ItemStack(STEEL_INGOT)).build();
    public static final ItemGroup MACHINES = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "machine")).icon(() -> new ItemStack(BLAST_FURNACE.asItem())).build();
    public static final ItemGroup LOGISTICS = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "logistic")).icon(() -> new ItemStack(Items.POTATO)).build();
    public static final ItemGroup TOOLS  = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "tool")).icon(() -> new ItemStack(Items.POTATO)).build();

    @Override
    public void onInitialize() {

        AutoConfig.register(ConfigGen.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ConfigGen.class).getConfig();

        //Registries
        ItemRegistry.init();
        PotionRegistry.init();
        BlockEntityRegistry.init();
        ContainerRegistry.init();
        FluidRegistry.init();
        HeatTransferHelper.init();
    }
}
