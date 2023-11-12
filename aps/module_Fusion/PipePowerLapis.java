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
import net.minecraft.block.BlockCarrot;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
















public class PipePowerLapis extends Pipe implements IPowerReceptor
{
  private IPowerProvider powerProvider;
  private int baseTexture = module_Fusion.TextureIdPipePowerLapis;
  private int plainTexture = module_Fusion.TextureIdPipePowerLapis;
  private int nextTexture = this.baseTexture;
  
  private float MovingAverage = 0.0F;
  private int MovingAverageNodes = 160;
  
  public PipePowerLapis(int itemID) {
    super(new PipeTransportPower(), new PipeLogicWood(), itemID);
    
    this.powerProvider = PowerFramework.currentFramework.createPowerProvider();
    this.powerProvider.configure(0, 1, 500, 1, 500);
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
  
  public String getName()
  {
    return "Advanced Power Output Pipe";
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
          PipeTransportPower pow = (PipeTransportPower)((TileGenericPipe)tile).pipe.transport;
          
          float energyToRemove = 0.0F;
          
          this.MovingAverage -= this.MovingAverage / this.MovingAverageNodes;
          this.MovingAverage += this.powerProvider.getEnergyStored();
          
          energyToRemove = this.MovingAverage / this.MovingAverageNodes;
          
          float energyUsed = this.powerProvider.useEnergy(1.0F, energyToRemove, true);
          
          pow.receiveEnergy(o.getOpposite(), energyUsed);
          
          if (this.worldObj.isRemote) return;
          int tmp183_180 = o.ordinal(); short[] tmp183_175 = ((PipeTransportPower)this.transport).displayPower;tmp183_175[tmp183_180] = ((short)(int)(tmp183_175[tmp183_180] + energyUsed));
        }
      }
    }
  }

  public int powerRequest()
  {
    return getPowerProvider().getMaxEnergyReceived();
  }
 
}
