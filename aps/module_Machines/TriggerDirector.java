package aps.module_Machines;

import aps.TriggerAPS;
import buildcraft.api.gates.ITriggerParameter;

public class TriggerDirector extends TriggerAPS
{
  public TriggerDirector(int id)
  {
    super(id);
  }
  

  public int getIndexInTexture()
  {
    return 0;
  }
  

  public String getDescription()
  {
    return null;
  }
  

  public boolean hasParameter()
  {
    return false;
  }
  

  public boolean isTriggerActive(net.minecraft.tileentity.TileEntity tile, ITriggerParameter parameter)
  {
    return false;
  }
}
