package aps.module_Fusion;

import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;
import buildcraft.core.DefaultProps;
import buildcraft.core.utils.Utils;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeTransportPower;
import buildcraft.transport.TileGenericPipe;
import buildcraft.transport.pipes.PipeLogicWood;
import java.io.PrintStream;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;












public class APSPipePower extends Pipe implements IPowerReceptor
{
  private IPowerProvider powerProvider;
  private int baseTexture = module_Fusion.TextureIdPipePowerLapis;
  private int plainTexture = module_Fusion.TextureIdPipePowerLapis;
  private int nextTexture = this.baseTexture;
  
  private float MovingAverage = 0.0F;
  private int MovingAverageNodes = 160;
  
  public float maxEnergy = 500.0F;
  
  public boolean clogged = false;
  
  public APSPipePower(int itemID) {
    super(new PipeTransportPower(), new PipeLogicWood(), itemID);
    this.powerProvider = PowerFramework.currentFramework.createPowerProvider();
    this.powerProvider.configure(0, 1, 10000, 1, 10000);
    this.powerProvider.configurePowerPerdition(1, 100);
  }
  




  public int getTextureIndex(ForgeDirection direction)
  {
    return 123;
  }
  


  public String getTextureFile()
  {
    return DefaultProps.TEXTURE_BLOCKS;
  }
  





  public void setPowerProvider(IPowerProvider provider)
  {
    provider = this.powerProvider;
  }
  
  public IPowerProvider getPowerProvider()
  {
    return this.powerProvider;
  }
  

  public void doWork() {}
  

  public void updateEntity()
  {
    super.updateEntity();
    

    for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS)
    {
      if (Utils.checkPipesConnections(this.container, this.container.getTile(o)))
      {
        TileEntity tile = this.container.getTile(o);
        
        if ((tile instanceof TileGenericPipe))
        {
          if (((TileGenericPipe)tile).pipe == null) {
            System.out.println("PipePowerWood.pipe was null, this used to cause a NPE crash)");
          }
          else
          {
            PipeTransportPower pow = (PipeTransportPower)((TileGenericPipe)tile).pipe.transport;
            
            float energyToRemove = 0.0F;
            
            if (this.powerProvider.getEnergyStored() > this.maxEnergy) {
              energyToRemove = this.maxEnergy;
            } else {
              energyToRemove = this.powerProvider.getEnergyStored();
            }
            if (pow.internalNextPower[o.getOpposite().ordinal()] > 6000.0D)
            {
              this.clogged = true;
            } else {
              this.clogged = false;
            }
            
            float energyUsed = this.powerProvider.useEnergy(1.0F, energyToRemove, true);
            
            pow.receiveEnergy(o.getOpposite(), energyUsed);
            
            if (this.worldObj.isRemote) return;
            int tmp227_224 = o.ordinal(); short[] tmp227_219 = ((PipeTransportPower)this.transport).displayPower;tmp227_219[tmp227_224] = ((short)(int)(tmp227_219[tmp227_224] + energyUsed));
          }
        }
      }
    }
  }
  
  public int powerRequest() {
    return getPowerProvider().getMaxEnergyReceived();
  }
  

  public void writeToNBT(NBTTagCompound nbttagcompound)
  {
    nbttagcompound.setBoolean("isclogged", this.clogged);
    super.writeToNBT(nbttagcompound);
  }
  

  public void readFromNBT(NBTTagCompound nbttagcompound)
  {
    this.clogged = nbttagcompound.getBoolean("isclogged");
    super.readFromNBT(nbttagcompound);
  }
}
