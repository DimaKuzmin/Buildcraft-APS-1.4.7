package aps;

import buildcraft.api.gates.ITriggerParameter;
import net.minecraft.tileentity.TileEntity;

public abstract class TriggerAPS extends buildcraft.api.gates.Trigger
{
  public TriggerAPS(int id)
  {
    super(id);
  }
  

  public abstract int getIndexInTexture();
  

  public abstract String getDescription();
  

  public abstract boolean hasParameter();
  

  public abstract boolean isTriggerActive(TileEntity paramTileEntity, ITriggerParameter paramITriggerParameter);
  
  public String getTextureFile()
  {
    return BuildcraftAPS.imageFilesRoot + "APSTriggerTexes.png";
  }
}
