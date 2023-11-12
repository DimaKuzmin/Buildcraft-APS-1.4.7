package aps.module_Machines;

import buildcraft.api.gates.ITriggerProvider;
import buildcraft.api.transport.IPipe;
import java.util.LinkedList;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;



public class APSMachineTriggerProvider
  implements ITriggerProvider
{
  public LinkedList getPipeTriggers(IPipe pipe)
  {
    return null;
  }
  
  public LinkedList getNeighborTriggers(Block block, TileEntity tile)
  {
    LinkedList triggers = new LinkedList();
    
    if ((tile instanceof TileEntityEnergyStore))
    {
      triggers.add(module_Machines.energyStoreFullTrigger);
      triggers.add(module_Machines.energyStoreEmptyTrigger);
      triggers.add(module_Machines.energyStorePartFilledTrigger);
    }
    
    if ((tile instanceof TileEntityMagmafier))
    {
      triggers.add(module_Machines.BlastFurnaceFullTrigger);
      triggers.add(module_Machines.BlastFurnaceEmptyTrigger);
      triggers.add(module_Machines.BlastFurnacePartFilledTrigger);
    }
    
    return triggers;
  }
}
