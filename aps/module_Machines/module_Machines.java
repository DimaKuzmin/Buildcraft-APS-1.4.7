package aps.module_Machines;

import aps.module;
import aps.module_Core.module_Core;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftEnergy;
import buildcraft.BuildCraftTransport;
import buildcraft.api.gates.Action;
import buildcraft.api.gates.ActionManager;
import buildcraft.api.gates.Trigger;
import buildcraft.api.recipes.AssemblyRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;

public class module_Machines extends module
{
  private int EnergyStoreID = 0;
  private int EnergyDirectorID = 1;
  private int ExtractorID = 2;
  private int GrinderID = 3;
  private int MagmafierID = 4;
  private int PoweredFurnaceID = 5;
 
  public static Trigger energyStoreFullTrigger;
  
  public static Trigger energyStoreEmptyTrigger;
  
  public static Trigger energyStorePartFilledTrigger;
  
  public static Trigger BlastFurnaceFullTrigger;
  public static Trigger BlastFurnaceEmptyTrigger;
  public static Trigger BlastFurnacePartFilledTrigger;
  
  public static Action directorDisableNorth;
  public static Action directorDisableSouth;
  public static Action directorDisableEast;
  public static Action directorDisableWest;
  public static Action directorEnableNorth;
  public static Action directorEnableSouth;
  public static Action directorEnableEast;
  public static Action directorEnableWest;
 
     
  public void preInit() {
	  
  }
  
