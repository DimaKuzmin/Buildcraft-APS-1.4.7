package aps.module_Core;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;



public class ContainerAPS extends Container
{
  protected int inventorySize = 0;
  
  public TileEntityAPS tileEntity;
  
  public ContainerAPS(TileEntityAPS cargo, InventoryPlayer playerInv)
  {
    if (cargo.getInventory() != null)
      this.inventorySize = cargo.getInventory().getSizeInventory();
    this.tileEntity = cargo;
  }
  

  public ContainerAPS(InventoryPlayer playerInv)
  {
    addPlayerInventorySlots(playerInv);
  }
  


  public void addPlayerInventorySlots(InventoryPlayer playerInv)
  {
    for (int i = 0; i < 3; i++)
      for (int k = 0; k < 9; k++)
        addSlotToContainer(new Slot(playerInv, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
    for (int j = 0; j < 9; j++) {
      addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 142));
    }
  }
  
  public boolean canInteractWith(EntityPlayer player) {
    return true;
  }
 
  public ItemStack transferStackInSlot(EntityPlayer pl, int i)
  {
    ItemStack itemstack = null;
    Slot slot = (Slot)this.inventorySlots.get(i);
    if ((slot != null) && (slot.getHasStack()))
    {
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();
      if (i < this.inventorySize)
      {
        if (!mergeItemStack(itemstack1, this.inventorySize, this.inventorySlots.size(), true))
        {
          return null;
        }
      }
      else if (!mergeItemStack(itemstack1, 0, this.inventorySize, false))
      {
        return null;
      }
      if (itemstack1.stackSize == 0)
      {
        slot.putStack(null);
      }
      else {
        slot.onSlotChanged();
      }
    }
    return itemstack;
  }
}
