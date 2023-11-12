package aps.module_Fusion;

import buildcraft.transport.TileGenericPipe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiEnergyReader
  extends Gui
{
  public TileGenericPipe pipe;
  public EntityPlayer player;
  private Minecraft mc = ModLoader.getMinecraftInstance();
  






  public void render()
  {
    if (this.pipe == null) {
      return;
    }
    if (this.mc.thePlayer == null)
    {
      this.pipe = null;
      return;
    }
    
    if ((this.mc.currentScreen != null) && (!this.mc.inGameHasFocus)) { return;
    }
    DrawInfo();
  }
  



  public void DrawInfo()
  {
    drawString(this.mc.fontRenderer, "Test string", this.mc.displayWidth / 2, this.mc.displayHeight / 2, 16777215);
    GL11.glBindTexture(3553, this.mc.renderEngine.getTexture("/gui/gui.png"));
    drawTexturedModalRect(100, 100, 0, 0, 100, 100);
  }
}
