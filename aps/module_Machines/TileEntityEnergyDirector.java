package aps.module_Machines;

import java.util.ArrayList;
import java.util.List;

import aps.module_Core.TileEntityAPSPowered;
import aps.module_Core.module_Core;
import buildcraft.api.core.Position;
import buildcraft.api.gates.IAction;
import buildcraft.api.gates.IActionReceptor;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.core.network.PacketPayload;
import buildcraft.core.network.PacketUpdate;
import buildcraft.core.network.TileNetworkData;
import buildcraft.core.network.TilePacketWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityEnergyDirector
  extends TileEntityAPSPowered
  implements IPowerReceptor, IActionReceptor
{
  static int MaxPowerIn = 750;
  static int MaxPowerOut = 750;
  
  int lastSideViewed = 0;
  
  float PowerLevel;
  
  float PowerDrain;
  
  @TileNetworkData
  float[][] PowerData;
  
  boolean[] faceSwitch;
  String[] text = new String[3];
  
  boolean routeMode = false;
  int defaultRoute = -1;
  




  public static TilePacketWrapper updateMessageWrapper = new TilePacketWrapper(UpdateMessage.class);
  
  public TileEntityEnergyDirector()
  {
    super(0, 48, 1, 1, 0, 1, MaxPowerIn, 1, 1000, module_Core.APSBlockTypes.Machine);
    this.hasGUI = true;
    this.GuiID = 124;
    setTexture(2, 49);setTexture(3, 51);
    setTexture(4, 52);setTexture(5, 50);
    this.PowerLevel = 0.0F;
    this.PowerData = new float[6][4];
    this.faceSwitch = new boolean[4];
    for (int i = 0; i < 4; i++)
    {
      this.PowerData[0][i] = 0.0F;
      this.PowerData[1][i] = 0.0F;
      this.PowerData[2][i] = 0.0F;
      this.PowerData[3][i] = 0.0F;
      this.PowerData[4][i] = 0.0F;
      this.PowerData[5][i] = 0.0F;
      this.faceSwitch[i] = true;
    }
    setText("[b]Startup: Complete");
  }
  

  public void updateEntity()
  {
    if (this.worldObj.isRemote) { return;
    }
    
    this.PowerLevel = this.powerProvider.useEnergy(1.0F, 1000.0F, true);
    
    if (!this.routeMode) {
      splitPower();
    } else {
      routePower();
    }
    

    sendNetworkUpdate();
  }
  


  public void splitPower() {}
  

  public void routePower()
  {
    if (this.defaultRoute == -1) { return;
    }
    if (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)) {
      sendOutPower(this.defaultRoute, this.PowerLevel);
      return;
    }
    for (int priority = 0; priority < 3; priority++)
    {
      if (this.PowerLevel == 0.0F) { return;
      }
      
      for (Integer side : getConnectedSides()) {
          if ((side.intValue() != this.defaultRoute) && (getPriority(side.intValue()) == priority) && (this.faceSwitch[side.intValue()])) {
            sendOutPower(side.intValue(), this.PowerData[1][side.intValue()]);
          }
        }
    }
    
    if (this.PowerLevel > 0.0F) {
      sendOutPower(this.defaultRoute, this.PowerLevel);
    }
  }
  


  public void sendOutPower(int direction, float amount)
  {
    Position pos = new Position(this.xCoord, this.yCoord, this.zCoord, ForgeDirection.VALID_DIRECTIONS[translateToForgeDirection(direction)]);
    pos.moveForwards(1.0D);
    
    TileEntity tile = this.worldObj.getBlockTileEntity((int)pos.x, (int)pos.y, (int)pos.z);
    
    if (isPoweredTile(tile)) {
      IPowerReceptor receptor = (IPowerReceptor)tile;
      


      if (receptor.getPowerProvider().getMaxEnergyStored() >= receptor.getPowerProvider().getEnergyStored() + amount)
      {
        receptor.getPowerProvider().receiveEnergy(amount, pos.orientation.getOpposite());
        
        if (amount > this.PowerLevel) {
          amount = this.PowerLevel;
          this.PowerLevel = 0.0F;
        } else {
          this.PowerLevel -= amount;
        }
      }
    }
  }
  
  public void setMinimumPowerValue(int Side, float MinimumPower)
  {
    if ((Side < 0) || (Side > 3)) return;
    if ((MinimumPower < 0.0F) || (MinimumPower > MaxPowerIn)) return;
    this.PowerData[1][Side] = MinimumPower;
  }
  
  public void setGateValue(int Side, float GateValue)
  {
    if ((Side < 0) || (Side > 3)) return;
    if ((GateValue < 0.0F) || (GateValue > 1.0F)) return;
    this.PowerData[2][Side] = GateValue;
  }
  
  public void setPriority(int Side, int Priority)
  {
    if ((Side < 0) || (Side > 3) || (!this.routeMode)) return;
    if ((Priority < 0) || (Priority > 3)) { return;
    }
    if (Priority == 3) {
      if (this.defaultRoute == -1) {
        this.defaultRoute = Side;
      }
    }
    else if (this.defaultRoute == Side) {
      this.defaultRoute = -1;
    }
    
    this.PowerData[0][Side] = Priority;
  }
  
  public void setMode(boolean mode)
  {
    if (mode != this.routeMode) {
      this.routeMode = mode;
      if (mode == true) {
        setText("Routing mode");
        if (this.defaultRoute == -1)
        {
          setText("[r]No default route");
          setText("[r]Redirector disabled");
        }
      } else {
        setText("Split mode");
        setText("[r]Not implemented");
      }
    }
  }
  
  public void setText(String string)
  {
    for (int i = 0; i < 2; i++) {
      if ((this.text[i] == null) || (this.text[i].equals(""))) {
        this.text[i] = string;
        return;
      }
    }
    


    for (int i = 0; i < 2; i++)
    {
      this.text[i] = this.text[(i + 1)];
    }
    this.text[2] = string;
  }
  
  public float getScaledMinimumPowerValue(int Side, int MaxLevel)
  {
    if ((Side < 0) || (Side > 3)) return 0.0F;
    float value = this.PowerData[1][Side] / MaxPowerIn;
    return value * MaxLevel;
  }
  
  public float getScaledGateValue(int Side, int MaxLevel)
  {
    if ((Side < 0) || (Side > 3)) return 0.0F;
    return this.PowerData[2][Side] * MaxLevel;
  }
  
  public float getScaledLastPowerOutput(int Side, int MaxLevel)
  {
    if ((Side < 0) || (Side > 3)) return 0.0F;
    return this.PowerData[3][Side] / MaxPowerOut * MaxLevel;
  }
  
  public float getScaledLastMinimumPowerOut(int Side, int MaxLevel)
  {
    if ((Side < 0) || (Side > 3)) return 0.0F;
    return this.PowerData[4][Side] / MaxPowerOut * MaxLevel;
  }
  
  public float getScaledLastGatePowerOut(int Side, int MaxLevel)
  {
    if ((Side < 0) || (Side > 3)) return 0.0F;
    return this.PowerData[5][Side] / MaxPowerOut * MaxLevel;
  }
  
  public int getPriority(int Side)
  {
    if ((Side < 0) || (Side > 3)) return 0;
    return (int)this.PowerData[0][Side];
  }
  
  public int translateToForgeDirection(int blockSide)
  {
    switch (blockSide) {
    case 0: 
      return 2;
    case 1: 
      return 5;
    case 2: 
      return 3;
    case 3: 
      return 4;
    }
    
    return -1;
  }
  
  public List<Integer> getConnectedSides()
  {
    List result = new ArrayList();
    
    if (isPoweredTile(this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord))) result.add(Integer.valueOf(1));
    if (isPoweredTile(this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord))) result.add(Integer.valueOf(3));
    if (isPoweredTile(this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1))) result.add(Integer.valueOf(2));
    if (isPoweredTile(this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1))) { result.add(Integer.valueOf(0));
    }
    return result;
  }
  
  public int getTotalOutput()
  {
    int total = 0;
    for (int i = 0; i < 4; i++)
    {
      if (i != this.defaultRoute)
        total = (int)(total + this.PowerData[1][i]);
    }
    return total;
  }
  


  public int powerRequest()
  {
    if (getConnectedSides().size() == 0) {
      return 0;
    }
    if (getTotalOutput() == 0) {
      return 0;
    }
    if (this.routeMode)
    {
      if (this.defaultRoute == -1) {
        return 0;
      }
      if (!getConnectedSides().contains(Integer.valueOf(this.defaultRoute))) {
        return 0;
      }
      Position pos = new Position(this.xCoord + ForgeDirection.VALID_DIRECTIONS[translateToForgeDirection(this.defaultRoute)].offsetX, this.yCoord + ForgeDirection.VALID_DIRECTIONS[translateToForgeDirection(this.defaultRoute)].offsetY, this.zCoord + ForgeDirection.VALID_DIRECTIONS[translateToForgeDirection(this.defaultRoute)].offsetZ);
      


      TileEntity tile = this.worldObj.getBlockTileEntity((int)pos.x, (int)pos.y, (int)pos.z);
      
      if ((tile instanceof IPowerReceptor)) {
        IPowerProvider provider = ((IPowerReceptor)tile).getPowerProvider();
        
        if (provider.getEnergyStored() + MaxPowerIn >= provider.getMaxEnergyStored()) {
          return 0;
        }
      }
    }
    else {
      return 0;
    }
    
    return MaxPowerIn;
  }
  
  public void readFromNBT(NBTTagCompound nbttagcompound)
  {
    super.readFromNBT(nbttagcompound);
    
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 4; j++)
      {
        this.PowerData[i][j] = nbttagcompound.getFloat("powerdata" + i + j);
      }
    }
    this.routeMode = nbttagcompound.getBoolean("routemode");
    this.defaultRoute = nbttagcompound.getInteger("defaultroute");
  }
  
  public void writeToNBT(NBTTagCompound nbttagcompound) {
    super.writeToNBT(nbttagcompound);
    
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 4; j++)
      {
        nbttagcompound.setFloat("powerdata" + i + j, this.PowerData[i][j]);
      }
    }
    nbttagcompound.setBoolean("routemode", this.routeMode);
    nbttagcompound.setInteger("defaultroute", this.defaultRoute);
  }
  














  public void kill() {}
  













  public PacketPayload getPacketPayload()
  {
    PacketPayload payload = new PacketPayload(2, 16, 3);
    

    for (int side = 0; side < 4; side++) {
      for (int prop = 0; prop < 4; prop++)
      {
        payload.floatPayload[(prop + side * 3)] = this.PowerData[prop][side];
      }
    }
    payload.intPayload[0] = (this.routeMode ? 1 : 0);
    
    for (int index = 0; index < 3; index++) {
      payload.stringPayload[index] = (this.text[index] != null ? this.text[index] : "");
    }
    payload.intPayload[1] = this.defaultRoute;
    
    return payload;
  }
  
  public void handleUpdatePacket(PacketUpdate packet)
  {
    for (int side = 0; side < 4; side++) {
      for (int prop = 0; prop < 4; prop++)
      {
        this.PowerData[prop][side] = packet.payload.floatPayload[(prop + side * 3)]; }
    }
    this.routeMode = (packet.payload.intPayload[0] == 1);
    
    for (int index = 0; index < 3; index++) {
      this.text[index] = packet.payload.stringPayload[index];
    }
    this.defaultRoute = packet.payload.intPayload[1];
  }
  
  public void handleUpdateMessage(UpdateMessage message)
  {
    setGateValue(message.Side, message.gateValue);
    setMinimumPowerValue(message.Side, message.minValue);
    setPriority(message.Side, (int)message.priority);
    setMode(message.mode);
    
    sendNetworkUpdate();
  }
  



















  public void actionActivated(IAction action)
  {
    if (action == module_Machines.directorEnableNorth) {
      this.faceSwitch[0] = true;
    } else if (action == module_Machines.directorDisableNorth) {
      this.faceSwitch[0] = false;
    }
    
    if (action == module_Machines.directorEnableSouth) {
      this.faceSwitch[2] = true;
    } else if (action == module_Machines.directorDisableSouth) {
      this.faceSwitch[2] = false;
    }
    
    if (action == module_Machines.directorEnableEast) {
      this.faceSwitch[1] = true;
    } else if (action == module_Machines.directorDisableEast) {
      this.faceSwitch[1] = false;
    }
    
    if (action == module_Machines.directorEnableWest) {
      this.faceSwitch[3] = true;
    } else if (action == module_Machines.directorDisableWest) {
      this.faceSwitch[3] = false;
    }
  }
  
  public static class UpdateMessage
  {
    @TileNetworkData
    public int Side;
    @TileNetworkData
    public float minValue;
    @TileNetworkData
    public float gateValue;
    @TileNetworkData
    public float priority;
    @TileNetworkData
    public boolean mode;
  }
}
