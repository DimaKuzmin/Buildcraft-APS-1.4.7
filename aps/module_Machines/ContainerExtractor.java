package aps.module_Machines;

import aps.module_Core.ContainerAPS;
import aps.module_Core.TileEntityAPS;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraftforge.liquids.LiquidTank;

public class ContainerExtractor extends ContainerAPS
{
  private TileEntityExtractor tile;


public ContainerExtractor(TileEntityExtractor cargo, InventoryPlayer playerInv)
  {
    super(cargo, playerInv);
    tile = cargo;
    addPlayerInventorySlots(playerInv);
  }
  
  @SideOnly(Side.CLIENT)
  public void updateProgressBar(int chennel, int value)
  {
    if (chennel == 0)
    this.tile.PowerLevelMA = value;
    if (chennel == 1)
    this.tile.tank = new LiquidTank(tile.LavaID,  value, tile.LavaID);
  }
  
  
  public void detectAndSendChanges()
  {
	  super.detectAndSendChanges();
		for (int i = 0; i < crafters.size(); ++i)
	    {
			ICrafting icraft = (ICrafting) this.crafters.get(i);
 				icraft.sendProgressBarUpdate(this, 0, (int) this.tile.PowerLevelMA);
				icraft.sendProgressBarUpdate(this, 1, tile.tank.getLiquid() != null ? tile.tank.getLiquid().amount : 0);
			
	    }
		 
  }
}
