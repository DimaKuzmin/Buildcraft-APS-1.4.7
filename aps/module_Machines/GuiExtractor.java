package aps.module_Machines;

import aps.BuildcraftAPS;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiExtractor
  extends GuiContainer
{
  private TileEntityExtractor extractorInventory;
  String guiFile = BuildcraftAPS.imageFilesRoot + "ExtractorGUI.png";
  
  int guiTex;
  
  public GuiExtractor(TileEntityExtractor extractor, InventoryPlayer playerInv)
  {
    super(new ContainerExtractor(extractor, playerInv));
    this.extractorInventory = extractor;
  }
  
  protected void drawGuiContainerForegroundLayer(int x, int y)
  {
    this.fontRenderer.drawString("Traces Detected:", 42, 15, 6145451);
    int i = 0;
    for (String S : this.extractorInventory.getPossibleProducts())
    {
      this.fontRenderer.drawString(S, 42, 23 + 8 * i, 8000000);
      i++;
    }
  }
  
  protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
  {
    this.guiTex = this.mc.renderEngine.getTexture(this.guiFile);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.renderEngine.bindTexture(this.guiTex);
    int topLeftX = (this.width - this.xSize) / 2;
    int topLeftY = (this.height - this.ySize) / 2;
    drawTexturedModalRect(topLeftX, topLeftY, 0, 0, this.xSize, this.ySize);
    displayTempGauge(topLeftX, topLeftY, 8, 16, (int)this.extractorInventory.getScaledPowerLevel(64));
    displayLiquidGauge(topLeftX, topLeftY, 22, 19, (int)this.extractorInventory.getScaledLiquidQuantity(), TileEntityExtractor.LavaID);
  }
  
  private void displayTempGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight)
  {
    int start = 0;
    int texPosX;
    if (this.extractorInventory.getPowerSufficient()) {
      texPosX = 184;
    } else {
      texPosX = 176;
    }
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
      
      drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + 64 - x - start, texPosX, 8 - x, 8, x);
      start += 8;
      
      if ((x == 0) || (BarHeight == 0)) {
        break;
      }
    }
  }
  
  private void displayLiquidGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight, int LiquidId)
  {
    Object o = Block.blocksList[LiquidId];
    int liquidImgIndex = Block.blocksList[LiquidId].blockIndexInTexture;
    
    if (o == null) {
      return;
    }
    
    this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/terrain.png"));
    
    int imgLine = liquidImgIndex / 16;
    int imgColumn = liquidImgIndex - imgLine * 16;
    
    int start = 0;
    
    for (;;)
    {
      int x = 0;
      
      if (BarHeight > 16) {
        x = 16;
        BarHeight -= 16;
      } else {
        x = BarHeight;
        BarHeight = 0;
      }
      
      drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + 58 - x - start, imgColumn * 16, imgLine * 16 + (16 - x), 16, x);
      start += 16;
      
      if ((x == 0) || (BarHeight == 0)) {
        break;
      }
    }
    this.mc.renderEngine.bindTexture(this.guiTex);
    drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY, 176, 8, 16, 60);
  }
}
