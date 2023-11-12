package aps.module_Core;

import java.util.List;

import aps.BuildcraftAPS;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;



public class ItemAPSPowerCore
  extends Item
{
  public ItemAPSPowerCore(int i)
  {
    super(i);
    
    setHasSubtypes(true);
    setMaxDamage(0);
  }
  

  public int getIconFromDamage(int i)
  {
    if (i < 4) {
      return 17 + i;
    }
    return 16;
  }
  
  public String getItemNameIS(ItemStack itemstack)
  {
    switch (itemstack.getItemDamage()) {
    case 0: 
      return "redstonepowercore";
    case 1: 
      return "ironpowercore";
    case 2: 
      return "goldpowercore";
    case 3: 
      return "diamondpowercore";
    }
    return "powercore";
  }
  


  public String getTextureFile()
  {
    return BuildcraftAPS.imageFilesRoot + "APSItemTexes.png";
  }
  
  public void getSubItems(int item, CreativeTabs tabs, List list)
  {
    for (int i = 0; i <= 3; i++)
    {
      list.add(new ItemStack(item, 1, i));
    }
  }
}
