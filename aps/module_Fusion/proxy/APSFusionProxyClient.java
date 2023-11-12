package aps.module_Fusion.proxy;

import aps.module_Fusion.TextureHeavyWaterFX;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderEngine;

public class APSFusionProxyClient extends APSFusionProxy
{
  public void registerTextureFX()
  {
    RenderEngine renderEngine = FMLClientHandler.instance().getClient().renderEngine;
    
    renderEngine.registerTextureFX(new TextureHeavyWaterFX());
    
    cpw.mods.fml.common.registry.TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
  }
}
