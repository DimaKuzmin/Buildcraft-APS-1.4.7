package aps.module_Fusion;

import aps.BuildcraftAPS;
import net.minecraft.item.Item;




public class ItemHeavyWaterBucket
  extends Item
{
  public ItemHeavyWaterBucket(int i)
  {
    super(i);
    this.maxStackSize = 1;
    this.iconIndex = 7;
  }
  

  public String getTextureFile()
  {
    return BuildcraftAPS.imageFilesRoot + "APSItemTexes.png";
  }
}
