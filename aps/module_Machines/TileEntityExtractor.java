package aps.module_Machines;

import aps.module_Core.TileEntityAPSPowered;
import aps.module_Core.module_Core;
import aps.module_Machines.recipes.GrinderRecipes;
import buildcraft.BuildCraftCore;
import buildcraft.api.gates.Action;
import buildcraft.api.gates.IAction;
import buildcraft.api.gates.IActionReceptor;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.core.IMachine;
import buildcraft.core.network.PacketPayload;
import buildcraft.core.network.PacketUpdate;
import buildcraft.core.triggers.ActionMachineControl;
import buildcraft.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public class TileEntityExtractor
  extends TileEntityAPSPowered
  implements IPowerReceptor, ITankContainer, IActionReceptor, IMachine, IPipeConnection
{
  static int LavaID = Block.lavaStill.blockID;
  static int LavaCapacity = 20000;
  static int MinimumEnergy = 5;
  static int OptimumEnergy = 250;
  static int LiquidSideTex = 3;
  
  float PowerLevelMA;
  int PowerLevelMANodes = 100;
  
  float CurPowerLevel;
  
  float LastPowerLevel;
  String[] products;
  LiquidTank tank = new LiquidTank(LavaCapacity);
  
  public TileEntityExtractor()
  {
    super(0, 37, 1, 2, 0, 1, OptimumEnergy, 1, OptimumEnergy, module_Core.APSBlockTypes.Machine);
    this.hasGUI = true;
    this.GuiID = 123;
    this.PowerLevelMA = 0.0F;
  }
  

  int LavaCounter = 0;
  
  public void updateEntity()
  {
    if (this.worldObj.isRemote) { return;
    }
    super.updateEntity();
    
    
    if ((this.lastMode == ActionMachineControl.Mode.On) || (this.lastMode == ActionMachineControl.Mode.Loop) || (this.lastMode == ActionMachineControl.Mode.Unknown))
    {
      this.PowerLevelMA -= this.PowerLevelMA / this.PowerLevelMANodes;
      this.PowerLevelMA += this.powerProvider.useEnergy(1.0F, OptimumEnergy, true);
      this.CurPowerLevel = (this.PowerLevelMA / this.PowerLevelMANodes);
      this.LastPowerLevel = this.CurPowerLevel;
      
      while ((this.tank.getLiquid() != null) && (this.tank.getLiquid().amount > 0) && (this.CurPowerLevel > MinimumEnergy))
      {
        this.tank.drain(6, true);
        this.CurPowerLevel -= MinimumEnergy;
        
        ItemStack itemstack = GrinderRecipes.ExtractorProducts.getRandomProduct();
        if (itemstack != null)
        {


          ItemStack added = Utils.addToRandomInventory(itemstack, this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.UNKNOWN);
          itemstack.stackSize -= added.stackSize;
          
          if (itemstack.stackSize > 0) {
            Utils.addToRandomPipeEntry(this, ForgeDirection.UNKNOWN, itemstack);
          }
          if (itemstack.stackSize > 0)
          {
            EntityItem entityitem = new EntityItem(this.worldObj, this.xCoord + 0.5D, this.yCoord + 0.7D, this.zCoord + 0.5D, itemstack.copy());
            this.worldObj.spawnEntityInWorld(entityitem);
          }
        }
      }
    }
    

    if (this.lastMode == ActionMachineControl.Mode.On) this.lastMode = ActionMachineControl.Mode.Off;
  }
  
  public String[] getPossibleProducts()
  {
    if (this.worldObj.isRemote)
    {
      if (GrinderRecipes.ExtractorProducts == null) return new String[0];
      ItemStack[] P = GrinderRecipes.ExtractorProducts.getAllProducts();
      String[] Output = new String[P.length];
      for (int i = 0; i < P.length; i++)
        Output[i] = (P[i].stackSize + " " + P[i].getItem().getItemDisplayName(P[i]) + " " + GrinderRecipes.ExtractorProducts.chances[i] * 160.0F + "%");
      return Output;
    }
    
    return this.products;
  }
  
  public int powerRequest()
  {
    if ((this.tank.getLiquid() != null) && (this.tank.getLiquid().amount > 0) && (this.lastMode != ActionMachineControl.Mode.Off)) {
      return OptimumEnergy;
    }
    return 0;
  }
  

  public void readFromNBT(NBTTagCompound nbttagcompound)
  {
    super.readFromNBT(nbttagcompound);
    
    this.PowerLevelMA = nbttagcompound.getFloat("powerlevelma");
    fill(0, new LiquidStack(LavaID, nbttagcompound.getInteger("lavalevel")), true);
  }
  

  public void writeToNBT(NBTTagCompound nbttagcompound)
  {
    super.writeToNBT(nbttagcompound);
    
    nbttagcompound.setFloat("powerlevelma", this.PowerLevelMA);
    nbttagcompound.setInteger("lavalevel", this.tank.getLiquid() == null ? 0 : this.tank.getLiquid().amount);
  }
  
  public int getLiquidId() {
    return LavaID;
  }
  





  public float getScaledLiquidQuantity() {
	  
	  return (this.tank.getLiquid() == null ? 0 : this.tank.getLiquid().amount)/339.0F;

  }
  
  public float getScaledPowerLevel(int MaxLevel) { return this.LastPowerLevel / OptimumEnergy * MaxLevel; }
  
  public boolean getPowerSufficient() { return this.LastPowerLevel >= MinimumEnergy; }
  


  public void kill() {}
  


  public boolean isActive()
  {
    return this.tank.getLiquid() != null;
  }
  
  public boolean manageSolids() {
    return true;
  }
  

  ActionMachineControl.Mode lastMode = ActionMachineControl.Mode.Unknown;
  
  public void actionActivated(Action action) {
    if (action == BuildCraftCore.actionOn) {
      this.lastMode = ActionMachineControl.Mode.On;
    } else if (action == BuildCraftCore.actionOff) {
      this.lastMode = ActionMachineControl.Mode.Off;
    } else if (action == BuildCraftCore.actionLoop) {
      this.lastMode = ActionMachineControl.Mode.Loop;
    }
  }
  


  public void actionActivated(IAction action) {}
  

  public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
  {
    return fill(0, resource, doFill);
  }
  
  public int fill(int tankIndex, LiquidStack resource, boolean doFill)
  {
    if (resource.itemID == LavaID)
      return this.tank.fill(resource, doFill);
    return 0;
  }
  


  public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
  {
    return null;
  }
  
  public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain)
  {
    return null;
  }
  
  public ILiquidTank[] getTanks(ForgeDirection direction)
  {
    return new ILiquidTank[] { this.tank };
  }
  

  public ILiquidTank getTank(ForgeDirection direction, LiquidStack liquidstack)
  {
    return this.tank;
  }
  
  public void sendGUIUpdates(Container container, ICrafting iCrafting)
  {
    iCrafting.sendProgressBarUpdate(container, 0, this.tank.getLiquid() == null ? 0 : this.tank.getLiquid().amount);
    iCrafting.sendProgressBarUpdate(container, 1, (int)this.LastPowerLevel);
  }
  
  public void getGUIUpdates(int channel, int i)
  {
    if (channel == 0) this.tank = new LiquidTank(LavaID, i, LavaCapacity);
    if (channel == 1) { this.LastPowerLevel = i;
    }
  }
  
  public PacketPayload getPacketPayload()
  {
    if (GrinderRecipes.ExtractorProducts == null) return super.getPacketPayload();
    ItemStack[] P = GrinderRecipes.ExtractorProducts.getAllProducts();
    
    PacketPayload payload = new PacketPayload(0, P.length, 0);
    for (int i = 0; i < P.length; i++)
    {
      String Output = P[i].stackSize + " " + P[i].getItem().getItemDisplayName(P[i]) + " " + GrinderRecipes.ExtractorProducts.chances[i] * 160.0F + "%";
      payload.stringPayload[i] = Output;
    }
    return payload;
  }
  
  public void handleUpdatePacket(PacketUpdate packet)
  {
    String[] output = packet.payload.stringPayload;
  }
  
  public boolean isPipeConnected(ForgeDirection with)
  {
    return true;
  }
}
