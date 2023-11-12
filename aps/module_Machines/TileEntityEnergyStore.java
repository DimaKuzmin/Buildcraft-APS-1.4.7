package aps.module_Machines;

import aps.BuildcraftAPS;
import aps.module_Core.InventoryAPS;
import aps.module_Core.TileEntityAPSPowered;
import aps.module_Core.module_Core;
import buildcraft.api.core.Position;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.core.network.PacketPayload;
import buildcraft.core.network.PacketUpdate;
import buildcraft.core.network.TileNetworkData;
import buildcraft.core.network.TilePacketWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityEnergyStore extends TileEntityAPSPowered implements IPowerReceptor
{
  static int MaxPowerStored = 1000000;
  static int MaxPowerIn = 100;
  static int MaxPowerOut = 100;
  
  int PowerLevel;
  
  float PowerIn;
  
  float PowerOut;
  float UserMaxPowerIn;
  float UserMaxPowerOut;
  int SideUpdateDelay = 0;
  
  boolean IsDraining;
  
  
  public TileEntityEnergyStore()
  {
    super(0, 32, 1, 2, 0, 1, MaxPowerIn, 1, MaxPowerStored, module_Core.APSBlockTypes.Machine);
    if (BuildcraftAPS.DEV == true)
    this.PowerLevel = 1000000;
    else
    this.PowerLevel = 0;
    this.PowerIn = 0.0F;
    this.PowerOut = 0.0F;
    this.UserMaxPowerIn = MaxPowerIn;
    this.UserMaxPowerOut = MaxPowerOut;
    this.hasGUI = true;
    this.GuiID = 125;
  }
  

  public void updateEntity()
  {
    super.updateEntity();
    this.IsDraining = this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);
    this.PowerIn = 0.0F;
    this.PowerOut = 0.0F;
     
    if (this.IsDraining)
    {
      this.PowerOutDirection = getPoweredNeighbour();
      
      if (this.PowerOutDirection != null)
      {
        Position pos = new Position(this.xCoord, this.yCoord, this.zCoord, this.PowerOutDirection);
        
        pos.moveForwards(1.0D);
        
        TileEntity tile = this.worldObj.getBlockTileEntity((int)pos.x, (int)pos.y, (int)pos.z);
        
        IPowerReceptor receptor = (IPowerReceptor)tile;
        float extracted;
        if (this.PowerLevel > this.UserMaxPowerOut) {
          extracted = this.UserMaxPowerOut;
        } else {
          extracted = this.PowerLevel;
        }
        if (extracted > 0.0F)
        {
          receptor.getPowerProvider().receiveEnergy(extracted, this.PowerOutDirection);
          this.PowerLevel = ((int)(this.PowerLevel - extracted));
          this.PowerOut = extracted;
        }
      }
    }
    else
    {
      this.PowerIn = this.powerProvider.useEnergy(1.0F, this.UserMaxPowerIn, true);
      this.PowerLevel = ((int)(this.PowerLevel + this.PowerIn));
     }
    
    if (this.PowerLevel > MaxPowerStored) {
      this.PowerLevel = MaxPowerStored;
    } else if (this.PowerLevel < 0) {
      this.PowerLevel = 0;
    } 
    sendNetworkUpdate();
  }
  
  public int powerRequest()
  {
    if (this.IsDraining) {
      return 0;
    }
    
    int free = MaxPowerStored - this.PowerLevel;
    if (free >= MaxPowerIn) return (int) this.UserMaxPowerIn;
    return free;
  }
  

  public void readFromNBT(NBTTagCompound nbttagcompound)
  {
    super.readFromNBT(nbttagcompound);
    
    this.PowerLevel = nbttagcompound.getInteger("powerlevel");
    this.UserMaxPowerIn = nbttagcompound.getFloat("userpowerin");
    this.UserMaxPowerOut = nbttagcompound.getFloat("userpowerout");
  }
  

  public void writeToNBT(NBTTagCompound nbttagcompound)
  {
    super.writeToNBT(nbttagcompound);
    
    nbttagcompound.setInteger("powerlevel", this.PowerLevel);
    nbttagcompound.setFloat("userpowerin", this.UserMaxPowerIn);
    nbttagcompound.setFloat("userpowerout", this.UserMaxPowerOut);
  }
  
  public boolean isDraining() { return this.IsDraining; }
  
  public float getScaledPower(boolean InputOutput, float MaxLevel)
  {
    if (InputOutput) {
      return this.PowerOut / MaxPowerOut * MaxLevel;
    }
    return this.PowerIn / MaxPowerIn * MaxLevel;
  }
  
  public float getScaledPowerMax(boolean InputOutput, float MaxLevel)
  {
    if (InputOutput) {
      return this.UserMaxPowerOut / MaxPowerOut * MaxLevel;
    }
    return this.UserMaxPowerIn / MaxPowerIn * MaxLevel;
  }
  
  public float getScaledPowerStored(int MaxLevel) { return ((int)this.PowerLevel * MaxLevel) / MaxPowerStored ; }
  

  public void kill() {}
  
  public InventoryAPS getInventory()
  {
    return null;
  }
  
    
 
}
