package aps.module_Solar;

import aps.BuildcraftAPS;
import aps.module;
import aps.module_Core.module_Core;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftTransport;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.recipes.AssemblyRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;

public class module_Solar extends module
{
  private int ReflectorID = 3;
  private int CollectorID = 4;
  

  public static APSSolarManagerController controller;
  

  public static ITrigger solarPowerOutputTrigger;
  
  public static boolean ShowBeams;
  

  public void initialize()
  {
    controller = new APSSolarManagerController();
    
    solarPowerOutputTrigger = new TriggerSolar(103, TriggerSolar.TriggerType.SolarPowerOutput);
    

    ModLoader.addRecipe(new ItemStack(module_Core.APSKitItem, 1, this.ReflectorID + this.ItemEnergy), new Object[] { "GGG", "iIi", "oCo", Character.valueOf('G'), Block.glass, Character.valueOf('I'), Item.ingotIron, Character.valueOf('o'), Block.obsidian, Character.valueOf('i'), Item.ingotIron, Character.valueOf('C'), new ItemStack(module_Core.APSPowerCoreItem, 1, 1) });
    









    module_Core.AddMetaSubblock(this.ReflectorID, TileEntitySolarReflector.class, "solarreflector", "Solar Reflector", module_Core.APSBlockTypes.Energy, new APSSolarItemAction());
    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(module_Core.APSKitItem, 1, this.ReflectorID + this.ItemEnergy) }, 2500, new ItemStack(module_Core.APSMetaBlockEnergy, 1, this.ReflectorID)));
    





    ModLoader.addRecipe(new ItemStack(module_Core.APSKitItem, 1, this.CollectorID + this.ItemEnergy), new Object[] { "oCo", "IBI", "owo", Character.valueOf('o'), Block.obsidian, Character.valueOf('C'), new ItemStack(module_Core.APSPowerCoreItem, 1, 2), Character.valueOf('w'), BuildCraftTransport.pipePowerWood, Character.valueOf('B'), Item.bucketWater, Character.valueOf('I'), BuildCraftCore.ironGearItem });
    









    module_Core.AddMetaSubblock(this.CollectorID, TileEntitySolarCollector.class, "solarcollector", "Solar Collector", module_Core.APSBlockTypes.Energy);
    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(module_Core.APSKitItem, 1, this.CollectorID + this.ItemEnergy) }, 7500, new ItemStack(module_Core.APSMetaBlockEnergy, 1, this.CollectorID)));
  }
  




  public void preInit()
  {
    ShowBeams = Boolean.parseBoolean(BuildcraftAPS.Conf.get("general", "ShowSolarBeams", true).value);
  }
  
  public void clientInit() {}
  
  public void postInit() {}
}
