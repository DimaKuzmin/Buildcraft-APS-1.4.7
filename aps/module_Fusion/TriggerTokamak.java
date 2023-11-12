package aps.module_Fusion;

import aps.TriggerAPS;
import buildcraft.api.gates.ITriggerParameter;
import net.minecraft.tileentity.TileEntity;

public class TriggerTokamak
  extends TriggerAPS
{
  TriggerType type;
  
  public static enum TriggerType
  {
    TokamakIdling,  TokamakTempReached,  TokamakOutputQuater,  TokamakOutputHalf,  TokamakOutput3Quaters,  TokamakOutputMax;
  }
  

  public TriggerTokamak(int id, TriggerType triggerType)
  {
    super(id);
    this.type = triggerType;
  }
  

  public int getIndexInTexture()
  {
    return 16 + this.type.ordinal();
  }
  

  public String getDescription()
  {
    switch (this.type)
    {
    case TokamakIdling: 
      return "Idling";
    case TokamakTempReached: 
      return "Temperature reached";
    case TokamakOutputQuater: 
      return "Power output 25% of max";
    case TokamakOutputHalf: 
      return "Power output 50% of max";
    case TokamakOutput3Quaters: 
      return "Power output 75% of max";
    case TokamakOutputMax: 
      return "Power output max reached";
    }
    return "";
  }
  



  public boolean hasParameter()
  {
    return false;
  }
  

  public boolean isTriggerActive(TileEntity tile, ITriggerParameter parameter)
  {
    if (!(tile instanceof TileEntityTokamakGenerator)) return false;
    switch (this.type)
    {
    case TokamakIdling: 
      return ((TileEntityTokamakGenerator)tile).isIdling();
    case TokamakTempReached: 
      TileEntityTokamakGenerator tokamak = (TileEntityTokamakGenerator)tile;
      return tokamak.TokamakTemp > tokamak.FusionStartTemp;
    case TokamakOutputQuater: 
      return ((TileEntityTokamakGenerator)tile).getScaledPower(true, 100) >= 25.0F;
    case TokamakOutputHalf: 
      return ((TileEntityTokamakGenerator)tile).getScaledPower(true, 100) >= 50.0F;
    case TokamakOutput3Quaters: 
      return ((TileEntityTokamakGenerator)tile).getScaledPower(true, 100) >= 75.0F;
    case TokamakOutputMax: 
      return ((TileEntityTokamakGenerator)tile).getScaledPower(true, 100) == 100.0F;
    }
    return false;
  }
}
