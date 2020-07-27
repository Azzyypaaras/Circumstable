package azzy.fabric.circumstable.util.recipe.type;

import azzy.fabric.circumstable.registry.RecipeRegistry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.HashMap;

import static azzy.fabric.circumstable.registry.ItemRegistry.STEEL_INGOT;

public class BlastFurnaceRecipe implements Recipe<Inventory> {

    private final RecipeType<BlastFurnaceRecipe> type;
    private final Identifier id;
    private final Ingredient input;
    private final int bonus;
    private final float chance;
    private final HashMap<Ingredient, Float> additives;
    private final Item output;
    private final double heat;

    public BlastFurnaceRecipe(Identifier id, Ingredient input, int bonus, float chance, HashMap<Ingredient, Float> additives, Item output, double heat){
        type = RecipeRegistry.BLAST_RECIPE;
        this.id = id;
        this.input = input;
        this.bonus = bonus;
        this.chance = chance;
        this.additives = additives;
        this.output = output;
        this.heat = heat;
    }

    @Override
    public boolean matches(Inventory inv, World world) {
        return true;
    }

    @Override
    public ItemStack craft(Inventory inv) {
        return new ItemStack(STEEL_INGOT);
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return null;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<BlastFurnaceRecipe> getSerializer() {
        return RecipeRegistry.BLAST_SERIALIZER;
    }

    @Override
    public RecipeType<BlastFurnaceRecipe> getType() {
        return type;
    }
}
