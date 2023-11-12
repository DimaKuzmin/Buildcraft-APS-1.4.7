package aps.module_Core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class APSKitItemAction
{
  public abstract ItemStack Action(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer);
}
