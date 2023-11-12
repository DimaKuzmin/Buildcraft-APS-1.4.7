package aps.module_Machines;

import aps.BuildcraftAPS;
import aps.module_Core.InventoryAPS;
import aps.module_Core.TileEntityAPSPowered;
import aps.module_Core.module_Core;
import aps.module_Machines.recipes.GrinderRecipes;
import buildcraft.BuildCraftCore;
import buildcraft.api.gates.Action;
import buildcraft.api.gates.IAction;
import buildcraft.api.gates.IActionReceptor;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.core.IMachine;
import buildcraft.core.triggers.ActionMachineControl;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;




public class TileEntityMagmafier extends TileEntityAPSPowered implements IPowerReceptor, IInventory, ITankContainer, IActionReceptor, IMachine
{
  static int LavaID = Block.lavaStill.blockID;
  static int LavaCapacity = 10000;
  static int BlockToLavaRatio = 16;
  static int BlockMeltEnergy = 1250;
  static int BlockCapacity = 10 * BlockToLavaRatio;
  static int LiquidSideTex = 3;
  
  InventoryAPS Inventory;
  float PowerLevel;
  float BlockLevel;
  ILiquidTank tank = new LiquidTank(LavaCapacity);
  
  ActionMachineControl.Mode lastMode = ActionMachineControl.Mode.Unknown;
  
  public TileEntityMagmafier()
  {
    super(0, 36, 1, 2, 0, 1, BlockMeltEnergy, 1, BlockMeltEnergy, module_Core.APSBlockTypes.Machine);
    this.hasGUI = true;
    this.GuiID = 121;
    this.Inventory = new InventoryAPS(1, "magmafierinput");
    this.PowerLevel = 0.0F;
    this.BlockLevel = 0;
  }
  
  int LavaCounter = 0;
  
  public void updateEntity()
  {
    super.updateEntity();
    
    ItemStack itemInInventory = this.Inventory.getStackInSlot(0);
    
    if ((itemInInventory != null) && (this.BlockLevel < BlockCapacity) && (GrinderRecipes.isMeltable(itemInInventory)))
    {
      if (BlockCapacity - this.BlockLevel < itemInInventory.stackSize)
      {
        float i = BlockCapacity - this.BlockLevel;
        this.BlockLevel = BlockCapacity;
        this.Inventory.decrStackSize(0, (int) i);
      }
      else
      {
        this.BlockLevel += itemInInventory.stackSize;
        this.Inventory.setInventorySlotContents(0, null);
      }
    }
    
    if ((this.lastMode == ActionMachineControl.Mode.On) || (this.lastMode == ActionMachineControl.Mode.Loop) || (this.lastMode == ActionMachineControl.Mode.Unknown))
    {
      this.PowerLevel += this.powerProvider.useEnergy(1.0F, BlockMeltEnergy - this.PowerLevel, true);
      
      if ((this.BlockLevel > 0) && (this.PowerLevel >= BlockMeltEnergy))
      {

        this.tank.fill(new LiquidStack(LavaID, LavaCapacity / 10 / BlockToLavaRatio), true);
        this.BlockLevel -= 1;
        this.PowerLevel -= BlockMeltEnergy;
      }
    }
    if (this.lastMode == ActionMachineControl.Mode.On) this.lastMode = ActionMachineControl.Mode.Off;
  }
  
  public int powerRequest()
  {
    if (this.BlockLevel > 0)
    {

      if (this.tank.getLiquid() == null)
        return BlockMeltEnergy;
      if (this.tank.getLiquid().amount < LavaCapacity)
        return BlockMeltEnergy;
    }
    return 0;
  }
  
