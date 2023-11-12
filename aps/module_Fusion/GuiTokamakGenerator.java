package aps.module_Fusion;

import aps.BuildcraftAPS;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.ForgeHooks;

import org.lwjgl.opengl.GL11;



public class GuiTokamakGenerator extends GuiContainer
{
  private TileEntityTokamakGenerator tokamakInventory;
  String guiFile = BuildcraftAPS.imageFilesRoot + "TokamakGUI.png";
  
  int liquidImgIndex = 0;
  public GuiTokamakGenerator(TileEntityTokamakGenerator tokamak, InventoryPlayer playerInv)
  {
    super(new ContainerTokamakGenerator(tokamak, playerInv));
    this.tokamakInventory = tokamak;
  }
  
  protected void drawGuiContainerForegroundLayer(int x, int y)
  {
    this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
    String s1 = "" + (int)this.tokamakInventory.PowerIn;
    this.fontRenderer.drawString(s1, 160 - this.fontRenderer.getStringWidth(s1), 44, 4210752);
    String s2 = "" + (int)this.tokamakInventory.PowerOut;
    this.fontRenderer.drawString(s2, 160 - this.fontRenderer.getStringWidth(s2), 57, 4210752);
  }
  
  protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
  {
    int guiTex = this.mc.renderEngine.getTexture(this.guiFile);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.renderEngine.bindTexture(guiTex);
    int topLeftX = (this.width - this.xSize) / 2;
    int topLeftY = (this.height - this.ySize) / 2;
    drawTexturedModalRect(topLeftX, topLeftY, 0, 0, this.xSize, this.ySize);
    


    if (this.tokamakInventory.PowerOut > 1.0F) {
      drawTexturedModalRect(topLeftX + 103, topLeftY + 20, 176, 0, 8, 8);
    }
    displayPowerGauge(topLeftX, topLeftY, 103, 44, (int)this.tokamakInventory.getScaledPower(false, 58));
    
    displayPowerGauge(topLeftX, topLeftY, 103, 57, (int)this.tokamakInventory.getScaledPower(true, 58));
    
    displayTempGauge(topLeftX, topLeftY, 85, 18, (int)this.tokamakInventory.getScaledTemp(58), (int)this.tokamakInventory.getScaledFusionTemp(58));
    
    displayWaterGauge(topLeftX, topLeftY, 61, 18, (int)this.tokamakInventory.getScaledLiquidQuantity(58));
  }
  

  private void displayWaterGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight)
  {
  
  if (this.tokamakInventory.tank.getLiquid() != null){
      if (this.tokamakInventory.tank.getLiquid().itemID != 0)
      {
    	  ForgeHooksClient.bindTexture(Block.blocksList[this.tokamakInventory.tank.getLiquid().itemID].getTextureFile(), 0);    
    	  liquidImgIndex = Block.blocksList[this.tokamakInventory.tank.getLiquid().itemID].blockIndexInTexture;
      }
      else
      {
     	  return;
      }
      
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
  }
    this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture(this.guiFile));
    drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY, 176, 17, 16, 60);
  }
  


  private void displayTempGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight, int FusionHeight)
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
      
      drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + 58 - x - start, 176, 8 + (8 - x), 8, x);
      start += 8;
      
      if ((x == 0) || (BarHeight == 0)) {
        break;
      }
    }
    drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + (58 - FusionHeight), 176, 16, 8, 1);
  }
  


  private void displayPowerGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarWidth)
  {
    int start = 0;
    
    for (;;)
    {
      int x = 0;
      
      if (BarWidth > 8) {
        x = 8;
        BarWidth -= 8;
      } else {
        x = BarWidth;
        BarWidth = 0;
      }
      
      drawTexturedModalRect(TopLeftX + GaugeTopLeftX + start, TopLeftY + GaugeTopLeftY, 176, 8, x, 8);
      start += 8;
      
      if ((x == 0) || (BarWidth == 0)) {
        break;
      }
    }
  }
}
