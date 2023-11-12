package aps.module_Core;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class BlockAPSMetaEnergy extends BlockAPSMeta
{
  protected BlockAPSMetaEnergy(int i, Material m)
  {
    super(i, m);
    setCreativeTab(module_Core.APSTab);
  }
  
  public TileEntity getBlockEntity(int md)
  {
    if (module_Core.APSMetaSubs[md] == null) return null;
    return module_Core.APSMetaSubs[md].getBlockEntity();
  }
  

  public void getSubBlocks(int blockid, CreativeTabs tabs, List itemList)
  {
    for (int i = 0; i < 16; i++) {
      if (module_Core.APSMetaSubs[i] != null) {
        itemList.add(new ItemStack(this, 1, i));
      }
    }
  }
}
