package aps.module_Machines;

import aps.BuildcraftAPS;
import aps.module_Core.ContainerAPS;
import aps.module_Core.TileEntityAPS;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerEnergyStore extends ContainerAPS
{
  private TileEntityEnergyStore tile;
 
  
  public ContainerEnergyStore(TileEntityEnergyStore cargo, InventoryPlayer playerInv)
  {
    super(cargo, playerInv);
    tile = cargo;
    addPlayerInventorySlots(playerInv);   
  }
  
  @Override 
  public void updateProgressBar(int id, int value)
  {
    if (id == 0)
    this.tile.PowerIn = value;
    if (id == 1)
    this.tile.PowerOut = value;
    if (id == 2)
    this.tile.PowerLevel = value;
    if (id == 3)
    this.tile.UserMaxPowerIn = value;
    if (id == 4)
    this.tile.UserMaxPowerOut = value;
  }
  
  @Override
  public void detectAndSendChanges()
  {
	  	super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i)
	    {
 			this.sendUpdate((ICrafting) crafters.get(i));
	    }
   }

  private void sendUpdate(ICrafting craft) {
	craft.sendProgressBarUpdate(this, 0, (int) this.tile.PowerIn);
	craft.sendProgressBarUpdate(this, 1, (int) this.tile.PowerOut);
	craft.sendProgressBarUpdate(this, 2, (int) this.tile.PowerLevel);
	craft.sendProgressBarUpdate(this, 3, (int) this.tile.UserMaxPowerIn);
	craft.sendProgressBarUpdate(this, 4, (int) this.tile.UserMaxPowerOut);
}

 
}
