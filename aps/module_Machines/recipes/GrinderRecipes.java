package aps.module_Machines.recipes;

import java.util.LinkedList;
import java.util.List;

import aps.module_Machines.GrinderRecipe;
import ic2.api.Items;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;

public class GrinderRecipes {
	
	public static GrinderRecipe ExtractorProducts;
	public static LinkedList<GrinderRecipe> grinderRecipes = new LinkedList<GrinderRecipe>();
	public static LinkedList MeltableBlocks = new LinkedList();
	
	public static void loadRecipes(){
			grinderRecipes.add(new GrinderRecipe(new ItemStack(Block.gravel), new ItemStack(Item.flint), 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.flint), new ItemStack(Item.gunpowder), 0.75F, 25.0F));
		    
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Block.sandStone), new ItemStack(Block.sand, 4), 100.0F, 50.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Block.brick), new ItemStack(Item.brick, 4), 100.0F, 75.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Block.planks), new ItemStack(Item.stick, 5), 100.0F, 50.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Block.cloth), new ItemStack[] { new ItemStack(Item.silk, 1), new ItemStack(Item.silk, 2), new ItemStack(Item.silk, 4) }, new float[] { 25.0F, 25.0F, 25.0F }, 50.0F));

		    if (ModLoader.isModLoaded("IC2")){
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Block.dirt), new ItemStack[] 
		    		{ 
		    				new ItemStack(Block.cobblestone), 
		    				Items.getItem("copperOre"),
		    				Items.getItem("tinOre"),
		    				Items.getItem("uraniumOre"),
		    				new ItemStack(Block.stone),
		    				new ItemStack(Block.oreCoal), 
		    				new ItemStack(Block.oreIron), 
		    				new ItemStack(Block.oreGold), //mk2 
		    				new ItemStack(Block.oreRedstone), //mk2
		    				new ItemStack(Block.oreLapis), //mk3
		    				new ItemStack(Block.oreDiamond), //mk3
		    				new ItemStack(Block.oreEmerald) },//mk3
			new float[] { 
					20.0F, 
					1.2F, 
					1.2F,
					0.4F,
					4.0F,
					2.5F, 
					2.0F, 
					0.5F, 
					0.5F, 
					0.5F, 
					0.1F, 
					0.1F 
			}, 
			50.0F));
		    }else{
		    	grinderRecipes.add(new GrinderRecipe(new ItemStack(Block.dirt), new ItemStack[] 
		        		{ 
		        				new ItemStack(Block.cobblestone), 
		        				new ItemStack(Block.stone),
		        				new ItemStack(Block.oreCoal), 
		        				new ItemStack(Block.oreIron), 
		        				new ItemStack(Block.oreGold), //mk2 
		        				new ItemStack(Block.oreRedstone), //mk2
		        				new ItemStack(Block.oreLapis), //mk3
		        				new ItemStack(Block.oreDiamond), //mk3
		        				new ItemStack(Block.oreEmerald) },//mk3
		    	new float[] { 
		    			20.0F, 
		    			4.0F, 
		    			2.5F, 
		    			2.0F, 
		    			0.4F, 
		    			0.5F, 
		    			0.5F, 
		    			0.1F, 
		    			0.1F 
		    	}, 
		    	50.0F));
		    }
		    
		    if (ModLoader.isModLoaded("IC2")){
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Block.cobblestone), new ItemStack[] 
		    { 
		    		new ItemStack(Block.gravel), 
					Items.getItem("copperOre"),
					Items.getItem("tinOre"),
					Items.getItem("uraniumOre"),
		    		new ItemStack(Block.oreCoal),
		    		new ItemStack(Block.oreIron),
					new ItemStack(Block.oreRedstone), //mk2
					new ItemStack(Block.oreGold), //mk2 
					new ItemStack(Block.oreLapis), //mk3
					new ItemStack(Block.oreDiamond), //mk3
					new ItemStack(Block.oreEmerald) },//mk3
		    new float[] { 
		    		50.0F, //gravel
		    		2.0F, //copper
		    		2.0F, //tin
		    		0.8F, //uranium
		    		2.5F, //coal
					2.0F,//iron
		    		0.5F,//redstone 
		    		0.4F, //gold
		    		0.5F, //lapis
		    		0.1F, //Diamond
		    		0.1F  //Emeralds
		    		},
		    70.0F));
		    }
		    else{
		    	grinderRecipes.add(new GrinderRecipe(new ItemStack(Block.cobblestone), new ItemStack[] 
		    		    { 
		    		    		new ItemStack(Block.gravel), 
		    		    		new ItemStack(Block.oreCoal),
		    		    		new ItemStack(Block.oreIron),
		    		    		new ItemStack(Block.oreRedstone), //mk2
		    		    		new ItemStack(Block.oreGold), //mk2
		    		    		new ItemStack(Block.oreLapis), //mk3
		    		    		new ItemStack(Block.oreDiamond),//mk3
		    		    		new ItemStack(Block.oreEmerald)},//mk3
		    		    new float[] { 
		    		    		50.0F, //gravel
		    		    		2.5F, //coal
		    					2.0F,//iron
		    		    		0.5F,//redstone 
		    		    		0.4F, //gold
		    		    		0.5F, //lapis
		    		    		0.1F, //Diamond
		    		    		0.1F  //Emeralds
		    		    		},
		    		    75.0F));
		    }
		    
		    if (ModLoader.isModLoaded("IC2")){
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Block.stone), new ItemStack[] 
		    		{ 
		    				new ItemStack(Block.gravel),
		    				Items.getItem("copperOre"), 
		    				Items.getItem("tinOre"),
		    				Items.getItem("uraniumOre"),
		    				new ItemStack(Block.oreIron), 
		    				new ItemStack(Block.oreRedstone), // mk2 
		    				new ItemStack(Block.oreGold), // mk2
		    				new ItemStack(Block.oreLapis),  // mk3
		    				new ItemStack(Block.oreDiamond), // mk3
		    				new ItemStack(Block.oreEmerald)}, // mk3
			new float[] { 
					50.0F, // gravel
					2.0F,  // copper
					2.0F,  // tin
					0.8F,  // uran
					2.0F,  // iron
					0.5F,  // redstone
					0.4F,  // gold
					0.5F,  // lapis
					0.1F,  // diamond
					0.1F   // Emerald
					
		    }, 
			70.0F));
		    }
		    else{
		    	 grinderRecipes.add(new GrinderRecipe(new ItemStack(Block.stone), new ItemStack[] 
		    	    		{ 
		    	    				new ItemStack(Block.gravel),
		    	    				new ItemStack(Block.oreIron), 
		    	    				new ItemStack(Block.oreRedstone),  // mk2
		    	    				new ItemStack(Block.oreGold),  // mk2
		    	    				new ItemStack(Block.oreLapis),  // mk3
		    	    				new ItemStack(Block.oreDiamond), // mk3
		    	    				new ItemStack(Block.oreEmerald)}, // mk3
		    		new float[] { 
		    				50.0F, // gravel  
		    				2.0F,  // iron
		    				0.5F,  // redstone
		    				0.3F,  // gold
		    				0.5F,  // lapis
		    				0.1F,  // diamond
		    				0.1F  // emerald
		    				
		    	    }, 
		    		70.0F));
		    }
		  
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.pickaxeWood), new ItemStack[] { new ItemStack(Block.planks, 2) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.axeWood), new ItemStack[] { new ItemStack(Block.planks, 2) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.swordWood), new ItemStack[] { new ItemStack(Block.planks, 1) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.hoeWood), new ItemStack[] { new ItemStack(Block.planks, 1) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.pickaxeStone), new ItemStack[] { new ItemStack(Block.cobblestone, 2) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.axeStone), new ItemStack[] { new ItemStack(Block.cobblestone, 2) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.swordStone), new ItemStack[] { new ItemStack(Block.cobblestone, 1) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.hoeStone), new ItemStack[] { new ItemStack(Block.cobblestone, 1) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.pickaxeSteel), new ItemStack[] { new ItemStack(Item.ingotIron, 2) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.axeSteel), new ItemStack[] { new ItemStack(Item.ingotIron, 2) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.swordSteel), new ItemStack[] { new ItemStack(Item.ingotIron, 1) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.hoeSteel), new ItemStack[] { new ItemStack(Item.ingotIron, 1) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.pickaxeGold), new ItemStack[] { new ItemStack(Item.ingotGold, 2) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.axeGold), new ItemStack[] { new ItemStack(Item.ingotGold, 2) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.swordGold), new ItemStack[] { new ItemStack(Item.ingotGold, 1) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.hoeGold), new ItemStack[] { new ItemStack(Item.ingotGold, 1) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.pickaxeDiamond), new ItemStack[] { new ItemStack(Item.diamond, 2) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.axeDiamond), new ItemStack[] { new ItemStack(Item.diamond, 2) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.swordDiamond), new ItemStack[] { new ItemStack(Item.diamond, 1) }, 100.0F, 25.0F));
		    grinderRecipes.add(new GrinderRecipe(new ItemStack(Item.hoeDiamond), new ItemStack[] { new ItemStack(Item.diamond, 1) }, 100.0F, 25.0F));
		    
	}

	public static void loadRecipesExtractor() {
		if (ModLoader.isModLoaded("IC2")){
	    	ExtractorProducts = new GrinderRecipe(null, new ItemStack[]{
	    			new ItemStack(Item.ingotIron),
	    			new ItemStack(Item.redstone),
	    			new ItemStack(Block.obsidian),
	    			new ItemStack(Item.ingotGold),
	    			new ItemStack(Item.diamond),
	    			Items.getItem("copperIngot"),
	    			Items.getItem("tinIngot")
	    			}, new float[]{
	    			0.03125f, // ironIngot
	    			0.0125f, //redstone
	    			0.009375f, // obsidian
	    			0.00625f, // goldIngot
	    			0.003125f, // diamond
	    			0.03125f, // copperIngot
	    			0.03125f, // tinIngot
	    			},
		0);
		}else{
			ExtractorProducts = new GrinderRecipe(null, new ItemStack[]{
					new ItemStack(Item.ingotIron),
					new ItemStack(Item.redstone),
					new ItemStack(Block.obsidian),
					new ItemStack(Item.ingotGold),
					new ItemStack(Item.diamond),
					}, new float[]{
			    			0.03125f, // ironIngot
			    			0.0125f, //redstone
			    			0.009375f, // obsidian
			    			0.00625f, // goldIngot
			    			0.003125f // diamond
					}, 
		0);
		}		
	}

	public static void loadRecipesMagmafier() {
		MeltableBlocks.add(Block.stone);
	    MeltableBlocks.add(Block.cobblestone);
	    MeltableBlocks.add(Block.gravel);
	    MeltableBlocks.add(Block.netherrack);
	    MeltableBlocks.add(Block.slowSand);
	}
	
	public static boolean isMeltable(ItemStack stack)
	{
	    if (stack.itemID > 255)
	      return false;
	    return MeltableBlocks.contains(Block.blocksList[stack.itemID]);
	}

}
