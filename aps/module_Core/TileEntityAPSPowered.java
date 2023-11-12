package aps.module_Core;

import aps.module_Core.module_Core.APSBlockTypes;
import buildcraft.api.core.Position;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;
import buildcraft.core.IMachine;
import buildcraft.energy.PneumaticPowerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;


public abstract class TileEntityAPSPowered extends TileEntityAPS implements IPowerReceptor, IMachine
{
  int PoweredTex;
  protected ForgeDirection PowerOutDirection;
  protected IPowerProvider powerProvider;
  
  public TileEntityAPSPowered(int BottomTex, int TopTex, int DefSideTex, int PoweredSideTex, int latency, int minPower, int maxPower, int activationEnergy, int maxStoredPower, APSBlockTypes type)
  {
    super(BottomTex, TopTex, DefSideTex, type);
    this.PoweredTex = PoweredSideTex;
    this.powerProvider = PowerFramework.currentFramework.createPowerProvider();
    this.powerProvider.configure(latency, minPower, maxPower, activationEnergy, maxStoredPower);
  }
  

  public void updateEntity()
  {
    resetAllTextures();
    this.powerProvider.update(this);
    if (this.PowerOutDirection != null) setTexture(this.PowerOutDirection.ordinal(), this.PoweredTex);
    for (ForgeDirection O : ForgeDirection.VALID_DIRECTIONS) if (this.powerProvider.isPowerSource(O)) setTexture(O.ordinal(), this.PoweredTex);
  }
  
  protected boolean isPoweredDirection(int Direction)
  {
    Position pos = new Position(this.xCoord, this.yCoord, this.zCoord, this.PowerOutDirection);
    
    pos.moveForwards(1.0D);
    
    return isPoweredTile(this.worldObj.getBlockTileEntity((int)pos.x, (int)pos.y, (int)pos.z));
  }
  
  protected boolean isPoweredTile(TileEntity tile)
  {
    if ((tile instanceof IPowerReceptor))
    {
      IPowerProvider prov = ((IPowerReceptor)tile).getPowerProvider();
      return (prov != null) && (prov.getClass().equals(PneumaticPowerProvider.class));
    }
    return false;
  }
  
  protected ForgeDirection getPoweredNeighbour()
  {
    ForgeDirection o = ForgeDirection.values()[1];
    if (isPoweredTile(this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord))) return ForgeDirection.values()[5];
    if (isPoweredTile(this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord))) { return ForgeDirection.values()[4];
    }
    if (isPoweredTile(this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1))) return ForgeDirection.values()[3];
    if (isPoweredTile(this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1))) { return ForgeDirection.values()[2];
    }
    return null;
  }
  
  public void doWork() {}
  
  public IPowerProvider getPowerProvider() { return this.powerProvider; }
  
  public void setPowerProvider(IPowerProvider Provider) { this.powerProvider = Provider; }
  
  public boolean isActive() {
    return false;
  }
  
  public boolean manageLiquids() {
    return false;
  }
  
  public boolean manageSolids() {
    return false;
  }
  
  public boolean allowActions() {
    return false;
  }
}
