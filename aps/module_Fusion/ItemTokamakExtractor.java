package aps.module_Fusion;

import aps.BuildcraftAPS;
import aps.module_Core.module_Core;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;



public class ItemTokamakExtractor
  extends Item
{
  public ItemTokamakExtractor(int i)
  {
    super(i);
    this.maxStackSize = 64;
    this.iconIndex = 3;
    setCreativeTab(module_Core.APSTab);
  }
  

  public String getTextureFile()
  {
    return BuildcraftAPS.imageFilesRoot + "APSItemTexes.png";
  }
}
