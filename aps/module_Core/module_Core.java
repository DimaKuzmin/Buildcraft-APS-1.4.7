package aps.module_Core;

import aps.BuildcraftAPS;
import aps.module;
import aps.module_Core.proxy.GuiHandler;
import aps.module_Core.recipes.Core_Recipes;
import aps.tabs.CreativeTabsAps;
import buildcraft.core.utils.Localization;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.client.MinecraftForgeClient;


public class module_Core extends module
{ 
  public static CreativeTabsAps APSTab;
  //ID
  int insulatedBottleID;
  int ItemIdPowerCore;
  int ItemIdKit;
  int ItemIdControlPanel;
  int BlockIdMetaBlock;
  int energyCrystalID;
  //Items
  public static Item APSPowerCoreItem = null;
  public static Item APSKitItem = null;
  public static Item APSMetaBlockEnergyItem = null;
  public static Item APSMetaBlockMachineItem = null;
  public static Item APSControlPanelItem = null;

  
   public static Block APSMetaBlockEnergy = null;
  public static Block APSMetaBlockMachine = null;
  public static APSMetaSub[] APSMetaSubs = new APSMetaSub[32];
  
  public static enum APSBlockTypes { Energy,  Machine; }
  


  public void preInit()
  {
    this.ItemIdPowerCore = Integer.parseInt(BuildcraftAPS.Conf.get("item", "PowerCore.ID", BuildcraftAPS.defItemStartID + 0).value);
    this.ItemIdKit = Integer.parseInt(BuildcraftAPS.Conf.get("item", "KitItem.ID", BuildcraftAPS.defItemStartID + 1).value);
    this.insulatedBottleID = Integer.parseInt(BuildcraftAPS.Conf.get("item", "InsulatedBottle.ID", BuildcraftAPS.defItemStartID + 2 + 11).value);
    this.energyCrystalID = Integer.parseInt(BuildcraftAPS.Conf.get("item", "EnergyCrystal.ID", BuildcraftAPS.defItemStartID + 2 + 15).value);
    this.BlockIdMetaBlock = Integer.parseInt(BuildcraftAPS.Conf.get("block", "MetablockStart.ID", BuildcraftAPS.defBlockStartID).value);
  }
  



  public void initialize()
  {
	
    NetworkRegistry.instance().registerGuiHandler(BuildcraftAPS.instance, new GuiHandler());
        
    APSPowerCoreItem = new ItemAPSPowerCore(this.ItemIdPowerCore).setItemName("apspowercoreitem");
    APSTab = new CreativeTabsAps("APS");
    APSKitItem = new ItemAPSKit(this.ItemIdKit).setItemName("apskititem");
    APSPowerCoreItem.setCreativeTab(APSTab);

    APSMetaBlockEnergy = new BlockAPSMetaEnergy(this.BlockIdMetaBlock, Material.iron).setBlockName("apsmetablockenergy");
    APSMetaBlockMachine = new BlockAPSMetaMachine(this.BlockIdMetaBlock + 1, Material.iron).setBlockName("apsmetablockmachine");
    APSMetaBlockEnergyItem = new ItemBlockAPSMeta(APSMetaBlockEnergy.blockID - 256).setItemName("apsmetablockenergyitem");
    APSMetaBlockMachineItem = new ItemBlockAPSMeta(APSMetaBlockMachine.blockID - 256).setItemName("apsmetablockmachineitem");
    
    ModLoader.registerBlock(APSMetaBlockEnergy);
    ModLoader.registerBlock(APSMetaBlockMachine);
    
    Core_Recipes.initRecipesAssembly();
    Core_Recipes.initRecipesAssembyChipsets();
    Core_Recipes.initRecipesMinecraft();
    
   
    ModLoader.addName(new ItemStack(APSPowerCoreItem, 1, 0), "Redstone Power Core");
    ModLoader.addName(new ItemStack(APSPowerCoreItem, 1, 1), "Iron Power Core");
    ModLoader.addName(new ItemStack(APSPowerCoreItem, 1, 2), "Gold Power Core");
    ModLoader.addName(new ItemStack(APSPowerCoreItem, 1, 3), "Diamond Power Core");
    


    
    Item.itemsList[APSMetaBlockEnergy.blockID] = APSMetaBlockEnergyItem;
    Item.itemsList[APSMetaBlockMachine.blockID] = APSMetaBlockMachineItem;
  }
  

  public void clientInit()
  {
    Localization.addLocalization("/lang/aps/", "en_US");
    MinecraftForgeClient.preloadTexture(BuildcraftAPS.imageFilesRoot + "APSBlockTexes.png");
  }
  
  public void postInit() {}
  
  public static void AddMetaSubblock(int Index, Class Ent, String InternalName, String IngameName, APSBlockTypes type)
  {
    AddMetaSubblock(Index, Ent, InternalName, IngameName, type, null, false, false);
  }
  
  public static void AddMetaSubblock(int Index, Class Ent, String InternalName, String IngameName, APSBlockTypes type, boolean ProviderTriggers, boolean ProviderActions)
  {
    AddMetaSubblock(Index, Ent, InternalName, IngameName, type, null, ProviderTriggers, ProviderActions);
  }
  
  public static void AddMetaSubblock(int Index, Class Ent, String InternalName, String IngameName, APSBlockTypes type, APSKitItemAction action)
  {
    AddMetaSubblock(Index, Ent, InternalName, IngameName, type, action, false, false);
  }
  
  public static void AddMetaSubblock(int Index, Class Ent, String InternalName, String IngameName, APSBlockTypes type, APSKitItemAction action, boolean ProvidesTriggers, boolean ProvidesActions)
  {
    ModLoader.registerTileEntity(Ent, InternalName);
    
    int offset = 0;
    
    if (type == APSBlockTypes.Machine) {
      offset = 16;
    }
    
    APSMetaSubs[(Index + offset)] = new APSMetaSub(Index, Ent, InternalName, IngameName, type);
    
    if (action != null) {
      ItemAPSKit.registerKit(Index + offset, 32 + Index + offset, IngameName, action);
    } else {
      ItemAPSKit.registerKit(Index + offset, 32 + Index + offset, IngameName);
    }
  }
}
