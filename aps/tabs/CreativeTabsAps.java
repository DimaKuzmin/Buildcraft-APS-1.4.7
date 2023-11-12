package aps.tabs;

import aps.module_Core.ItemAPSPowerCore;
import aps.module_Core.module_Core;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabsAps extends CreativeTabs {

 
	private ItemStack PowerCore = new ItemStack(module_Core.APSPowerCoreItem, 1, 3);

	public CreativeTabsAps(String label) {
		super(label);
 	}
	
	@Override
	public ItemStack getIconItemStack() {
 		return PowerCore;
	}
	
	@Override
	public String getTranslatedTabLabel() {
		// TODO Auto-generated method stub
		return "APS";
	}
}
