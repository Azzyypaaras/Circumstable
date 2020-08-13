package azzy.fabric.circumstable.registry;

import azzy.fabric.circumstable.item.AngularItem;
import azzy.fabric.circumstable.item.IOItem;
import azzy.fabric.circumstable.item.ThermometerItem;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.circumstable.Circumstable.*;

public class ItemRegistry extends Item {

    private ItemRegistry(Item.Settings settings) {
        super(settings);
    }
    private static final Item.Settings MATERIAL = new Item.Settings().group(MACHINE_MATERIALS);
    private static final Item.Settings TOOL = new Item.Settings().group(TOOLS);
    private static final Item.Settings SPECIAL = new Item.Settings().rarity(Rarity.EPIC);

    //Crafting
    public static Item STEEL_INGOT = register("hsla_steel_ingot", new Item(MATERIAL));
    public static Item TITANIUM_INGOT = register("titanium_ingot", new Item(MATERIAL));

    //Tools
    public static Item THERMOMETER = register("thermometer", new ThermometerItem(TOOL));
    public static Item IOSCANNER = register("ioscanner", new IOItem(TOOL));
    public static Item TRANSDUCER = register("transducer", new AngularItem(TOOL));

    //Technical
    public static Item STICK_GRANITE = register("shaft_core_granite", new Item(SPECIAL));
    public static Item STICK_STEEL = register("shaft_core_steel", new Item(SPECIAL));
    public static Item STICK_DIAMOND = register("shaft_core_diamond", new Item(SPECIAL));
    public static Item STICK_TUNGSTEN = register("shaft_core_tungsten", new Item(SPECIAL));
    public static Item STICK_TITANIUM = register("shaft_core_titanium", new Item(SPECIAL));

    public static void init(){}

    private static Item register(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), item);
        return item;
    }

    public static Item registerBucket(String name, FlowableFluid item) {
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), new BucketItem(item, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(MACHINE_MATERIALS)));
    }
}


