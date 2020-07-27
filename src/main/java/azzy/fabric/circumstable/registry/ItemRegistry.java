package azzy.fabric.circumstable.registry;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.circumstable.Circumstable.MACHINE_MATERIALS;
import static azzy.fabric.circumstable.Circumstable.MOD_ID;

public class ItemRegistry extends Item {

    private ItemRegistry(Item.Settings settings) {
        super(settings);
    }

    //Crafting
    public static Item STEEL_INGOT = register("hsla_steel_ingot", new Item(new Item.Settings().group(MACHINE_MATERIALS)));


    public static void init() {
    }

    private static Item register(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), item);
        return item;
    }

    public static Item registerBucket(String name, FlowableFluid item) {
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), new BucketItem(item, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(MACHINE_MATERIALS)));
    }
}


