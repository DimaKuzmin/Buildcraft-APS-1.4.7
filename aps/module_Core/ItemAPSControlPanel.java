package aps.module_Core;

import aps.BuildcraftAPS;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAPSControlPanel
  extends Item
{
  protected GuiControlPanel.Mode lastPanelMode;
  
  protected ItemAPSControlPanel(int i)
  {
    super(i);
    
    setHasSubtypes(true);
    setMaxDamage(0);
  }
  

  public int getIconFromDamage(int i)
  {
    return 9;
  }
  

  public String getItemDisplayName(ItemStack itemstack)
  {
    return "Control Panel";
  }
  

  public String getTextureFile()
  {
    return BuildcraftAPS.imageFilesRoot + "APSItemTexes.png";
  }
  





  public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
  {
    return stack;
  }
}
