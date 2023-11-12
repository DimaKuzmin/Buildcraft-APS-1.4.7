package aps.module_Fusion.proxy;

import cpw.mods.fml.common.SidedProxy;

public class APSFusionProxy
{
  @SidedProxy(clientSide="aps.module_Fusion.proxy.APSFusionProxyClient", serverSide="aps.module_Fusion.proxy.APSFusionProxy")
  public static APSFusionProxy proxy;
  
  public void registerTextureFX() {}
}
