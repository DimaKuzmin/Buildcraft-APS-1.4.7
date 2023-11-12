package aps.module_Machines;

import aps.BuildcraftAPS;
import aps.module_Core.TileEntityAPSPowered;
import aps.module_Core.module_Core;
import buildcraft.BuildCraftCore;
import buildcraft.api.gates.Action;
import buildcraft.api.gates.IAction;
import buildcraft.api.gates.IActionReceptor;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.core.IMachine;
import buildcraft.core.triggers.ActionMachineControl;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;






public class TileEntityPoweredFurnace extends TileEntityAPSPowered implements IPowerReceptor, IInventory, ISidedInventory, IActionReceptor, IMachine
{
  private ItemStack[] furnaceItemStacks;
  public static int maxStoredPower = 1000;
  public int storedPower = 0;
  public int smeltPower = 200;
  
  public TileEntityPoweredFurnace()
  {
    super(0, 34, 1, 2, 0, 5, maxStoredPower, 5, maxStoredPower, module_Core.APSBlockTypes.Machine);
    this.hasGUI = true;
    this.GuiID = 122;
    this.furnaceItemStacks = new ItemStack[2];
  }
  
  public int getSizeInventory()
  {
    return this.furnaceItemStacks.length;
  }
  
  public int getStartInventorySide(int side) {
    if (side == 1) return 0;
    return 1;
  }
  
  public int getSizeInventorySide(int side) {
    return 1;
  }
  
  public ItemStack getStackInSlot(int i)
  {
    return this.furnaceItemStacks[i];
  }
  
  public ItemStack decrStackSize(int i, int j)
  {
    if (this.furnaceItemStacks[i] != null)
    {
      if (this.furnaceItemStacks[i].stackSize <= j)
      {
        ItemStack itemstack = this.furnaceItemStacks[i];
        this.furnaceItemStacks[i] = null;
        return itemstack;
      }
      ItemStack itemstack1 = this.furnaceItemStacks[i].splitStack(j);
      if (this.furnaceItemStacks[i].stackSize == 0)
      {
        this.furnaceItemStacks[i] = null;
      }
      return itemstack1;
    }
    
    return null;
  }
  

  public void setInventorySlotContents(int i, ItemStack itemstack)
  {
    this.furnaceItemStacks[i] = itemstack;
    if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit()))
    {
      itemstack.stackSize = getInventoryStackLimit();
    }
  }
  
  public String getInvName()
  {
    return "Powered Furnace";
  }
  
  public void readFromNBT(NBTTagCompound nbttagcompound)
  {
    super.readFromNBT(nbttagcompound);
    
    this.storedPower = nbttagcompound.getInteger("storedPower");
    
    NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
    this.furnaceItemStacks = new ItemStack[getSizeInventory()];
    for (int i = 0; i < nbttaglist.tagCount(); i++)
    {
      NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
      byte byte0 = nbttagcompound1.getByte("Slot");
      if ((byte0 >= 0) && (byte0 < this.furnaceItemStacks.length))
      {
        this.furnaceItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
      }
    }
  }
  
  public void writeToNBT(NBTTagCompound nbttagcompound)
  {
    super.writeToNBT(nbttagcompound);
    
    nbttagcompound.setInteger("storedPower", storedPower);
    
    NBTTagList nbttaglist = new NBTTagList();
    for (int i = 0; i < this.furnaceItemStacks.length; i++)
    {
      if (this.furnaceItemStacks[i] != null)
      {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setByte("Slot", (byte)i);
        this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
        nbttaglist.appendTag(nbttagcompound1);
      }
    }
    
    nbttagcompound.setTag("Items", nbttaglist);
  }
  
  public int getInventoryStackLimit()
  {
    return 64;
  }
 
  public int getCookProgressScaled(int i)
  {
    return this.storedPower * i / this.smeltPower;
  }
  
  public int getBurnTimeRemainingScaled(int i)
  {
    return getCookProgressScaled(i);
  }
  
  public boolean isBurning()
  {
    return getCookProgressScaled(200) > 0;
  }
  
  public void updateEntity()
  {
	if (storedPower < maxStoredPower)
    this.storedPower = ((int)(this.storedPower + this.powerProvider.useEnergy(5.0F, maxStoredPower, true)));
	
	if (canSmelt())
    if ((this.lastMode == ActionMachineControl.Mode.On) || (this.lastMode == ActionMachineControl.Mode.Loop) || (this.lastMode == ActionMachineControl.Mode.Unknown))
    {
      if (this.storedPower >= this.smeltPower)
      {
        this.storedPower -= this.smeltPower;
        if (this.storedPower > maxStoredPower) {
          this.storedPower = maxStoredPower;
        } else if (this.storedPower < 0)
          this.storedPower = 0;
        smeltItem();
      }
    }
    
    
    if (storedPower > maxStoredPower)
    storedPower = maxStoredPower;
    
    if (this.lastMode == ActionMachineControl.Mode.On) this.lastMode = ActionMachineControl.Mode.Off;
  }
  
  private boolean canSmelt()
  {
    if (this.furnaceItemStacks[0] == null)
    {
      return false;
    }
    ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);
    if (itemstack == null)
    {
      return false;
    }
    if (this.furnaceItemStacks[1] == null)
    {
      return true;
    }
    if (!this.furnaceItemStacks[1].isItemEqual(itemstack))
    {
      return false;
    }
    int st = this.furnaceItemStacks[1].stackSize + itemstack.stackSize;
    return (st <= getInventoryStackLimit()) && (st <= itemstack.getMaxStackSize());
  }
  
  public void smeltItem()
  {
    if (!canSmelt())
    {
      return;
    }
    ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);
    if (this.furnaceItemStacks[1] == null)
    {
      this.furnaceItemStacks[1] = itemstack.copy();
    }
    else if (this.furnaceItemStacks[1].isItemEqual(itemstack))
    {
      this.furnaceItemStacks[1].stackSize += itemstack.stackSize;
    }
    if (this.furnaceItemStacks[0].getItem().hasContainerItem())
    {
      this.furnaceItemStacks[0] = new ItemStack(this.furnaceItemStacks[0].getItem().getContainerItem());
    }
    else {
      this.furnaceItemStacks[0].stackSize -= 1;
    }
    if (this.furnaceItemStacks[0].stackSize <= 0)
    {
      this.furnaceItemStacks[0] = null;
    }
  }
  
  public boolean isUseableByPlayer(EntityPlayer entityplayer)
  {
    if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this)
    {
      return false;
    }
    return entityplayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
  }
  

  public void openChest() {}
  

  public void closeChest() {}
  
  public IInventory getInventory()
  {
    return this;
  }
  
  public int powerRequest() {
     if (storedPower < maxStoredPower)
      return maxStoredPower;
    
    return 0;
  }
  
  public void kill() {}
  
  public boolean isActive()
  {
    return canSmelt();
  }
  
  public boolean manageLiquids() { return false; }
  
  public boolean manageSolids() {
    return true;
  }
  
  public boolean allowActions() { return true; }
  
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
  
  public ItemStack getStackInSlotOnClosing(int slot)
  {
    if (this.furnaceItemStacks[slot] == null) {
      return null;
    }
    ItemStack stackToReturn = this.furnaceItemStacks[slot];
    this.furnaceItemStacks[slot] = null;
    return stackToReturn;
  }
  



  public void actionActivated(IAction action) {}
  


  public int getStartInventorySide(ForgeDirection side)
  {
    return 0;
  }
  

  public int getSizeInventorySide(ForgeDirection side)
  {
    return 0;
  }
}
