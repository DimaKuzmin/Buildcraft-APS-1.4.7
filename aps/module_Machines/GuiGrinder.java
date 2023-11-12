package aps.module_Machines;

import aps.BuildcraftAPS;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiGrinder
  extends GuiContainer
{
  private TileEntityGrinder grinderInventory;
  String guiFile = BuildcraftAPS.imageFilesRoot + "GrinderGUI.png";
  
  public GuiGrinder(TileEntityGrinder grinder, InventoryPlayer playerInv)
  {
    super(new ContainerGrinder(grinder, playerInv));
    this.grinderInventory = grinder;
  }
  
  protected void drawGuiContainerForegroundLayer(int x, int y)
  {
    this.fontRenderer.drawString("Traces Detected:", 60, -10, 6145451);
    int i = 0;
    for (String S : this.grinderInventory.getPossibleProducts())
    {
      this.fontRenderer.drawString(S, 64, 3 + 7 * i, 8000000);
      i++;
    }
  }
  
  protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
  {
    int guiTex = this.mc.renderEngine.getTexture(this.guiFile);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.renderEngine.bindTexture(guiTex);
    int topLeftX = (this.width - this.xSize) / 2;
    int topLeftY = (this.height - this.ySize) / 2;
    drawTexturedModalRect(topLeftX, topLeftY, 0, 0, this.xSize, this.ySize);
    displayTempGauge(topLeftX, topLeftY, 52, 11, this.grinderInventory.getGrindProgressScaled(64));
  }
  
  private void displayTempGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight)
  {
    int start = 0;
    
    for (;;)
    {
      int x = 0;
      
      if (BarHeight > 8) {
        x = 8;
        BarHeight -= 8;
      } else {
        x = BarHeight;
        BarHeight = 0;
      }
      
      drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + 64 - x - start, 176, 8 - x, 8, x);
      start += 8;
      
      if ((x == 0) || (BarHeight == 0)) {
        break;
      }
    }
  }
}
