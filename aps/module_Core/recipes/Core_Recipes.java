package aps.module_Core.recipes;

import aps.module_Core.module_Core;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftSilicon;
import buildcraft.api.recipes.AssemblyRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;

public class Core_Recipes {

	public static void initRecipesAssembly() {
		//Maincraft recipes Pickaxe Fixes
		AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.pickaxeWood), new ItemStack(Block.planks, 2) }, 500, new ItemStack(Item.pickaxeWood)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.axeWood), new ItemStack(Block.planks, 2) }, 500, new ItemStack(Item.axeWood)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.shovelWood), new ItemStack(Block.planks, 1) }, 500, new ItemStack(Item.shovelWood)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.swordWood), new ItemStack(Block.planks, 1) }, 500, new ItemStack(Item.swordWood)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.hoeWood), new ItemStack(Block.planks, 1) }, 500, new ItemStack(Item.hoeWood)));
	    
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.pickaxeStone), new ItemStack(Block.cobblestone, 2) }, 1000, new ItemStack(Item.pickaxeStone)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.axeStone), new ItemStack(Block.cobblestone, 2) }, 1000, new ItemStack(Item.axeStone)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.shovelStone), new ItemStack(Block.cobblestone, 1) }, 1000, new ItemStack(Item.shovelStone)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.swordStone), new ItemStack(Block.cobblestone, 1) }, 1000, new ItemStack(Item.swordStone)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.hoeStone), new ItemStack(Block.cobblestone, 1) }, 1000, new ItemStack(Item.hoeStone)));
	    
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.pickaxeSteel), new ItemStack(Item.ingotIron, 2) }, 2500, new ItemStack(Item.pickaxeSteel)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.axeSteel), new ItemStack(Item.ingotIron, 2) }, 2500, new ItemStack(Item.axeSteel)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.shovelSteel), new ItemStack(Item.ingotIron, 1) }, 2500, new ItemStack(Item.shovelSteel)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.swordSteel), new ItemStack(Item.ingotIron, 1) }, 2500, new ItemStack(Item.swordSteel)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.hoeSteel), new ItemStack(Item.ingotIron, 1) }, 2500, new ItemStack(Item.hoeSteel)));
	    
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.pickaxeGold), new ItemStack(Item.ingotGold, 2) }, 5000, new ItemStack(Item.pickaxeGold)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.axeGold), new ItemStack(Item.ingotGold, 2) }, 5000, new ItemStack(Item.axeGold)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.shovelGold), new ItemStack(Item.ingotGold, 1) }, 5000, new ItemStack(Item.shovelGold)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.swordGold), new ItemStack(Item.ingotGold, 1) }, 5000, new ItemStack(Item.swordGold)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.hoeGold), new ItemStack(Item.ingotGold, 1) }, 5000, new ItemStack(Item.hoeGold)));
	    
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.pickaxeDiamond), new ItemStack(Item.diamond, 2) }, 10000, new ItemStack(Item.pickaxeDiamond)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.axeDiamond), new ItemStack(Item.diamond, 2) }, 10000, new ItemStack(Item.axeDiamond)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.shovelDiamond), new ItemStack(Item.diamond, 1) }, 10000, new ItemStack(Item.shovelDiamond)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.swordDiamond), new ItemStack(Item.diamond, 1) }, 10000, new ItemStack(Item.swordDiamond)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.hoeDiamond), new ItemStack(Item.diamond, 1) }, 10000, new ItemStack(Item.hoeDiamond)));
	    
	     
	}

	public static void initRecipesAssembyChipsets() {
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(Item.redstone, 2), new ItemStack(BuildCraftSilicon.redstoneChipset) }, 3000, new ItemStack(module_Core.APSPowerCoreItem, 1)));  
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(module_Core.APSPowerCoreItem, 1, 0), new ItemStack(Item.ingotIron, 2) }, 5000, new ItemStack(module_Core.APSPowerCoreItem, 1, 1)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(module_Core.APSPowerCoreItem, 1, 1), new ItemStack(Item.ingotGold, 2) }, 7500, new ItemStack(module_Core.APSPowerCoreItem, 1, 2)));
	    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(module_Core.APSPowerCoreItem, 1, 2), new ItemStack(Item.diamond, 2) }, 10000, new ItemStack(module_Core.APSPowerCoreItem, 1, 3)));
	}

	public static void initRecipesMinecraft() {
	    ModLoader.addRecipe(new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 0), new Object[] { "rrr", " r ", "rrr", Character.valueOf('r'), Item.redstone });
	    ModLoader.addRecipe(new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 1), new Object[] { "rrr", "IrI", "rrr", Character.valueOf('r'), Item.redstone, Character.valueOf('I'), Item.ingotIron });
	    ModLoader.addRecipe(new ItemStack(module_Core.APSPowerCoreItem, 1, 0), new Object[] { "IrI", "GRG", "IrI", Character.valueOf('I'), Item.ingotIron, Character.valueOf('G'), BuildCraftCore.woodenGearItem, Character.valueOf('r'), Item.redstone, Character.valueOf('R'), new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 0) });
	    ModLoader.addRecipe(new ItemStack(module_Core.APSPowerCoreItem, 1, 1), new Object[] { "IrI", "rRr", "IGI", Character.valueOf('I'), Item.ingotIron, Character.valueOf('G'), BuildCraftCore.ironGearItem, Character.valueOf('r'), Item.redstone, Character.valueOf('R'), new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 1) });	 
	}

}
