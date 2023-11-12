package aps.module_Machines;

import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
 
public class GrinderRecipe
{
  ItemStack ingredient;
  ItemStack[] products;
  float[] chances;
  float energyRequiredPerBlock;
  
  public GrinderRecipe(ItemStack Ingredient, ItemStack[] Products, float[] Chances, float EnergyRequired)
  {
    this.ingredient = Ingredient;
    this.products = Products;
    if (Chances.length != Products.length)
    {
      String s1 = "";
      for (ItemStack I : Products) { s1 = s1 + I.getItem().getItemName();
      }
      String s2 = "";
      for (float F : Chances) { s2 = s2 + F + ":";
      }
      new Exception("APS GrinderRecipe: Product array and Chance array not same length! \n Info:\n" + Ingredient.getItem().getItemName() + s1 + s2).printStackTrace();
    }
    this.chances = Chances;
    this.energyRequiredPerBlock = EnergyRequired;
  }
  
  public GrinderRecipe(ItemStack Ingredient, ItemStack[] Products, float Chance, float EnergyRequired)
  {
    this.ingredient = Ingredient;
    this.products = Products;
    this.chances = new float[this.products.length];
    for (int i = 0; i < this.products.length; i++) this.chances[i] = Chance;
    this.energyRequiredPerBlock = EnergyRequired;
  }
  
  public GrinderRecipe(ItemStack Ingredient, ItemStack Product, float Chance, float EnergyRequired)
  {
    this.ingredient = Ingredient;
    this.products = new ItemStack[1];
    this.products[0] = Product;
    this.chances = new float[1];
    this.chances[0] = Chance;
    this.energyRequiredPerBlock = EnergyRequired;
  }
  
  public boolean isIngredient(ItemStack I) { if (I == null) return false; return this.ingredient.itemID == I.itemID;
  }
  
  public boolean isProduct(ItemStack P) { if (P == null) return false;
    for (ItemStack product : this.products)
    {
      if (product.itemID == P.itemID) return true;
    }
    return false;
  }
  
  public ItemStack getIngredient() { return this.ingredient; }
  public float getEnergyRequired() { return this.energyRequiredPerBlock; }
  public ItemStack[] getAllProducts() { return this.products; }
  
  public ItemStack getRandomProduct() {
    float R = new Random().nextFloat() * 160.0F;
    float C = 0.0F;
    for (int i = 0; i < this.products.length; i++)
    {
      if (this.products.length > 1)
      {
        if (this.chances.length > 1)
        {
          if (R < C + this.chances[i])
            return this.products[i].copy();
          C += this.chances[i];
        }
        else
        {
          if (R < C + this.chances[0])
            return this.products[i].copy();
          C += this.chances[0];
        }
      } else
        return this.products[0].copy();
    }
    return null;
  }
}
