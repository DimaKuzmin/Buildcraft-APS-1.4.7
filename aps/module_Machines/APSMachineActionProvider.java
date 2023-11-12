package aps.module_Machines;

import buildcraft.api.gates.IActionProvider;
import java.util.LinkedList;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;


public class APSMachineActionProvider
  implements IActionProvider
{
  public LinkedList getNeighborActions(Block block, TileEntity tile)
  {
    LinkedList actions = new LinkedList();
    
    if ((tile instanceof TileEntityEnergyDirector)) {
      actions.add(module_Machines.directorDisableNorth);
      actions.add(module_Machines.directorDisableSouth);
      actions.add(module_Machines.directorDisableEast);
      actions.add(module_Machines.directorDisableWest);
      actions.add(module_Machines.directorEnableNorth);
      actions.add(module_Machines.directorEnableSouth);
      actions.add(module_Machines.directorEnableEast);
      actions.add(module_Machines.directorEnableWest);
    }
    
    return actions;
  }
}
