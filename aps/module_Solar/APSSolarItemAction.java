package aps.module_Solar;

import aps.module_Core.APSKitItemAction;
import aps.module_Core.module_Core;
import buildcraft.BuildCraftCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class APSSolarItemAction extends APSKitItemAction
{
  public ItemStack Action(ItemStack itemstack, World world, EntityPlayer player)
  {
    if (player.inventory.hasItem(BuildCraftCore.wrenchItem.itemID))
    {
      if (player.inventory.addItemStackToInventory(new ItemStack(module_Core.APSMetaBlockEnergy, 1, itemstack.getItemDamage()))) {
        itemstack.stackSize -= 1;
      }
    }
    
    return itemstack;
  }
}
