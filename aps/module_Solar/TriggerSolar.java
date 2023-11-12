package aps.module_Solar;

import buildcraft.api.gates.ITriggerParameter;

public class TriggerSolar extends aps.TriggerAPS {
  TriggerType type;
  
  public static enum TriggerType {
    SolarPowerOutput;
  }
  

  public TriggerSolar(int id, TriggerType triggerType)
  {
    super(id);
    this.type = triggerType;
  }
  

  public int getIndexInTexture()
  {
    return 32 + this.type.ordinal();
  }
  

  public String getDescription()
  {
    switch (this.type)
    {
    case SolarPowerOutput: 
      return "Power Output";
    }
    return "";
  }
  


  public boolean hasParameter()
  {
    return this.type == TriggerType.SolarPowerOutput;
  }
  

  public boolean isTriggerActive(net.minecraft.tileentity.TileEntity tile, ITriggerParameter parameter)
  {
    if (!(tile instanceof TileEntitySolarCollector)) return false;
    switch (this.type)
    {


    case SolarPowerOutput: 
      return false; }
    
    return false;
  }
}
