package com.mycompany.chemmastergui;

import java.io.Serializable;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Chemical implements Serializable {

    private String name;
    private String temperature;
    private Float amount;
    private HashMap<Chemical, Float> recipe;
    private Color color = Color.WHITE;

    public Chemical(String name, String temperature, Float amount, HashMap<Chemical, Float> recipe, Color color) {
        this.name = name;
        this.temperature = temperature;
        this.amount = amount;
        this.recipe = recipe;
        if (color != null) {

            this.color = color;
        }
    }
    // Getters

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getTemperature() {
        return temperature;
    }

    public Float getAmount() {
        return amount;
    }

    public HashMap<Chemical, Float> getRecipe(Float wantedAmount) {
        HashMap<Chemical, Float> res = new HashMap<>();
        for (Map.Entry<Chemical, Float> entry : recipe.entrySet()) {
            Chemical chem = entry.getKey();
            Float value = entry.getValue();
            Float ingredientAmount = (wantedAmount / recipe.size() * value) * recipe.size() / amount;
            res.put(chem, ingredientAmount);
        }
        return res;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setRecipe(HashMap<Chemical, Float> recipe) {
        this.recipe = recipe;

    }

    // recipes
    public void getFullRecipe(float wantedSize) {

        if (hasDepth()) {
            System.out.println("{");
            System.out.println("name: " + name);
            System.out.println("temp: " + temperature);

            if (recipe != null) {
                getRecipeString(wantedSize);
                recipe.forEach((chem, value) -> {
                    Float res = (wantedSize / recipe.size() * value) * recipe.size() / amount;
                    chem.getFullRecipe(res);
                });
            }
        }
    }

    public String getRecipeString(Float wantedSize) {
        String out = new String();
        for (Map.Entry<Chemical, Float> entry : recipe.entrySet()) {
            Chemical chem = entry.getKey();
            Float value = entry.getValue();
            Float res = (wantedSize / recipe.size() * value) * recipe.size() / amount;
            out += chem.getName() + " " + res + "\n";
        }

        return out;
    }

    public boolean hasDepth() {
        return recipe != null;
    }
}