  public void initialize()
  {
	aps.module_Machines.recipes.GrinderRecipes.loadRecipes();
	aps.module_Machines.recipes.GrinderRecipes.loadRecipesExtractor();
    aps.module_Machines.recipes.GrinderRecipes.loadRecipesMagmafier();
    
    
    energyStoreFullTrigger = new TriggerMachines(90, TriggerMachines.TriggerType.EnergyStoreFull);
    energyStoreEmptyTrigger = new TriggerMachines(91, TriggerMachines.TriggerType.EnergyStoreEmpty);
    energyStorePartFilledTrigger = new TriggerMachines(92, TriggerMachines.TriggerType.EnergyStorePartFilled);
    BlastFurnaceFullTrigger = new TriggerMachines(93, TriggerMachines.TriggerType.BlastFurnaceBlockFull);
    BlastFurnaceEmptyTrigger = new TriggerMachines(94, TriggerMachines.TriggerType.BlastFurnaceBlockEmpty);
    BlastFurnacePartFilledTrigger = new TriggerMachines(95, TriggerMachines.TriggerType.BlastFurnaceBlockPartFilled);
    
    directorDisableNorth = new ActionDirector(110, ActionDirector.Mode.disableNorth);
    directorDisableSouth = new ActionDirector(111, ActionDirector.Mode.disableSouth);
    directorDisableEast = new ActionDirector(112, ActionDirector.Mode.disableEast);
    directorDisableWest = new ActionDirector(113, ActionDirector.Mode.disableWest);
    directorEnableNorth = new ActionDirector(114, ActionDirector.Mode.enableNorth);
    directorEnableSouth = new ActionDirector(115, ActionDirector.Mode.enableSouth);
    directorEnableEast = new ActionDirector(116, ActionDirector.Mode.enableEast);
    directorEnableWest = new ActionDirector(117, ActionDirector.Mode.enableWest);
    

    ActionManager.registerTriggerProvider(new APSMachineTriggerProvider());
    ActionManager.registerActionProvider(new APSMachineActionProvider());
    

    ModLoader.addRecipe(new ItemStack(module_Core.APSKitItem, 1, this.EnergyStoreID + this.ItemMachine), new Object[] { "owo", "ICI", "oco", Character.valueOf('o'), Block.obsidian, Character.valueOf('w'), BuildCraftTransport.pipePowerWood, Character.valueOf('C'), new ItemStack(module_Core.APSPowerCoreItem, 1, 1), Character.valueOf('c'), BuildCraftTransport.pipePowerGold, Character.valueOf('I'), Item.ingotIron });

    module_Core.AddMetaSubblock(this.EnergyStoreID, TileEntityEnergyStore.class, "energystore", "Energy Store", module_Core.APSBlockTypes.Machine, true, false);
 
    ModLoader.addRecipe(new ItemStack(module_Core.APSKitItem, 1, this.EnergyDirectorID + this.ItemMachine), new Object[] { "owo", "wCw", "owo", Character.valueOf('o'), Block.obsidian, Character.valueOf('w'), BuildCraftTransport.pipePowerWood, Character.valueOf('C'), new ItemStack(module_Core.APSPowerCoreItem, 1, this.EnergyDirectorID) });
    
    module_Core.AddMetaSubblock(this.EnergyDirectorID, TileEntityEnergyDirector.class, "energydirector", "Energy Director", module_Core.APSBlockTypes.Machine, true, false);
    





    ModLoader.addRecipe(new ItemStack(module_Core.APSKitItem, 1, this.ExtractorID + this.ItemMachine), new Object[] { "oEo", "BGB", "oCo", Character.valueOf('o'), Block.obsidian, Character.valueOf('E'), new ItemStack(BuildCraftEnergy.engineBlock, 1, 2), Character.valueOf('B'), Item.bucketEmpty, Character.valueOf('G'), BuildCraftCore.goldGearItem, Character.valueOf('C'), new ItemStack(module_Core.APSPowerCoreItem, 1, 2) });
    






    module_Core.AddMetaSubblock(this.ExtractorID, TileEntityExtractor.class, "extractor", "Mineral Extractor", module_Core.APSBlockTypes.Machine);
    





    ModLoader.addRecipe(new ItemStack(module_Core.APSKitItem, 1, this.GrinderID + this.ItemMachine), new Object[] { "oSo", "G W", "oCo", Character.valueOf('o'), Block.obsidian, Character.valueOf('S'), BuildCraftTransport.pipeItemsStone, Character.valueOf('G'), BuildCraftCore.ironGearItem, Character.valueOf('W'), BuildCraftTransport.pipeItemsWood, Character.valueOf('C'), new ItemStack(module_Core.APSPowerCoreItem, 1, 1) });
    







    module_Core.AddMetaSubblock(this.GrinderID, TileEntityGrinder.class, "blockgrinder", "Block Grinder", module_Core.APSBlockTypes.Machine);
    



    





    ModLoader.addRecipe(new ItemStack(module_Core.APSKitItem, 1, this.MagmafierID + this.ItemMachine), new Object[] { "oSo", "CFL", "owo", Character.valueOf('o'), Block.obsidian, Character.valueOf('S'), BuildCraftTransport.pipeItemsStone, Character.valueOf('C'), new ItemStack(module_Core.APSPowerCoreItem, 1, 2), Character.valueOf('F'), Block.stoneOvenIdle, Character.valueOf('L'), BuildCraftTransport.pipeLiquidsGold, Character.valueOf('w'), BuildCraftTransport.pipePowerGold });
    







    module_Core.AddMetaSubblock(this.MagmafierID, TileEntityMagmafier.class, "blastfurnace", "Blast Furnace", module_Core.APSBlockTypes.Machine);
    
    





    ModLoader.addRecipe(new ItemStack(module_Core.APSKitItem, 1, this.PoweredFurnaceID + this.ItemMachine), new Object[] { "oIo", "iFW", "owo", Character.valueOf('o'), Block.obsidian, Character.valueOf('w'), BuildCraftTransport.pipePowerWood, Character.valueOf('i'), BuildCraftCore.ironGearItem, Character.valueOf('F'), Block.stoneOvenIdle, Character.valueOf('W'), BuildCraftTransport.pipeItemsWood, Character.valueOf('I'), Item.ingotIron });
    








    module_Core.AddMetaSubblock(this.PoweredFurnaceID, TileEntityPoweredFurnace.class, "poweredfurnace", "Powered Furnace", module_Core.APSBlockTypes.Machine);
     
    
    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(module_Core.APSKitItem, 1, this.EnergyStoreID + this.ItemMachine) }, 7500, new ItemStack(module_Core.APSMetaBlockMachine, 1, this.EnergyStoreID)));
    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(module_Core.APSKitItem, 1, this.EnergyDirectorID + this.ItemMachine) }, 10000, new ItemStack(module_Core.APSMetaBlockMachine, 1, this.EnergyDirectorID)));
    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(module_Core.APSKitItem, 1, this.ExtractorID + this.ItemMachine) }, 5000, new ItemStack(module_Core.APSMetaBlockMachine, 1, this.ExtractorID)));
    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(module_Core.APSKitItem, 1, this.GrinderID + this.ItemMachine) }, 5000, new ItemStack(module_Core.APSMetaBlockMachine, 1, this.GrinderID)));
    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(module_Core.APSKitItem, 1, this.PoweredFurnaceID + this.ItemMachine) }, 2500, new ItemStack(module_Core.APSMetaBlockMachine, 1, this.PoweredFurnaceID)));
    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(module_Core.APSKitItem, 1, this.MagmafierID + this.ItemMachine) }, 5000, new ItemStack(module_Core.APSMetaBlockMachine, 1, this.MagmafierID)));
  }
  

 

  public void clientInit() {}
  


  public void postInit() {}
 
}
