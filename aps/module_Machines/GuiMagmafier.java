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





public class GuiMagmafier
  extends GuiContainer
{
  private TileEntityMagmafier magmafierInventory;
  String guiFile = BuildcraftAPS.imageFilesRoot + "MagmafierGUI.png";
  
  public GuiMagmafier(TileEntityMagmafier magmafier, InventoryPlayer playerInv)
  {
    super(new ContainerMagmafier(magmafier, playerInv));
    this.magmafierInventory = magmafier;
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
    displayBlockLevelGauge(topLeftX, topLeftY, 79, 11, (int)this.magmafierInventory.getScaledBlockQuantity(64));
    displayPowerLevelGauge(topLeftX, topLeftY, 93, 11, (int)this.magmafierInventory.getScaledPowerLevel(64));
    displayLiquidGauge(topLeftX, topLeftY, 107, 14, this.magmafierInventory.getScaledLiquidQuantity(), TileEntityMagmafier.LavaID);
  }
  
  private void displayPowerLevelGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight)
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
  
  private void displayBlockLevelGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight)
  {
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
      
      drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + 64 - x - start, 176, 24 - x, 8, x);
      start += 16;
      
      if ((x == 0) || (BarHeight == 0)) {
        break;
      }
    }
  }
  
  private void displayLiquidGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, float BarHeight, int LiquidId)
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
        x = (int) BarHeight;
        BarHeight = 0;
      }
      
      drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + 58 - x - start, imgColumn * 16, imgLine * 16 + (16 - x), 16, x);
      start += 16;
      
      if ((x == 0) || (BarHeight == 0)) {
        break;
      }
    }
    this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture(this.guiFile));
    drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY, 176, 24, 16, 60);
  }
}
