package azzy.fabric.circumstable.registry;


import azzy.fabric.circumstable.util.recipe.serializer.BlastFurnaceSerializer;
import azzy.fabric.circumstable.util.recipe.type.BlastFurnaceRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.circumstable.Circumstable.MOD_ID;

public class RecipeRegistry {

    public static RecipeType<BlastFurnaceRecipe> BLAST_RECIPE;
    public static RecipeSerializer<BlastFurnaceRecipe> BLAST_SERIALIZER;

    public static void init(){
        BLAST_RECIPE = registerType("blast_recipe");
        BLAST_SERIALIZER = registerSerializer("blast_recipe", new BlastFurnaceSerializer(BlastFurnaceRecipe::new));
    }

    private static <T extends Recipe<?>, K extends RecipeSerializer<T>> K registerSerializer(String name, K serializer){
        return Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(MOD_ID, name), serializer);
    }

    private static <T extends Recipe<?>> RecipeType<T> registerType(String name){
        return Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, name), new RecipeType<T>() {
            public String toString() {
                return name;
            }
        });
    }

}
