package aps.module_Machines;

import aps.TriggerAPS;
import buildcraft.api.gates.ITriggerParameter;
import net.minecraft.tileentity.TileEntity;






public class TriggerMachines
  extends TriggerAPS
{
  TriggerType type;
  
  public static enum TriggerType
  {
    EnergyStoreFull,  EnergyStoreEmpty,  EnergyStorePartFilled, 
    BlastFurnaceBlockEmpty,  BlastFurnaceBlockFull,  BlastFurnaceBlockPartFilled;
  }
  


  public TriggerMachines(int id, TriggerType triggerType)
  {
    super(id);
    this.type = triggerType;
  }
  

  public int getIndexInTexture()
  {
    return this.type.ordinal();
  }
  

  public String getDescription()
  {
    switch (this.type)
    {
    case EnergyStoreFull: 
      return "Store Full";
    case EnergyStoreEmpty: 
      return "Store Empty";
    case EnergyStorePartFilled: 
      return "Store Part Filled";
    case BlastFurnaceBlockFull: 
      return "Blast Furnace Full";
    case BlastFurnaceBlockEmpty: 
      return "Blast Furnace Empty";
    case BlastFurnaceBlockPartFilled: 
      return "Blast Furnace Half Filled";
    }
    return "";
  }
  


  public boolean hasParameter()
  {
    return false;
  }
  

  public boolean isTriggerActive(TileEntity tile, ITriggerParameter parameter)
  {
    if (((tile instanceof TileEntityEnergyStore)) || ((tile instanceof TileEntityMagmafier))) {
      switch (this.type)
      {
      case EnergyStoreFull: 
        return ((TileEntityEnergyStore)tile).PowerLevel == TileEntityEnergyStore.MaxPowerStored;
      case EnergyStoreEmpty: 
        return ((TileEntityEnergyStore)tile).PowerLevel == 0;
      case EnergyStorePartFilled: 
        return ((TileEntityEnergyStore)tile).getScaledPowerStored(100) >= 50.0F;
      case BlastFurnaceBlockFull: 
        return ((TileEntityMagmafier)tile).BlockLevel == TileEntityMagmafier.BlockCapacity;
      case BlastFurnaceBlockEmpty: 
        return ((TileEntityMagmafier)tile).BlockLevel == 0;
      case BlastFurnaceBlockPartFilled: 
        return ((TileEntityMagmafier)tile).getScaledBlockQuantity(100) >= 50.0F;
      }
      return false;
    }
    
    return false;
  }
}
