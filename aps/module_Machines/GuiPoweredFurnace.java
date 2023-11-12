package aps.module_Machines;

import aps.BuildcraftAPS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;




public class GuiPoweredFurnace extends GuiContainer
{
  private TileEntityPoweredFurnace furnaceInventory;
  String guiFile = BuildcraftAPS.imageFilesRoot + "PoweredFurnaceGUI.png";
  
  public GuiPoweredFurnace(TileEntityPoweredFurnace furnace, InventoryPlayer playerInv)
  {
    super(new ContainerPoweredFurnace(furnace, playerInv));
    this.furnaceInventory = furnace;
  }
  
  protected void drawGuiContainerForegroundLayer(int x, int y)
  {
    this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
  }
  
  protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
  {
    int guiTex = this.mc.renderEngine.getTexture(this.guiFile);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.renderEngine.bindTexture(guiTex);
    int topLeftX = (this.width - this.xSize) / 2;
    int topLeftY = (this.height - this.ySize) / 2;
    drawTexturedModalRect(topLeftX, topLeftY, 0, 0, this.xSize, this.ySize);
    displayTempGauge(topLeftX, topLeftY, 79, 11, this.furnaceInventory.getCookProgressScaled(13));
  }
  
  private void displayTempGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight)
  {
   // MinecraftForgeClient.preloadTexture(this.guiFile);
    
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
