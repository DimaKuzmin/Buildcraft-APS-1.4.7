package aps.module_Machines;

import buildcraft.api.gates.Action;

public class ActionDirector extends Action {
  Mode mode;
  
  public static enum Mode { disableNorth,  disableSouth,  disableEast,  disableWest,  enableNorth,  enableSouth,  enableEast,  enableWest; }
  
  public ActionDirector(int id, Mode type)
  {
    super(id);
    this.mode = type;
  }
  
  public String getTexture()
  {
    return aps.BuildcraftAPS.imageFilesRoot + "APSTriggerTexes.png";
  }
  
  public int getIndexInTexture()
  {
    return 6 + this.mode.ordinal();
  }
  
  public String getDescription() {
    switch (this.mode) {
    case disableNorth: 
      return "Disable North Face";
    case disableSouth: 
      return "Disable South Face";
    case disableEast: 
      return "Disable East Face";
    case disableWest: 
      return "Disable West Face";
    case enableNorth: 
      return "Enable North Face";
    case enableSouth: 
      return "Enable South Face";
    case enableEast: 
      return "Enable East Face";
    case enableWest: 
      return "Enable West Face";
    }
    return "";
  }
}
