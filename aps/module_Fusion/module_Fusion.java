package aps.module_Fusion;

import org.lwjgl.opengl.GL11;

import aps.BuildcraftAPS;
import aps.module;
import aps.module_Core.module_Core;
import aps.module_Fusion.proxy.APSFusionProxy;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftEnergy;
import buildcraft.BuildCraftFactory;
import buildcraft.BuildCraftTransport;
import buildcraft.api.gates.ActionManager;
import buildcraft.api.gates.Trigger;
import buildcraft.api.recipes.AssemblyRecipe;
import buildcraft.api.recipes.RefineryRecipe;
import buildcraft.core.proxy.CoreProxy;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.ItemPipe;
import buildcraft.transport.TransportProxyClient;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
  
public class module_Fusion
  extends module
{
  private int TokamakID = 0;
  
  int ItemIdTokamakPlating;
  
  int ItemIdTokamakChamber;
  
  int ItemIdTokamakControl;
  
  int ItemIdTokamakExtractor;
  int ItemIdTokamakInput;
  int ItemIdTokamakOutput;
  int ItemIdPipeLapis;
  int ItemIdHeavyWaterBucket;
  int ItemIdHeavyWater;
  int ItemIDPipe;
 
  public static int TextureIdPipePowerLapis;
  
  public static Item tokamakPlatingItem = null;
  public static Item tokamakChamberItem = null;
  public static Item tokamakControlItem = null;
  public static Item tokamakExtractorItem = null;
  public static Item tokamakInputItem = null;
  public static Item tokamakOutputItem = null;
  
  public static Item heavyWaterBucketItem = null;
  public static Item heavyWater = null;
  
  public static ItemPipe advancedPowerOutput = null;
   
  public static Trigger tokamakIdlingTrigger;
  
  public static Trigger tokamakTempReachedTrigger;
  
  public static Trigger tokamakOutputQuaterTrigger;
  
  public static Trigger tokamakOutputHalfTrigger;
  
  public static Trigger tokamakOutput3QuatersTrigger;
  
  public static Trigger tokamakOutputMaxTrigger;
  
  public static Trigger pipeCloggedTrigger;
  
  public static Trigger pipeFreeTrigger;
  public static int MaxTemp;
  public static int CoolRate;
  public static float FusionFraction;
   
  public void preInit()
  {
    MaxTemp = Integer.parseInt(BuildcraftAPS.Conf.get("Fusion", "MaxTemperature", 10000000).value);
    CoolRate = Integer.parseInt(BuildcraftAPS.Conf.get("Fusion", "CoolRate", 1000).value);
    FusionFraction = Integer.parseInt(BuildcraftAPS.Conf.get("Fusion", "TokamakFusionTemperaturePercentage", 80).value) / 100.0F;
    
    this.ItemIdTokamakPlating = Integer.parseInt(BuildcraftAPS.Conf.get("item", "TokamakPlating.ID", BuildcraftAPS.defItemStartID + 2).value);
    this.ItemIdTokamakChamber = Integer.parseInt(BuildcraftAPS.Conf.get("item", "TokamakChamber.ID", BuildcraftAPS.defItemStartID + 2 + 1).value);
    this.ItemIdTokamakControl = Integer.parseInt(BuildcraftAPS.Conf.get("item", "TokamakControl.ID", BuildcraftAPS.defItemStartID + 2 + 2).value);
    this.ItemIdTokamakExtractor = Integer.parseInt(BuildcraftAPS.Conf.get("item", "TokamakExtractor.ID", BuildcraftAPS.defItemStartID + 2 + 3).value);
    this.ItemIdTokamakInput = Integer.parseInt(BuildcraftAPS.Conf.get("item", "TokamakInput.ID", BuildcraftAPS.defItemStartID + 2 + 4).value);
    this.ItemIdTokamakOutput = Integer.parseInt(BuildcraftAPS.Conf.get("item", "TokamakOutput.ID", BuildcraftAPS.defItemStartID + 2 + 5).value);
    this.ItemIDPipe = Integer.parseInt(BuildcraftAPS.Conf.get("item", "AdvancedOutputPipeGrade1.ID", BuildcraftAPS.defItemStartID + 2 + 6).value);
    this.ItemIdHeavyWaterBucket = Integer.parseInt(BuildcraftAPS.Conf.get("item", "HeavyWaterBucket.ID", BuildcraftAPS.defItemStartID + 2 + 7).value);
    this.ItemIdHeavyWater = Integer.parseInt(BuildcraftAPS.Conf.get("item", "HeavyWater.ID", BuildcraftAPS.defItemStartID + 2 + 8).value);
     
    
  }
  

  public void initialize()
  { 
	  
	advancedPowerOutput = createPipe(this.ItemIDPipe, APSPipePower.class, "Advanced Output Pipe");
    tokamakPlatingItem = new ItemTokamakPlating(this.ItemIdTokamakPlating).setItemName("tokamakplatingitem");
    tokamakChamberItem = new ItemTokamakChamber(this.ItemIdTokamakChamber).setItemName("tokamakchamberitem");
    tokamakControlItem = new ItemTokamakControl(this.ItemIdTokamakControl).setItemName("tokamakcontrolitem");
    tokamakExtractorItem = new ItemTokamakExtractor(this.ItemIdTokamakExtractor).setItemName("tokamakextractoritem");
    tokamakInputItem = new ItemTokamakInput(this.ItemIdTokamakInput).setItemName("tokamakinputitem");
    tokamakOutputItem = new ItemTokamakOutput(this.ItemIdTokamakOutput).setItemName("tokamakoutputitem");
    
    heavyWaterBucketItem = new ItemHeavyWaterBucket(this.ItemIdHeavyWaterBucket).setItemName("heavywaterbucket").setContainerItem(Item.bucketEmpty);
    heavyWater = new ItemHeavyWater(this.ItemIdHeavyWater).setItemName("heavywater");
    
     
    tokamakIdlingTrigger = new TriggerTokamak(99, TriggerTokamak.TriggerType.TokamakIdling);
    tokamakTempReachedTrigger = new TriggerTokamak(100, TriggerTokamak.TriggerType.TokamakTempReached);
    tokamakOutputQuaterTrigger = new TriggerTokamak(101, TriggerTokamak.TriggerType.TokamakOutputQuater);
    tokamakOutputHalfTrigger = new TriggerTokamak(102, TriggerTokamak.TriggerType.TokamakOutputHalf);
    tokamakOutput3QuatersTrigger = new TriggerTokamak(103, TriggerTokamak.TriggerType.TokamakOutput3Quaters);
    tokamakOutputMaxTrigger = new TriggerTokamak(104, TriggerTokamak.TriggerType.TokamakOutputMax);
    
    pipeCloggedTrigger = new TriggerAdvancedPipe(105, TriggerAdvancedPipe.TriggerType.pipeClogged);
    pipeFreeTrigger = new TriggerAdvancedPipe(106, TriggerAdvancedPipe.TriggerType.pipeFree);
    
    ActionManager.registerTriggerProvider(new APSFusionTriggerProvider());
     
    ModLoader.addName(tokamakPlatingItem, "Tokamak Plating");
    ModLoader.addRecipe(new ItemStack(tokamakPlatingItem), new Object[] { "DIO", "DIO", "DIO", Character.valueOf('D'), Item.diamond, Character.valueOf('I'), Item.ingotIron, Character.valueOf('O'), Block.obsidian });
 
    ModLoader.addName(tokamakChamberItem, "Tokamak Chamber");
    ModLoader.addRecipe(new ItemStack(tokamakChamberItem), new Object[] { "IPI", "P P", "IPI", Character.valueOf('I'), Item.ingotIron, Character.valueOf('P'), tokamakPlatingItem });
 
    ModLoader.addName(tokamakControlItem, "Control Module");
    ModLoader.addRecipe(new ItemStack(tokamakControlItem), new Object[] { "IrI", "rCr", "IrI", Character.valueOf('I'), Item.ingotIron, Character.valueOf('C'), new ItemStack(module_Core.APSPowerCoreItem, 1, 3), Character.valueOf('r'), Item.redstoneRepeater });
 
    ModLoader.addName(tokamakExtractorItem, "Deuterium Extractor");
    ModLoader.addRecipe(new ItemStack(tokamakExtractorItem), new Object[] { "ClC", "ere", "clc", Character.valueOf('C'), new ItemStack(module_Core.APSPowerCoreItem, 1, 1), Character.valueOf('l'), BuildCraftTransport.pipeLiquidsGold, Character.valueOf('e'), new ItemStack(BuildCraftEnergy.engineBlock, 1, 2), Character.valueOf('r'), BuildCraftFactory.refineryBlock, Character.valueOf('c'), BuildCraftTransport.pipePowerGold });
 
    ModLoader.addName(tokamakInputItem, "Input Module");
    ModLoader.addRecipe(new ItemStack(tokamakInputItem), new Object[] { "DcD", "Rcl", "Rcl", Character.valueOf('D'), Item.diamond, Character.valueOf('c'), BuildCraftTransport.pipePowerGold, Character.valueOf('R'), Item.redstone, Character.valueOf('l'), BuildCraftTransport.pipeLiquidsGold });
 
    ModLoader.addName(tokamakOutputItem, "Output Module");
    ModLoader.addRecipe(new ItemStack(tokamakOutputItem), new Object[] { "Rcd", "Rcd", "DwD", Character.valueOf('R'), Item.redstone, Character.valueOf('c'), BuildCraftTransport.pipePowerGold, Character.valueOf('d'), BuildCraftCore.diamondGearItem, Character.valueOf('w'), BuildCraftTransport.pipePowerWood, Character.valueOf('D'), Item.diamond });
 
    ModLoader.addName(heavyWaterBucketItem, "Heavy Water Bucket");
     
    ModLoader.addName(heavyWater, "Heavy Water");
    LiquidDictionary.getOrCreateLiquid("Heavy Water", new LiquidStack(heavyWater.itemID, 0));
    
    RefineryRecipe.registerRefineryRecipe(new RefineryRecipe(new LiquidStack(Block.waterStill, 2), new LiquidStack(Block.waterStill, 2), new LiquidStack(heavyWater.itemID, 4), 5, 1));
    
    APSFusionProxy.proxy.registerTextureFX();
    
    TextureIdPipePowerLapis = CoreProxy.proxy.addCustomTexture(BuildcraftAPS.imageFilesRoot + "PipePowerLapis.png");
     
    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(module_Core.APSPowerCoreItem, 1, 2), new ItemStack(BuildCraftTransport.pipePowerGold, 1) }, 1000, new ItemStack(advancedPowerOutput)));
    
    ModLoader.addName(new ItemStack(module_Core.APSKitItem, 1, this.TokamakID + this.ItemEnergy), "Fusion Generator Kit");
    ModLoader.addRecipe(new ItemStack(module_Core.APSKitItem, 1, this.TokamakID + this.ItemEnergy), new Object[] { "oOo", "CTD", "oIo", Character.valueOf('o'), Block.obsidian, Character.valueOf('O'), tokamakOutputItem, Character.valueOf('C'), tokamakControlItem, Character.valueOf('T'), tokamakChamberItem, Character.valueOf('D'), tokamakExtractorItem, Character.valueOf('I'), tokamakInputItem });

    module_Core.AddMetaSubblock(this.TokamakID, TileEntityTokamakGenerator.class, "tokamakgenerator", "Fusion Generator", module_Core.APSBlockTypes.Energy, true, false);
    AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(new ItemStack[] { new ItemStack(module_Core.APSKitItem, 1, this.TokamakID + this.ItemEnergy) }, 15000, new ItemStack(module_Core.APSMetaBlockEnergy, 1, this.TokamakID)));
   }

  public void clientInit()
  {
    MinecraftForgeClient.registerItemRenderer(advancedPowerOutput.itemID, TransportProxyClient.pipeItemRenderer);
    MinecraftForgeClient.preloadTexture(BuildcraftAPS.imageFilesRoot + "PipePowerLapis.png");
    

   }
  
  private static ItemPipe createPipe(int defaultID, Class clas, String descr)
  {
    String name = clas.getSimpleName();
    
    ItemPipe res = BlockGenericPipe.registerPipe(defaultID, clas);
    res.setItemName(name);
    res.setCreativeTab(module_Core.APSTab);

    CoreProxy.proxy.addName(res, descr);
    
    return res;
  }
  
  public void renderInventory(RenderBlocks renderblocks, int itemID, int meta) {
    Tessellator tessellator = Tessellator.instance;
    
    Block block = BuildCraftTransport.genericPipeBlock;
    int textureID = ((ItemPipe)Item.itemsList[itemID]).getTextureIndex();
    
    block.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
    
    block.setBlockBoundsForItemRender();
    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
    tessellator.startDrawingQuads();
    tessellator.setNormal(0.0F, -1.0F, 0.0F);
    renderblocks.renderBottomFace(block, 0.0D, 0.0D, 0.0D, textureID);
    tessellator.draw();
    tessellator.startDrawingQuads();
    tessellator.setNormal(0.0F, 1.0F, 0.0F);
    renderblocks.renderTopFace(block, 0.0D, 0.0D, 0.0D, textureID);
    tessellator.draw();
    tessellator.startDrawingQuads();
    tessellator.setNormal(0.0F, 0.0F, -1.0F);
    renderblocks.renderEastFace(block, 0.0D, 0.0D, 0.0D, textureID);
    tessellator.draw();
    tessellator.startDrawingQuads();
    tessellator.setNormal(0.0F, 0.0F, 1.0F);
    renderblocks.renderWestFace(block, 0.0D, 0.0D, 0.0D, textureID);
    tessellator.draw();
    tessellator.startDrawingQuads();
    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
    renderblocks.renderNorthFace(block, 0.0D, 0.0D, 0.0D, textureID);
    tessellator.draw();
    tessellator.startDrawingQuads();
    tessellator.setNormal(1.0F, 0.0F, 0.0F);
    renderblocks.renderSouthFace(block, 0.0D, 0.0D, 0.0D, textureID);
    tessellator.draw();
    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  }
  
  public void postInit() {}
}
