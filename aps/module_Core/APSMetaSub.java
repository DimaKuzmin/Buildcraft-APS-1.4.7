package aps.module_Core;

import aps.module_Core.module_Core.APSBlockTypes;
import net.minecraft.tileentity.TileEntity;

public class APSMetaSub
{
  Class ent;
  String internalName;
  String ingameName;
  APSBlockTypes type;
  
  public APSMetaSub(int Index, Class Ent, String InternalName, String IngameName, APSBlockTypes type)
  {
    this.ent = Ent;
    this.internalName = InternalName;
    this.ingameName = IngameName;
    this.type = type;
    
    if (type == module_Core.APSBlockTypes.Energy) {
      net.minecraft.src.ModLoader.addLocalization(module_Core.APSMetaBlockEnergy.getBlockName() + "." + this.internalName + ".name", this.ingameName);
    }
    else {
      net.minecraft.src.ModLoader.addLocalization(module_Core.APSMetaBlockMachine.getBlockName() + "." + this.internalName + ".name", this.ingameName);
    }
  }
  

  public TileEntity getBlockEntity()
  {
    try
    {
      return (TileEntity)this.ent.newInstance();
    }
    catch (InstantiationException e)
    {
      e.printStackTrace();
      return null;
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace(); }
    return null;
  }
  


  public String getInternalName() { return this.internalName; }
  
  public String getInternalBlockName() { if (this.type == module_Core.APSBlockTypes.Energy) {
      return module_Core.APSMetaBlockEnergy.getBlockName() + "." + this.internalName;
    }
    return module_Core.APSMetaBlockMachine.getBlockName() + "." + this.internalName;
  }
  
  public String getInternalItemName() { if (this.type == module_Core.APSBlockTypes.Energy) {
      return module_Core.APSMetaBlockEnergyItem.getItemName() + "." + this.internalName;
    }
    return module_Core.APSMetaBlockMachineItem.getItemName() + "." + this.internalName;
  }
}
