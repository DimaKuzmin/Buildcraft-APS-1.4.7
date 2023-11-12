package aps.module_Solar;

import java.util.LinkedList;

import net.minecraft.tileentity.TileEntity;

public class APSSolarManagerController
{
  public LinkedList<APSSolarManager> SolarManagers = new LinkedList<APSSolarManager>();
  public void addSolarManager(APSSolarManager Manager) { this.SolarManagers.add(Manager);checkUnlinkedReflectors(); }
  public void remSolarManager(APSSolarManager Manager) { this.SolarManagers.remove(Manager); }
  
  public java.util.LinkedList UnlinkedReflectors = new java.util.LinkedList();
  void addUnlinkedReflector(TileEntitySolarReflector Reflector) { if (!this.UnlinkedReflectors.contains(Reflector)) this.UnlinkedReflectors.add(Reflector); }
  void remUnlinkedReflector(TileEntitySolarReflector Reflector) { this.UnlinkedReflectors.remove(Reflector); }
  
  void checkUnlinkedReflectors() {
    TileEntitySolarReflector[] UnlinkedRefs = (TileEntitySolarReflector[])this.UnlinkedReflectors.toArray(new TileEntitySolarReflector[0]);
    for (TileEntitySolarReflector Reflector : UnlinkedRefs)
    {
      APSSolarManager Manager = getNearestSolarManager(Reflector);
      if (Manager != null)
      {
        Reflector.assignManager(Manager);
        remUnlinkedReflector(Reflector);
      }
    }
  }
  
  public APSSolarManager getNearestSolarManager(TileEntitySolarReflector Reflector)
  {
    APSSolarManager Nearest = null;
    double Distance = 1000000.0D;
    for (APSSolarManager Manager : this.SolarManagers)
    {
      double Dist = getDistance(Reflector, Nearest);
      if ((Nearest == null) || (Dist < Distance))
      {
        Nearest = Manager;
        Distance = Dist;
      }
    }
    
    if (Nearest != null)
    {
      TileEntitySolarCollector Col = Nearest.collector;
      if ((Reflector.xCoord >= Col.xCoord - 16) && (Reflector.xCoord <= Col.xCoord + 16) && (Reflector.yCoord >= Col.yCoord - 16) && (Reflector.yCoord <= Col.yCoord) && (Reflector.zCoord >= Col.zCoord - 16) && (Reflector.zCoord <= Col.zCoord + 16))
      {

        return Nearest;
      }
      
      return null;
    }
    


    return null;
  }
  

  double getDistance(TileEntitySolarReflector Ref, APSSolarManager Manager)
  {
    if ((Manager != null) && (Manager.collector != null)) {
      TileEntitySolarCollector Col = Manager.collector;
      return Ref.getDistanceFrom(Col.xCoord, Col.yCoord, Col.zCoord); }
    return 1000000.0D;
  }
}
