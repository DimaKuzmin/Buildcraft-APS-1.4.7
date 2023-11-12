package aps.module_Core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;



public class InventoryAPS
  implements IInventory
{
  public ItemStack[] InputSlot;
  public boolean inventoryChanged;
  public int currentItem;
  String invName;
  
  public InventoryAPS(int InvSize, String InvName)
  {
    this.InputSlot = new ItemStack[InvSize];
    this.currentItem = 0;
    this.inventoryChanged = false;
    this.invName = InvName;
  }
  
  public boolean addItemStackToInventory(ItemStack itemstack)
  {
    if (!itemstack.isItemDamaged())
    {
      int StackSize;
      do
      {
        StackSize = itemstack.stackSize;
        itemstack.stackSize = storePartialItemStack(itemstack);
      }
      while ((itemstack.stackSize > 0) && (itemstack.stackSize < StackSize));
      
      return itemstack.stackSize < StackSize;
    }
    int j = getFirstEmptyStack();
    if (j >= 0)
    {
      this.InputSlot[j] = ItemStack.copyItemStack(itemstack);
      this.InputSlot[j].animationsToGo = 5;
      itemstack.stackSize = 0;
      return true;
    }
    
    return false;
  }
  

  private int storeItemStack(ItemStack itemstack)
  {
    for (int i = 0; i < this.InputSlot.length; i++)
    {
      if ((this.InputSlot[i] != null) && (this.InputSlot[i].itemID == itemstack.itemID) && (this.InputSlot[i].isStackable()) && (this.InputSlot[i].stackSize < this.InputSlot[i].getMaxStackSize()) && (this.InputSlot[i].stackSize < getInventoryStackLimit()) && ((!this.InputSlot[i].getHasSubtypes()) || (this.InputSlot[i].getItemDamage() == itemstack.getItemDamage())))
      {






        return i;
      }
    }
    
    return -1;
  }
  
  private int storePartialItemStack(ItemStack itemstack)
  {
    int i = itemstack.itemID;
    int j = itemstack.stackSize;
    int k = storeItemStack(itemstack);
    if (k < 0)
    {
      k = getFirstEmptyStack();
    }
    if (k < 0)
    {
      return j;
    }
    if (this.InputSlot[k] == null)
    {
      this.InputSlot[k] = new ItemStack(i, 0, itemstack.getItemDamage());
    }
    int l = j;
    if (l > this.InputSlot[k].getMaxStackSize() - this.InputSlot[k].stackSize)
    {
      l = this.InputSlot[k].getMaxStackSize() - this.InputSlot[k].stackSize;
    }
    if (l > getInventoryStackLimit() - this.InputSlot[k].stackSize)
    {
      l = getInventoryStackLimit() - this.InputSlot[k].stackSize;
    }
    if (l == 0)
    {
      return j;
    }
    
    j -= l;
    this.InputSlot[k].stackSize += l;
    this.InputSlot[k].animationsToGo = 5;
    return j;
  }
  

  private int getFirstEmptyStack()
  {
    for (int i = 0; i < this.InputSlot.length; i++)
    {
      if (this.InputSlot[i] == null)
      {
        return i;
      }
    }
    
    return -1;
  }
  
  public ItemStack getStackInSlot(int i)
  {
    if (i < this.InputSlot.length) {
      return this.InputSlot[i];
    }
    return null;
  }
  
  public void setInventorySlotContents(int i, ItemStack itemstack)
  {
    if (i < this.InputSlot.length) {
      this.InputSlot[i] = itemstack;
    }
  }
  
  public void onInventoryChanged() {
    this.inventoryChanged = true;
  }
  
  public int getSizeInventory()
  {
    return this.InputSlot.length;
  }
  
  public int getInventoryStackLimit()
  {
    return 64;
  }
  
  public ItemStack decrStackSize(int i, int j)
  {
    ItemStack[] aitemstack = this.InputSlot;
    if (aitemstack[i] != null)
    {
      if (aitemstack[i].stackSize <= j)
      {
        ItemStack itemstack = aitemstack[i];
        aitemstack[i] = null;
        return itemstack;
      }
      ItemStack itemstack1 = aitemstack[i].splitStack(j);
      if (aitemstack[i].stackSize == 0)
      {
        aitemstack[i] = null;
      }
      return itemstack1;
    }
    
    return null;
  }
  

  public boolean isUseableByPlayer(EntityPlayer entityplayer)
  {
    if (entityplayer.isDead)
    {
      return false;
    }
    return entityplayer.getDistanceSqToEntity(entityplayer) <= 64.0D;
  }
  

  public void openChest() {}
  

  public void closeChest() {}
  
  public String getInvName()
  {
    return this.invName;
  }
  

  public ItemStack getStackInSlotOnClosing(int var1)
  {
    return null;
  }
}
