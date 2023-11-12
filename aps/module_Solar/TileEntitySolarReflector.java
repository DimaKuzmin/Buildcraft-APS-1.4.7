package aps.module_Solar;

import aps.BuildcraftAPS;
import aps.module_Core.TileEntityAPS;
import aps.module_Core.module_Core;






public class TileEntitySolarReflector extends TileEntityAPS
{
  boolean isConnected = false;
  APSSolarManager manager = null;
  
  boolean Setup = false;
  float BiomePowerMult = 1.0F;
  

  String tex = BuildcraftAPS.imageFilesRoot + "SolarLaser.png";
  float[] beamFocus = new float[3];
  



















  public TileEntitySolarReflector()
  {
    super(0, 67, 66, module_Core.APSBlockTypes.Energy);
  }
  
  void setup()
  {
    if (this.worldObj.isRemote) { return;
    }
    this.BiomePowerMult = APSSolarManager.getBiomeLightMult(this.worldObj.getWorldChunkManager().getBiomeGenAt(this.xCoord, this.zCoord));
    this.manager = module_Solar.controller.getNearestSolarManager(this);
    assignManager(this.manager);
    module_Solar.controller.addUnlinkedReflector(this);
    this.Setup = true;
  }
  
  public void updateEntity()
  {
    if (!this.Setup) {
      setup();
    }
    
    if (this.worldObj.isRemote) { return;
    }
    if (this.manager == null)
    {
      this.manager = module_Solar.controller.getNearestSolarManager(this);
      module_Solar.controller.addUnlinkedReflector(this);
      return;
    }
    

    if ((getIsExposed()) && (this.worldObj.isDaytime()) && (this.manager.collector.Active)) {
      this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    } else {
      this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }
  }
  
  public void assignManager(APSSolarManager Manager)
  {
    this.manager = Manager;
    if (Manager != null)
    {
      this.manager.addReflector(this);
      this.beamFocus = this.manager.collector.getBeamFocus(this.xCoord, this.zCoord);
    }
  }
  
  public boolean getIsExposed() { return this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord); }
  
  public float getLightAmount()
  {
    if (!getIsExposed())
      return 0.0F;
    return this.BiomePowerMult;
  }
  

  public void kill()
  {
    if (this.manager != null)
    {
      this.manager.remReflector(this);
    }
  }
}
