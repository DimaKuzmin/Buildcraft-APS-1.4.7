package aps.module_Fusion;

import buildcraft.api.gates.ITriggerProvider;
import buildcraft.api.transport.IPipe;
import java.util.LinkedList;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;











public class APSFusionTriggerProvider
  implements ITriggerProvider
{
  public LinkedList getNeighborTriggers(Block block, TileEntity tile)
  {
    LinkedList result = new LinkedList();
    
    if ((tile instanceof TileEntityTokamakGenerator)) {
      result.add(module_Fusion.tokamakIdlingTrigger);
      result.add(module_Fusion.tokamakOutputQuaterTrigger);
      result.add(module_Fusion.tokamakOutputHalfTrigger);
      result.add(module_Fusion.tokamakOutput3QuatersTrigger);
      result.add(module_Fusion.tokamakOutputMaxTrigger);
      result.add(module_Fusion.tokamakTempReachedTrigger);
    }
    
    return result;
  }
  

  public LinkedList getPipeTriggers(IPipe pipe)
  {
    LinkedList result = new LinkedList();
    
    if ((pipe instanceof APSPipePower)) {
      result.add(module_Fusion.pipeCloggedTrigger);
      result.add(module_Fusion.pipeFreeTrigger);
    }
    
    return result;
  }
}
