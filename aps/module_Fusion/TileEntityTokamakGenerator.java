package aps.module_Fusion;

import aps.BuildcraftAPS;
import aps.module_Core.InventoryAPS;
import aps.module_Core.TileEntityAPSPowered;
import aps.module_Core.module_Core;
import buildcraft.BuildCraftCore;
import buildcraft.api.core.Position;
import buildcraft.api.gates.IAction;
import buildcraft.api.gates.IActionReceptor;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.core.IMachine;
import buildcraft.core.network.PacketPayload;
import buildcraft.core.network.PacketUpdate;
import buildcraft.core.triggers.ActionMachineControl;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

 
public class TileEntityTokamakGenerator
  extends TileEntityAPSPowered
  implements IPowerReceptor, IInventory, ITankContainer, IMachine, IActionReceptor
{
  InventoryAPS Inventory;
  int WaterID = Block.waterStill.blockID;
  int FuelCapacity = 10000;
  int FuelLevel = 0;
  int FuelType = 0;
  int HeavyWaterMult = 10;
  int HeavyWaterID = module_Fusion.heavyWater.itemID;
  
  static int EnergyToHeatingScalar = 11;
  
  int LiquidSideTex = 18;
  
  float FusionWaterUse = 0.1F;
  int FusionBurnTime = 1;
  int FusionMaxHeatGen = 500;
  int FusionMaxEnergyGen = 500;
  
  int TokamakMaxCoolRate = module_Fusion.CoolRate;
  int TokamakMaxTemp = module_Fusion.MaxTemp;
  float TokamakFusionFraction = module_Fusion.FusionFraction;
  int TokamakFusionTemp = (int)(this.TokamakMaxTemp * this.TokamakFusionFraction);
  float FusionStartTemp = this.TokamakFusionTemp + this.TokamakFusionTemp * 0.01F;
  static int TokamakMaxEnergyRec = 500;
  
  int TokamakIdlingBurnDelay = 0;
  
  int BurnTimeRemaining;
  
  ILiquidTank tank;
  
  int LiquidID;
  
  boolean Idling;
  
  int TokamakTemp;
  float PowerIn;
  float PowerOut;
  
  public TileEntityTokamakGenerator()
  {
    super(0, 16, 19, 17, 0, 1, TokamakMaxEnergyRec, 1, module_Fusion.MaxTemp / EnergyToHeatingScalar, module_Core.APSBlockTypes.Energy);
    this.hasGUI = true;
    this.GuiID = 127;
    this.Inventory = new InventoryAPS(1, "tokamakinput");
    this.TokamakTemp = 0;
    this.BurnTimeRemaining = 0;
    this.tank = new LiquidTank(this.FuelCapacity);
  }
 
  public void updateEntity()
  {
    if (this.worldObj.isRemote) { return;
    }
    this.PowerIn = 0.0F;
    this.PowerOut = 0.0F;
    this.Idling = ((this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)) || (this.lastMode == ActionMachineControl.Mode.Off));
    
    ItemStack itemInInventory = this.Inventory.getStackInSlot(0);
    
    if (itemInInventory != null)
    {
      LiquidStack liquidId = LiquidContainerRegistry.getLiquidForFilledItem(itemInInventory);
      if (liquidId != null)
      {
        if (liquidId.itemID == this.WaterID)
        {
          if (fill(ForgeDirection.UNKNOWN, liquidId, false) == 1000)
          {
            fill(ForgeDirection.UNKNOWN, liquidId, true);
            
            if (itemInInventory.getItem() == Item.bucketWater) {
              this.Inventory.setInventorySlotContents(0, new ItemStack(Item.bucketEmpty, 1));
            } else {
              this.Inventory.decrStackSize(0, 1);
            }
          }
        } else if (liquidId.itemID == this.HeavyWaterID)
        {
          if (fill(ForgeDirection.UNKNOWN, liquidId, false) > 0)
          {
            fill(ForgeDirection.UNKNOWN, liquidId, true);
            

            this.Inventory.decrStackSize(0, 1);
          }
        }
      }
    }
    
    if (this.TokamakTemp >= this.FusionStartTemp)
    {
      if (this.BurnTimeRemaining > 0)
      {
        Burn();
        if (!this.Idling)
        {
          this.BurnTimeRemaining -= 1;
          this.TokamakIdlingBurnDelay = 0;
        }
        else if (this.TokamakIdlingBurnDelay == 0)
        {
          this.BurnTimeRemaining -= 1;
          this.TokamakIdlingBurnDelay = 10;
        }
        else {
          this.TokamakIdlingBurnDelay -= 1;
        }
        
      }
      else if ((this.tank.getLiquid() != null) && (this.tank.getLiquid().amount > 1000.0F * this.FusionWaterUse))
      {
        if (this.tank.getLiquid().itemID == this.HeavyWaterID) {
          this.tank.drain((int)(1000 / this.HeavyWaterMult * this.FusionWaterUse), true);
        } else {
          this.tank.drain((int)(1000.0F * this.FusionWaterUse), true);
        }
        
        this.BurnTimeRemaining = this.FusionBurnTime;
        Burn();
      }
      
      if (this.Idling)
      {
        this.PowerIn = this.powerProvider.useEnergy(1.0F, (int)(TokamakMaxEnergyRec / 4.0F), true);
        
        this.TokamakTemp = ((int)(this.TokamakTemp + this.PowerIn * EnergyToHeatingScalar));
      }
    }
    else
    {
      this.PowerIn = this.powerProvider.useEnergy(1.0F, TokamakMaxEnergyRec, true);
      
      this.TokamakTemp = ((int)(this.TokamakTemp + this.PowerIn * EnergyToHeatingScalar));
    }
    

    this.TokamakTemp = ((int)(this.TokamakTemp - this.TokamakMaxCoolRate * (this.TokamakTemp / this.TokamakMaxTemp)));
    
    if (this.TokamakTemp > this.TokamakMaxTemp) {
      this.TokamakTemp = this.TokamakMaxTemp;
    } else if (this.TokamakTemp < 0) {
      this.TokamakTemp = 0;
    }
    sendNetworkUpdate();
  }
  
  void Burn()
  {
    if (!this.Idling)
    {
      this.PowerOutDirection = getPoweredNeighbour();
      
      float EnergyGradient = (this.TokamakTemp - this.TokamakFusionTemp) / (this.TokamakMaxTemp - this.TokamakFusionTemp);
      
      if (this.PowerOutDirection != null)
      {
        Position pos = new Position(this.xCoord, this.yCoord, this.zCoord, this.PowerOutDirection);
        
        pos.moveForwards(1.0D);
        
        TileEntity tile = this.worldObj.getBlockTileEntity((int)pos.x, (int)pos.y, (int)pos.z);
        
        IPowerReceptor receptor = (IPowerReceptor)tile;
        
        float extracted = EnergyGradient * this.FusionMaxEnergyGen;
         
        if (extracted > 0.0F)
        {
          if (!this.Idling)
          {
            receptor.getPowerProvider().receiveEnergy(extracted, this.PowerOutDirection);
            this.PowerOut = extracted;
          }
        }
      }
      
      this.TokamakTemp = ((int)(this.TokamakTemp + (EnergyGradient * this.FusionMaxHeatGen + this.TokamakMaxCoolRate * (this.TokamakTemp / this.TokamakMaxTemp) + 1.0F)));
    }
    else
    {
      this.TokamakTemp = ((int)(this.TokamakTemp + this.TokamakMaxCoolRate * this.TokamakFusionFraction));
    }
  }
  
  public int powerRequest()
  {
    if (this.TokamakTemp > this.FusionStartTemp) {
      if ((this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)) && (this.TokamakTemp < this.TokamakMaxTemp)) {
        return (int)(TokamakMaxEnergyRec / 4.0F);
      }
      return 0;
    }
    return TokamakMaxEnergyRec;
  }
  
  public int fill(ForgeDirection from, LiquidStack liquid, boolean doFill)
  {
    if (from.ordinal() > 1)
    {
      if ((liquid.itemID == this.WaterID) || (liquid.itemID == this.HeavyWaterID)) {
        return fill(0, liquid, doFill);
      }
      return 0;
    }
    return 0;
  }

  public int fill(int tankID, LiquidStack liquid, boolean doFill)
  {
    this.LiquidID = liquid.itemID;
    return this.tank.fill(liquid, doFill);
  }

  public LiquidStack drain(ForgeDirection orientation, int maxDrain, boolean doDrain)
  {
    return drain(0, maxDrain, doDrain);
  }
  
  public LiquidStack drain(int tankID, int quantityMax, boolean doDrain)
  {
    return this.tank.drain(quantityMax, doDrain);
  }
  
  public void readFromNBT(NBTTagCompound nbttagcompound)
  {
    super.readFromNBT(nbttagcompound);
    
    int fuelID = nbttagcompound.getInteger("liquidid");
    if (fuelID > 0) {
      this.tank.fill(new LiquidStack(fuelID, nbttagcompound.getInteger("fuellevel")), true);
    }
    
    this.TokamakTemp = nbttagcompound.getInteger("temperature");
    this.BurnTimeRemaining = nbttagcompound.getInteger("burnremaining");
    if (nbttagcompound.hasKey("inputslotitem")) {
      NBTTagCompound cpt = nbttagcompound.getCompoundTag("inputslotitem");
      this.Inventory.addItemStackToInventory(ItemStack.loadItemStackFromNBT(cpt));
    }
  }
  
  public void writeToNBT(NBTTagCompound nbttagcompound)
  {
    super.writeToNBT(nbttagcompound);
    
    nbttagcompound.setInteger("liquidid", this.tank.getLiquid() != null ? this.tank.getLiquid().itemID : 0);
    nbttagcompound.setInteger("fuellevel", this.tank.getLiquid() != null ? this.tank.getLiquid().amount : 0);
    nbttagcompound.setInteger("temperature", this.TokamakTemp);
    nbttagcompound.setInteger("burnremaining", this.BurnTimeRemaining);
    
    if (this.Inventory.getStackInSlot(0) != null) {
      NBTTagCompound cpt = new NBTTagCompound();
      this.Inventory.getStackInSlot(0).writeToNBT(cpt);
      nbttagcompound.setTag("inputslotitem", cpt);
    }
  }
  
  public IInventory getInventory()
  {
    return this.Inventory;
  }
  
  public float getScaledLiquidQuantity(int MaxLevel) { return this.tank.getLiquid() != null ? this.tank.getLiquid().amount /172 : 0; }
  

  public float getScaledTemp(int MaxLevel) { return this.TokamakTemp / this.TokamakMaxTemp * MaxLevel; }
  
  public float getScaledFusionTemp(int MaxLevel) { return this.TokamakFusionTemp / this.TokamakMaxTemp * MaxLevel; }
  
  public float getScaledPower(boolean InputOutput, int MaxLevel)
  {
    if (InputOutput) {
      return this.PowerOut / this.FusionMaxEnergyGen * MaxLevel;
    }
    return this.PowerIn / TokamakMaxEnergyRec * MaxLevel;
  }
  
  boolean isFusing()
  {
    if (this.TokamakTemp > this.FusionStartTemp) {
      return true;
    }
    return false;
  }
  
  boolean isIdling() { return this.Idling; }
  
  public void kill() {}
  
  public boolean isActive()
  {
    return isFusing();
  }
  
  public boolean manageLiquids() { return true; }
  
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
    }
  }
  
  public int getSizeInventory()
  {
    return this.Inventory.getSizeInventory();
  }
  
  public ItemStack getStackInSlot(int i)
  {
    return this.Inventory.getStackInSlot(i);
  }
  
  public ItemStack decrStackSize(int i, int j)
  {
    return this.Inventory.decrStackSize(i, j);
  }
  
  public void setInventorySlotContents(int i, ItemStack itemstack)
  {
    this.Inventory.setInventorySlotContents(i, itemstack);
  }
  
  public String getInvName()
  {
    return this.Inventory.getInvName();
  }
  
  public int getInventoryStackLimit()
  {
    return this.Inventory.getInventoryStackLimit();
  }
  
  public boolean isUseableByPlayer(EntityPlayer entityplayer)
  {
    return this.Inventory.isUseableByPlayer(entityplayer);
  }
  
  public void openChest()
  {
    this.Inventory.openChest();
  }
  
  public void closeChest()
  {
    this.Inventory.closeChest();
  }
  
  public ItemStack getStackInSlotOnClosing(int var1)
  {
    return null;
  }
  
  public ILiquidTank[] getTanks(ForgeDirection direction)
  {
    return new ILiquidTank[] { this.tank };
  }
  

  public ILiquidTank getTank(ForgeDirection direction, LiquidStack liquidstack)
  {
    return this.tank;
  }
  
  public PacketPayload getPacketPayload()
  {
    PacketPayload payload = new PacketPayload(4, 2, 0);
    
    if (this.tank.getLiquid() != null) {
      payload.intPayload[0] = this.tank.getLiquid().itemID;
      payload.intPayload[1] = this.tank.getLiquid().amount;
    } else {
      payload.intPayload[0] = 0;
      payload.intPayload[1] = 0;
    }
    
    payload.intPayload[2] = (this.Idling ? 1 : 0);
    payload.intPayload[3] = this.TokamakTemp;
    
    payload.floatPayload[0] = this.PowerIn;
    payload.floatPayload[1] = this.PowerOut;
    return payload;
  }

  public void handleUpdatePacket(PacketUpdate packet)
  {
	if (packet.payload.intPayload[0] != 0 | packet.payload.intPayload[1] != 0)
    this.tank = new LiquidTank(packet.payload.intPayload[0],  packet.payload.intPayload[1], FuelCapacity);
     
    this.Idling = (packet.payload.intPayload[2] == 1);
    this.TokamakTemp = packet.payload.intPayload[3];
    
    this.PowerIn = packet.payload.floatPayload[0];
    this.PowerOut = packet.payload.floatPayload[1];
  }
}
