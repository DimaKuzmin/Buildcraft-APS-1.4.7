package aps;

import java.io.File;
import java.util.LinkedList;
import java.util.logging.Logger;

import aps.module_Core.module_Core;
import aps.module_Core.proxy.APSPacketHandler;
import aps.module_Fusion.module_Fusion;
import aps.module_Machines.module_Machines;
import aps.module_Solar.module_Solar;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.Configuration;




@Mod(name="Advanced Power Systems", version="0.2.6.8", modid="BuildcraftAPS", dependencies="required-after:BuildCraft|Transport")
@NetworkMod(channels={"APS"}, packetHandler=APSPacketHandler.class, clientSideRequired=true, serverSideRequired=true)
public class BuildcraftAPS
{
  public static String imageFilesRoot = "/aps/gfx/";
  public static final Logger log = Logger.getLogger("Advanced Power Systems");
  public static Configuration Conf;
  public static boolean clientSide = false;
  
  public static int defItemStartID = 7200;
  public static int defBlockStartID = 1200;
  
  LinkedList<module> modules = new LinkedList();
  @Mod.Instance("BuildcraftAPS")
  public static BuildcraftAPS instance;
  
  public static boolean DEV = false;
 
  
  public String getPriorities()
  {
    return "after:Forestry;after:Buildcraft|Transport;after:IC2";
  }
  
  @Mod.PreInit
  public void preInit(FMLPreInitializationEvent evt)
  {
    Conf = new Configuration(new File(evt.getModConfigurationDirectory(), "buildcraft/APS.cfg"));
    log.setParent(FMLLog.getLogger());
    log.info("PreInit");
    load();
    preinitModules();
  }
  

  @Mod.Init
  public void initialize(FMLInitializationEvent env)
  {
    initializeModules();
    log.info("Init");

    if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
      ClientInit();
    }
  }
  
  @Mod.PostInit
  public void postInit(FMLPostInitializationEvent env)
  {
	log.info("PostInit");
    postInitalizeModules();
  }
  
  public void ClientInit()
  {
    clientSide = true;
    for (module M : this.modules) {
      M.clientInit();
    }
  }
  









  public void preinitModules()
  {
    for (module M : this.modules) {
      M.preInit();
    }
    Conf.save();
  }
  


  public void initializeModules()
  {
    for (module M : this.modules)
    {
      M.initialize();
    }
  }
  

  public void postInitalizeModules()
  {
    for (module M : this.modules) {
      M.postInit();
    }
  }
  


  public void load()
  {
    Conf.load();
 
    if ("true".equals(Conf.get("Modules", "Machines", true).value)) {
      this.modules.add(new module_Machines());
    }
    if ("true".equals(Conf.get("Modules", "Fusion", true).value)) {
      this.modules.add(new module_Fusion());
    }
    if ("true".equals(Conf.get("Modules", "Solar", true).value)) {
      this.modules.add(new module_Solar());
    }
    
    this.modules.addFirst(new module_Core());
    Conf.save();
  }
}