  public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
    return fill(0, resource, doFill);
  }
  
  public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
    return this.tank.fill(resource, doFill);
  }
  
  public ILiquidTank[] getTanks(ForgeDirection direction) {
    return new ILiquidTank[] { this.tank };
  }
  
  public ILiquidTank getTank(ForgeDirection direction, LiquidStack liquidStack) {
    return this.tank;
  }
  
  public LiquidStack drain(ForgeDirection from, int quantityMax, boolean doDrain)
  {
    return drain(0, quantityMax, doDrain);
  }
  

  public LiquidStack drain(int tankIndex, int maxEmpty, boolean doDrain)
  {
    return drain(maxEmpty, doDrain);
  }
  
  public LiquidStack drain(int maxDrain, boolean doDrain)
  {
    return this.tank.drain(maxDrain, doDrain);
  }
  

  public void readFromNBT(NBTTagCompound nbttagcompound)
  {
    super.readFromNBT(nbttagcompound);
    
    this.PowerLevel = nbttagcompound.getFloat("powerlevel");
    int lavaLevel = nbttagcompound.getInteger("lavalevel");
    if (lavaLevel >= 0)
    this.tank.fill(new LiquidStack(LavaID, lavaLevel), true);
    
    this.BlockLevel = nbttagcompound.getFloat("blocklevel");
    
    if (nbttagcompound.hasKey("inputslotitem")) {
      NBTTagCompound cpt = nbttagcompound.getCompoundTag("inputslotitem");
      this.Inventory.addItemStackToInventory(ItemStack.loadItemStackFromNBT(cpt));
    }
  }
  
  public void writeToNBT(NBTTagCompound nbttagcompound)
  {
    super.writeToNBT(nbttagcompound);
    
    nbttagcompound.setFloat("powerlevel", this.PowerLevel);
    nbttagcompound.setInteger("lavalevel", this.tank.getLiquid() == null ? 0 : this.tank.getLiquid().amount);
    nbttagcompound.setFloat("blocklevel", this.BlockLevel);
    
    if (this.Inventory.getStackInSlot(0) != null) {
      NBTTagCompound cpt = new NBTTagCompound();
      this.Inventory.getStackInSlot(0).writeToNBT(cpt);
      nbttagcompound.setTag("inputslotitem", cpt);
    }
  }
  
  public IInventory getInventory() { return this.Inventory; }
  
  public float getScaledLiquidQuantity() { 
  
	  return (this.tank.getLiquid() == null ? 0 : this.tank.getLiquid().amount)/172.4137931034483F;
	  
  }
  
  public float getScaledBlockQuantity(int MaxLevel) { return ( BlockLevel / BlockCapacity) * MaxLevel; }
  
  public float getScaledPowerLevel(int MaxLevel) { return (PowerLevel / BlockMeltEnergy) * MaxLevel; }
  
  public void kill() {}
  
  public boolean isActive()
  {
    return this.BlockLevel > 0;
  }
  
  public boolean manageLiquids() {
    return true;
  }
  
  public boolean manageSolids() { return true; }
  
  public boolean allowActions() {
    return true;
  }
  
  public void actionActivated(Action action) {
    if (action == BuildCraftCore.actionOn) {
      this.lastMode = ActionMachineControl.Mode.On;
    } else if (action == BuildCraftCore.actionOff) {
      this.lastMode = ActionMachineControl.Mode.Off;
    } else if (action == BuildCraftCore.actionLoop) {
      this.lastMode = ActionMachineControl.Mode.Loop;
    }
  }
  
  public int getSizeInventory()
  {
    return this.Inventory.getSizeInventory();
  }
  
  public ItemStack getStackInSlot(int i)
  {
    return this.Inventory.getStackInSlot(i);
  }
  
  public ItemStack decrStackSize(int i, int j)
  {
    return this.Inventory.decrStackSize(i, j);
  }
  
  public void setInventorySlotContents(int i, ItemStack itemstack)
  {
    this.Inventory.setInventorySlotContents(i, itemstack);
  }
  
  public String getInvName()
  {
    return this.Inventory.getInvName();
  }
  
  public int getInventoryStackLimit()
  {
    return this.Inventory.getInventoryStackLimit();
  }
  
  public boolean isUseableByPlayer(EntityPlayer entityplayer)
  {
    return this.Inventory.isUseableByPlayer(entityplayer);
  }
  
  public void openChest()
  {
    this.Inventory.openChest();
  }
  
  public void closeChest()
  {
    this.Inventory.closeChest();
  }
  
  public ItemStack getStackInSlotOnClosing(int var1)
  {
    return null;
  }
  
  public void actionActivated(IAction action) {}
  
}
