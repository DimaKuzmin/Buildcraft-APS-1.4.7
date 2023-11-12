package aps.module_Machines;

import aps.BuildcraftAPS;
import aps.module_Core.ContainerAPS;
import aps.module_Core.TileEntityAPS;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;



public class ContainerMagmafier extends ContainerAPS
{
  private TileEntityMagmafier tile;
  public ContainerMagmafier(TileEntityMagmafier cargo, InventoryPlayer playerInv)
  {
    super(cargo, playerInv);
    tile = cargo;
    addSlotToContainer(new Slot(cargo, 0, 53, 35));
    addPlayerInventorySlots(playerInv);
  }
  
 
  
  @SideOnly(Side.CLIENT)
  public void updateProgressBar(int chennel, int value)
  {
    if (chennel == 0)
    this.tile.PowerLevel = value;
    if (chennel == 1)
    this.tile.BlockLevel = value;
    if (chennel == 2)
	this.tile.tank = new LiquidTank(tile.LavaID, value, tile.LavaCapacity);
 	
  }
  
  
  public void detectAndSendChanges()
  {
	  super.detectAndSendChanges();
		for (int crafters = 0; crafters < this.crafters.size(); ++crafters)
	    {
			ICrafting icraft = (ICrafting)this.crafters.get(crafters);
				icraft.sendProgressBarUpdate(this, 0, (int) this.tile.PowerLevel);
				icraft.sendProgressBarUpdate(this, 1, (int) this.tile.BlockLevel);
				icraft.sendProgressBarUpdate(this, 2, this.tile.tank.getLiquid() == null ? 0 :  this.tile.tank.getLiquid().amount);
	    }
		 
  }
}
