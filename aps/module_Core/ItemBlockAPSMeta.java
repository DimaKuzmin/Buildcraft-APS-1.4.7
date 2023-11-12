package aps.module_Core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;








public class ItemBlockAPSMeta
  extends ItemBlock
{
  int blockID;
  
  public ItemBlockAPSMeta(int i)
  {
    super(i);
    
    this.blockID = (i + 256);
    
    setHasSubtypes(true);
    setMaxDamage(0);
  }
  
  public int getMetadata(int i) {
    return i;
  }
  
  public String getItemNameIS(ItemStack itemstack) {
    if ((itemstack.getItem().itemID == module_Core.APSMetaBlockEnergy.blockID) && (module_Core.APSMetaSubs[itemstack.getItemDamage()] != null))
      return module_Core.APSMetaSubs[itemstack.getItemDamage()].getInternalItemName();
    if ((itemstack.getItem().itemID == module_Core.APSMetaBlockMachine.blockID) && (module_Core.APSMetaSubs[(itemstack.getItemDamage() + 16)] != null)) {
      return module_Core.APSMetaSubs[(itemstack.getItemDamage() + 16)].getInternalItemName();
    }
    return "Unknown Block";
  }
}
