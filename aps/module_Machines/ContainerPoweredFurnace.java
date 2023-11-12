package aps.module_Machines;

import aps.module_Core.ContainerAPS;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

public class ContainerPoweredFurnace extends ContainerAPS
{
  private TileEntityPoweredFurnace tile;
  public ContainerPoweredFurnace(TileEntityPoweredFurnace cargo, InventoryPlayer playerInv)
  {
    super(cargo, playerInv);
    tile = cargo;
    addSlotToContainer(new Slot(cargo, 0, 53, 35));
    addSlotToContainer(new SlotFurnace(playerInv.player, cargo, 1, 101, 35));
    addPlayerInventorySlots(playerInv);
  }
  
  @SideOnly(Side.CLIENT)
  @Override
  public void updateProgressBar(int chennel, int value)
  {
    if (chennel == 0)
    this.tile.storedPower = value;
  }
  
  @Override
  public void detectAndSendChanges()
  {
	  super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i)
	    {
			ICrafting icraft = (ICrafting)this.crafters.get(i);
			 
			icraft.sendProgressBarUpdate(this, 0, this.tile.storedPower);
			
	    }
		 
  }
}
