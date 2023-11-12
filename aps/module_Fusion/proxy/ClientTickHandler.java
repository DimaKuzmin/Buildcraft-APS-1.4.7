package aps.module_Fusion.proxy;

import aps.module_Fusion.GuiEnergyReader;
import aps.module_Fusion.module_Fusion;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;


public class ClientTickHandler
  implements ITickHandler
{
  public Minecraft mc;
  
  public ClientTickHandler()
  {
    this.mc = Minecraft.getMinecraft();
  }
  

  public void tickStart(EnumSet type, Object... tickData) {}
  
  public void tickEnd(EnumSet type, Object... tickData)
  {
//    if (type.contains(TickType.RENDER))
//    {
//      module_Fusion.guiEnergyReader.render();
//    }
  }
  


  public EnumSet ticks()
  {
    return EnumSet.of(TickType.RENDER);
  }
  
  public String getLabel()
  {
    return null;
  }
}
