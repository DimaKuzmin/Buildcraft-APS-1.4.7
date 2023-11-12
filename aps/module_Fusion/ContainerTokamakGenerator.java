package aps.module_Fusion;

import aps.module_Core.ContainerAPS;
import aps.module_Core.TileEntityAPS;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;




public class ContainerTokamakGenerator extends ContainerAPS
{
  public ContainerTokamakGenerator(TileEntityTokamakGenerator cargo, InventoryPlayer playerInv)
  {
    super(cargo, playerInv);
    addSlotToContainer(new Slot(cargo.Inventory, 0, 9, 40));
    addPlayerInventorySlots(playerInv);
  }
  
  @Override
	public void detectAndSendChanges() {
 		super.detectAndSendChanges();
 		
	}
  
  @Override
	public void updateProgressBar(int id, int value) {
 		super.updateProgressBar(id, value);
		
		
	}
}
