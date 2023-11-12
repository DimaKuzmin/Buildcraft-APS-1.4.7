package aps.module_Core;

import aps.BuildcraftAPS;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;






public class ItemAPSKit
  extends Item
{
  static int[] TexIndexes = new int[32];
  static String[] KitNames = new String[32];
  static APSKitItemAction[] action = new APSKitItemAction[32];
  
  public ItemAPSKit(int i) {
    super(i);
    
    setHasSubtypes(true);
    setMaxDamage(0);
    
    for (int a = 0; a < 32; a++) TexIndexes[a] = 255;
    for (int a = 0; a < 32; a++) { KitNames[a] = "";
    }
    setCreativeTab(module_Core.APSTab);
  }
  
  public static void registerKit(int Metadata, int IconIndex, String Name)
  {
    TexIndexes[Metadata] = IconIndex;
    KitNames[Metadata] = (module_Core.APSKitItem.getItemName() + "." + Metadata);
    ModLoader.addLocalization(module_Core.APSKitItem.getItemName() + "." + Metadata + ".name", Name + " Kit");
  }
  
  public static void registerKit(int Metadata, int IconIndex, String name, APSKitItemAction itemAction)
  {
    action[Metadata] = itemAction;
    registerKit(Metadata, IconIndex, name);
  }
  

  public int getIconFromDamage(int i)
  {
    if (i >= 32) return 0;
    return TexIndexes[i];
  }
  

  public String getItemNameIS(ItemStack itemstack)
  {
    if (itemstack.getItemDamage() >= 32) return "";
    return KitNames[itemstack.getItemDamage()];
  }
  

  public String getLocalItemName(ItemStack itemstack)
  {
    String s = getItemNameIS(itemstack);
    if (s == null) {
      return "";
    }
    return s;
  }
  

  public String getTextureFile()
  {
    return BuildcraftAPS.imageFilesRoot + "APSItemTexes.png";
  }
  
  public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
  {
    for (int var4 = 0; var4 < 32; var4++)
    {
      if (module_Core.APSMetaSubs[var4] != null) {
        par3List.add(new ItemStack(par1, 1, var4));
      }
    }
  }
  
  public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
  {
    if (action[itemstack.getItemDamage()] != null) {
      return action[itemstack.getItemDamage()].Action(itemstack, world, player);
    }
    return itemstack;
  }
}
