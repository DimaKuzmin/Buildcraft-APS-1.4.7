package aps.module_Fusion;

import aps.BuildcraftAPS;
import net.minecraft.item.Item;

public class ItemHeavyWater extends Item
{
  public ItemHeavyWater(int i)
  {
    super(i);
    this.iconIndex = 8;
  }
  
  public String getTextureFile()
  {
    return BuildcraftAPS.imageFilesRoot + "APSItemTexes.png";
  }
}
