package aps.module_Machines;

import aps.module_Core.ContainerAPS;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class ContainerGrinder extends ContainerAPS
{
  private TileEntityGrinder tile;
  public ContainerGrinder(TileEntityGrinder cargo, InventoryPlayer playerInv)
  {
    super(cargo, playerInv);
    tile = cargo;
    addSlotToContainer(new Slot(cargo, 0, 30, 35));
    addPlayerInventorySlots(playerInv);
  }
  
  @SideOnly(Side.CLIENT)
  public void updateProgressBar(int chennel, int value)
  {
    super.updateProgressBar(chennel, value);
    if (chennel == 0)
    this.tile.storedPower = value;
  }
  
  
  public void detectAndSendChanges()
  {
	  super.detectAndSendChanges();
		for (int crafters = 0; crafters < this.crafters.size(); ++crafters)
	    {
			ICrafting icraft = (ICrafting)this.crafters.get(crafters);
			if (this.tile != null){
		 
			icraft.sendProgressBarUpdate(this, 0, this.tile.storedPower);
		 
			}
	    }
		 
	}
}
