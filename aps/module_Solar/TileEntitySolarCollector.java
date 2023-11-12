package aps.module_Solar;

import java.util.LinkedList;

import aps.module_Core.TileEntityAPS;
import aps.module_Core.module_Core;
import buildcraft.BuildCraftCore;
import buildcraft.api.core.Position;
import buildcraft.api.gates.IAction;
import buildcraft.api.gates.IActionReceptor;
import buildcraft.api.gates.ITriggerProvider;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.transport.IPipe;
import buildcraft.core.IMachine;
import buildcraft.core.network.PacketPayload;
import buildcraft.core.network.PacketUpdate;
import buildcraft.core.triggers.ActionMachineControl;
import buildcraft.energy.PneumaticPowerProvider;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEntitySolarCollector
  extends TileEntityAPS
  implements IActionReceptor, ITriggerProvider, IMachine
{
  APSSolarManager Manager;
  int powerRefreshCounter = 0;
  int powerRefreshDelay = 90;
  
  boolean Active = true;
  boolean lastActiveState;
  
  public TileEntitySolarCollector()
  {
    super(64, 64, 66, module_Core.APSBlockTypes.Energy);
    this.Manager = new APSSolarManager();
  }
  

  public void updateEntity()
  {
    if (this.worldObj.isRemote)
    {
      if (this.worldObj.getWorldTime() % 50L == 0L) {
        if (this.Active) {
          setTexture(0, 65);setTexture(1, 65);
        }
        else {
          setTexture(0, 64);setTexture(1, 64);
        }
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
      }
      return;
    }
    

    if (this.worldObj.getWorldTime() % 50L == 0L) {
      sendNetworkUpdate();
      this.Manager.worldSetup(this.worldObj, this);
    }
    
    this.Active = ((!this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)) && (this.lastMode != ActionMachineControl.Mode.Off) && (this.worldObj.isDaytime()));
    
    if ((!this.Active) || (this.Manager == null)) { return;
    }
    float extracted = 0.0F;
    
    extracted = this.Manager.getPowerOutput();
    ForgeDirection PowerOutDirection = getPoweredNeighbour();
    
    if (PowerOutDirection != null)
    {
      Position pos = new Position(this.xCoord, this.yCoord, this.zCoord, PowerOutDirection);
      
      pos.moveForwards(1.0D);
      
      IPowerReceptor receptor = (IPowerReceptor)this.worldObj.getBlockTileEntity((int)pos.x, (int)pos.y, (int)pos.z);
      
      if (extracted > 0.0F)
      {
        if (receptor != null) {
          receptor.getPowerProvider().receiveEnergy(extracted, PowerOutDirection);
        }
      }
    }
  }
  


  public float[] getBeamFocus(float X, float Z)
  {
    X -= this.xCoord;Z -= this.zCoord;
    if (X >= 0.0F)
    {
      if (Math.abs(X) >= Math.abs(Z))
        return new float[] { this.xCoord + 1.0F, this.yCoord + 0.5F, this.zCoord + 0.5F };
      if (Z >= 0.0F) {
        return new float[] { this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 1.0F };
      }
      return new float[] { this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 0.0F };
    }
    

    if (Math.abs(X) >= Math.abs(Z))
      return new float[] { this.xCoord + 0.0F, this.yCoord + 0.5F, this.zCoord + 0.5F };
    if (Z >= 0.0F) {
      return new float[] { this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 1.0F };
    }
    return new float[] { this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 0.0F };
  }
  

  protected ForgeDirection getPoweredNeighbour()
  {
    ForgeDirection o = ForgeDirection.values()[1];
    if (isPoweredTile(this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord))) return ForgeDirection.values()[5];
    if (isPoweredTile(this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord))) { return ForgeDirection.values()[4];
    }
    if (isPoweredTile(this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord - 1, this.zCoord))) { return ForgeDirection.values()[0];
    }
    if (isPoweredTile(this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1))) return ForgeDirection.values()[3];
    if (isPoweredTile(this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1))) { return ForgeDirection.values()[2];
    }
    return null;
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
  

  public void kill()
  {
    this.Manager.kill();
    this.Manager = null;
  }
  
  public boolean isActive() {
    return this.Active;
  }
  
  public boolean manageLiquids() { return false; }
  
  public boolean manageSolids() {
    return false;
  }
  
  public boolean allowActions() { return true; }
  
  ActionMachineControl.Mode lastMode = ActionMachineControl.Mode.Unknown;
  
  public void actionActivated(IAction action) {
    if (action == BuildCraftCore.actionOn) {
      this.lastMode = ActionMachineControl.Mode.On;
    } else if (action == BuildCraftCore.actionOff) {
      this.lastMode = ActionMachineControl.Mode.Off;
    } else if (action == BuildCraftCore.actionLoop) {
      this.lastMode = ActionMachineControl.Mode.Loop;
    }
  }
  
  public LinkedList getPipeTriggers(IPipe pipe) {
    return null;
  }
  
  public LinkedList getNeighborTriggers(Block block, TileEntity tile) { return null; }
  


  public PacketPayload getPacketPayload()
  {
    PacketPayload payload = new PacketPayload(1, 0, 0);
    payload.intPayload[0] = (this.Active ? 1 : 0);
    
    return payload;
  }
  
  public void handleUpdatePacket(PacketUpdate packet)
  {
    this.Active = (packet.payload.intPayload[0] == 1);
  }
}
