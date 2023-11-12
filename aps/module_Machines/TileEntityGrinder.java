package aps.module_Machines;

import aps.BuildcraftAPS;
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
import buildcraft.core.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;



public class TileEntityGrinder
  extends TileEntityAPSPowered
  implements IPowerReceptor, IInventory, IActionReceptor, IMachine
{
  private ItemStack[] grinderItemStacks;
  public GrinderRecipe currentRecipe = null;
  
  public static int maxStoredPower = 1000;
  public int storedPower = 0;
  
  public TileEntityGrinder()
  {
    super(0, 35, 1, 2, 0, 5, maxStoredPower, 5, maxStoredPower, module_Core.APSBlockTypes.Machine);
    this.hasGUI = true;
    this.GuiID = 120;
    this.grinderItemStacks = new ItemStack[1];
  }
  
  void checkRecipe()
  {
    if ((this.currentRecipe == null) || (!this.currentRecipe.isIngredient(this.grinderItemStacks[0])))
    {
      this.currentRecipe = null;
      for (GrinderRecipe recipe : GrinderRecipes.grinderRecipes)
      {
        if (recipe.isIngredient(this.grinderItemStacks[0]))
        {
          this.currentRecipe = recipe;
        }
      }
    }
  }
  
  public int getSizeInventory()
  {
    return this.grinderItemStacks.length;
  }
  
  public int getStartInventorySide(int side) {
    return 0;
  }
  
  public int getSizeInventorySide(int side) {
    return 1;
  }
  
  public ItemStack getStackInSlot(int i)
  {
    return this.grinderItemStacks[i];
  }
  
  public ItemStack decrStackSize(int i, int j)
  {
    if (this.grinderItemStacks[i] != null)
    {
      if (this.grinderItemStacks[i].stackSize <= j)
      {
        ItemStack itemstack = this.grinderItemStacks[i];
        this.grinderItemStacks[i] = null;
        checkRecipe();
        return itemstack;
      }
      ItemStack itemstack1 = this.grinderItemStacks[i].splitStack(j);
      if (this.grinderItemStacks[i].stackSize == 0)
      {
        this.grinderItemStacks[i] = null;
      }
      checkRecipe();
      return itemstack1;
    }
    
    return null;
  }
  

  public void setInventorySlotContents(int i, ItemStack itemstack)
  {
    this.grinderItemStacks[i] = itemstack;
    if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit()))
    {
      itemstack.stackSize = getInventoryStackLimit();
    }
    checkRecipe();
  }
  
  public String getInvName()
  {
    return "Block Grinder";
  }
  

  public int getInventoryStackLimit()
  {
    return 64;
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
  
  public IInventory getInventory() {
    return this;
  }
  
  public ItemStack getStackInSlotOnClosing(int var1)
  {
    return null;
  }
 
  public void writeToNBT(NBTTagCompound nbttagcompound)
  {
    super.writeToNBT(nbttagcompound);
    NBTTagList nbttaglist = new NBTTagList();
    for (int i = 0; i < this.grinderItemStacks.length; i++)
    {
      if (this.grinderItemStacks[i] != null)
      {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setByte("Slot", (byte)i);
        this.grinderItemStacks[i].writeToNBT(nbttagcompound1);
        nbttaglist.appendTag(nbttagcompound1);
      }
    }
    
    nbttagcompound.setTag("Items", nbttaglist);
  }
  
  public String[] getPossibleProducts()
  {
    if (this.currentRecipe == null) return new String[0];
    ItemStack[] P = this.currentRecipe.getAllProducts();
    String[] Output = new String[P.length];
    for (int i = 0; i < P.length; i++)
      Output[i] = (P[i].getItem().getItemDisplayName(P[i]) + " " + this.currentRecipe.chances[i] + "%");
    return Output;
  }
  
  public void readFromNBT(NBTTagCompound nbttagcompound)
  {
    super.readFromNBT(nbttagcompound);
    NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
    this.grinderItemStacks = new ItemStack[getSizeInventory()];
    for (int i = 0; i < nbttaglist.tagCount(); i++)
    {
      NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
      byte byte0 = nbttagcompound1.getByte("Slot");
      if ((byte0 >= 0) && (byte0 < this.grinderItemStacks.length))
      {
        this.grinderItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
      }
    }
    
    checkRecipe();
  }
  
  public void updateEntity()
  {
    super.updateEntity();
   
    if (this.worldObj.isRemote) { return; }
   
    if ((this.grinderItemStacks[0] == null) && (this.currentRecipe != null)) {
      checkRecipe();
    }
    
    if (this.lastMode != ActionMachineControl.Mode.Off)
    {
      this.storedPower = ((int)(this.storedPower + this.powerProvider.useEnergy(1.0F, maxStoredPower - this.storedPower, true)));
 
      if ((canGrind()) && (this.storedPower >= this.currentRecipe.getEnergyRequired()))
      {
        this.storedPower = ((int)(this.storedPower - this.currentRecipe.getEnergyRequired()));
        grindItem();
      }
    }
    if (this.lastMode == ActionMachineControl.Mode.On) this.lastMode = ActionMachineControl.Mode.Off;
  }
  
  public boolean canGrind() { return (this.currentRecipe != null) && (this.grinderItemStacks[0] != null) && (this.currentRecipe.getIngredient().itemID == this.grinderItemStacks[0].itemID); }
  
  public void grindItem()
  {
    if (this.grinderItemStacks[0].getItem().hasContainerItem())
    {
      this.grinderItemStacks[0] = new ItemStack(this.grinderItemStacks[0].getItem().getContainerItem());
    }
    else {
      this.grinderItemStacks[0].stackSize -= 1;
    }
    if (this.grinderItemStacks[0].stackSize <= 0)
    {
      this.grinderItemStacks[0] = null;
    }
    
    ItemStack itemstack = this.currentRecipe.getRandomProduct();
    if (itemstack != null)
    {
    boolean added = false;		
    
    
	added = Utils.addToRandomPipeEntry(this, ForgeDirection.UNKNOWN, itemstack);
     
   
    if (itemstack != null){
   	  int added2 = Utils.addToRandomInventory(itemstack, worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.UNKNOWN).stackSize;
   	  if (added2 != 0){
   		  added = true;
   	  }
    }
      
    if (!added) {
        EntityItem entityitem = new EntityItem(this.worldObj, this.xCoord + 0.5D, this.yCoord + 0.7D, this.zCoord + 0.5D, itemstack.copy());
        this.worldObj.spawnEntityInWorld(entityitem);
    }
    }
  }
  
  
  
  public int powerRequest() {
    if (canGrind()) {
      return (int)this.currentRecipe.getEnergyRequired();
    }
    return 0;
  }
  



  public void kill() {}
  


  public boolean isActive()
  {
    return canGrind();
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

  public void actionActivated(IAction action) {}
  

  public boolean isPipeConnected(ForgeDirection from)
  {
    return true;
  }

  public int getGrindProgressScaled(int i) {
	    int j;
	     if (this.canGrind()) {
	      j = (int)(this.storedPower / this.currentRecipe.getEnergyRequired() * i);
	    } else
	      j = 0;
	    if (j > i) return i; return j;
  } 

}
