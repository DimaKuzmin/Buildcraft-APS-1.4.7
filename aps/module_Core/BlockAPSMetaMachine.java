package aps.module_Core;

import java.util.List;

import aps.BuildcraftAPS;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class BlockAPSMetaMachine extends BlockAPSMeta
{
  protected BlockAPSMetaMachine(int i, Material m)
  {
    super(i, m);
    setCreativeTab(module_Core.APSTab);
  }
  
  public TileEntity getBlockEntity(int md)
  {
    if (module_Core.APSMetaSubs[(md + 16)] == null) return null;
    return module_Core.APSMetaSubs[(md + 16)].getBlockEntity();
  }
  

  public void getSubBlocks(int blockid, CreativeTabs tabs, List itemList)
  {
    for (int i = 0; i < 16; i++) {
      if (module_Core.APSMetaSubs[(i + 16)] != null) {
        itemList.add(new ItemStack(this, 1, i));
      }
    }
  }
}
