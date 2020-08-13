package azzy.fabric.circumstable.util.recipe.serializer;

import azzy.fabric.circumstable.util.recipe.type.BlastFurnaceRecipe;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

import static azzy.fabric.circumstable.Circumstable.CLog;

public class BlastFurnaceSerializer implements RecipeSerializer<BlastFurnaceRecipe> {

    private final RecipeFactory recipeFactory;

    public BlastFurnaceSerializer(RecipeFactory recipeFactory) {
        this.recipeFactory = recipeFactory;
    }

    @Override
    public BlastFurnaceRecipe read(Identifier id, JsonObject json) {
        JsonObject inputs = json.getAsJsonObject("input");
        JsonObject additive;
        HashMap<Ingredient, Float> extra = new HashMap<>();
        for(int i = 1; i < 4; i++){
            additive = json.getAsJsonObject("additive"+i);
            if(additive != null){
                extra.put(Ingredient.fromJson(additive.get("ingredient")), additive.get("chance").getAsFloat());
            }
        }
        Item output = Registry.ITEM.get(new Identifier(json.get("result").getAsString()));
        double heat = json.get("heat").getAsDouble();

        Ingredient input = Ingredient.fromJson(inputs.get("ingredient"));

       return this.recipeFactory.create(id, input, inputs.get("bonus").getAsInt(), inputs.get("chance").getAsFloat(), extra, output, heat);
    }

    @Override
    public BlastFurnaceRecipe read(Identifier id, PacketByteBuf buf) {
        return null;
    }

    @Override
    public void write(PacketByteBuf buf, BlastFurnaceRecipe recipe) {
    }

    public interface RecipeFactory {
        BlastFurnaceRecipe create(Identifier id, Ingredient input, int bonus, float chance, HashMap<Ingredient, Float> additives, Item output, double heat);
    }

    private class Additive{

        Ingredient ingredient;
        float chance;

        public Additive(Ingredient ingredient, float chance){
            this.ingredient = ingredient;
            this.chance = chance;
        }
    }
}
