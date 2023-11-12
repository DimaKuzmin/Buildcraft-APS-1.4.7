package aps.module_Fusion;

import aps.TriggerAPS;
import buildcraft.api.gates.ITriggerParameter;
import buildcraft.transport.Pipe;

public class TriggerAdvancedPipe extends TriggerAPS implements buildcraft.transport.ITriggerPipe
{
  TriggerType type;
  
  public static enum TriggerType
  {
    pipeClogged,  pipeFree;
  }
  
  public TriggerAdvancedPipe(int id, TriggerType trigger)
  {
    super(id);
    this.type = trigger;
  }
  
  public int getIndexInTexture()
  {
    return 64 + this.type.ordinal();
  }
  
  public String getDescription()
  {
    switch (this.type)
    {
    case pipeClogged: 
      return "Pipe Overloading";
    case pipeFree: 
      return "Pipe running";
    }
    return "";
  }
  

  public boolean hasParameter()
  {
    return false;
  }
  
  public boolean isTriggerActive(Pipe pipe, ITriggerParameter parameter)
  {
    if ((pipe.transport instanceof buildcraft.transport.PipeTransportPower))
    {
      if ((pipe instanceof APSPipePower)) {
        if (this.type == TriggerType.pipeClogged)
          return ((APSPipePower)pipe).clogged;
        if (this.type == TriggerType.pipeFree)
          return !((APSPipePower)pipe).clogged;
      }
    }
    return false;
  }
  
  public boolean isTriggerActive(net.minecraft.tileentity.TileEntity tile, ITriggerParameter parameter)
  {
    return false;
  }
}
