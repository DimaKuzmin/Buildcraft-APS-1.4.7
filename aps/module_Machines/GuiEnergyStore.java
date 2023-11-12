package aps.module_Machines;

import aps.BuildcraftAPS;
import aps.GuiAPSSlider;
import aps.module_Core.proxy.APSPacketUpdate;
import buildcraft.core.network.PacketPayload;
import buildcraft.core.network.TilePacketWrapper;
import buildcraft.core.proxy.CoreProxy;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

public class GuiEnergyStore
  extends GuiContainer
{
  private TileEntityEnergyStore storeInventory;
  GuiAPSSlider InSlider;
  GuiAPSSlider OutSlider;
  
  String guiFile = BuildcraftAPS.imageFilesRoot + "EnergyStoreGUI.png";
  private float InSlider_old;
  private float OutSlider_old;
  
  public GuiEnergyStore(TileEntityEnergyStore store, InventoryPlayer playerInv)
  {
    super(new ContainerEnergyStore(store, playerInv));
    this.storeInventory = store;
  }
  

  public void initGui()
  {
    super.initGui();
    
    int topLeftX = (this.width - this.xSize) /2;
    int topLeftY = (this.height - this.ySize) /2;
    
    this.InSlider = new GuiAPSSlider(420, topLeftX + 87, topLeftY + 40, 71, 8, 184, 0, 5, 7, this.guiFile, "", (((float) storeInventory.UserMaxPowerIn) / storeInventory.MaxPowerIn));
    this.OutSlider = new GuiAPSSlider(421, (width - xSize) / 2 + 87, (height - ySize) / 2 + 53, 71, 8, 184, 0, 5, 7, guiFile, "", (((float) storeInventory.UserMaxPowerOut) / storeInventory.MaxPowerOut));
    
    this.controlList.add(this.InSlider);
    this.controlList.add(this.OutSlider);
 
  }
  

  protected void mouseMovedOrUp(int posX, int posY, int event)
  {
    if (event == 0)
    {
      for (int element = 0; element < this.controlList.size(); element++)
      {
        if ((this.controlList.get(element) instanceof GuiAPSSlider))
        {
          ((GuiAPSSlider)this.controlList.get(element)).dragging = false;
        }
      }
    }
  }
  
  protected void drawGuiContainerForegroundLayer(int x, int y)
  {
    this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
    
    String s1 = "" + this.storeInventory.UserMaxPowerIn;
    this.fontRenderer.drawString(s1, 157 - this.fontRenderer.getStringWidth(s1), 40, 4210752);
    String s2 = "" + this.storeInventory.UserMaxPowerOut;
    this.fontRenderer.drawString(s2, 157 - this.fontRenderer.getStringWidth(s2), 53, 4210752);
  }
  

  protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
  {
    int guiTex = this.mc.renderEngine.getTexture(this.guiFile);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    int topLeftX = (this.width - this.xSize) / 2;
    int topLeftY = (this.height - this.ySize) / 2;
    this.mc.renderEngine.bindTexture(guiTex);
    drawTexturedModalRect(topLeftX, topLeftY, 0, 0, this.xSize, this.ySize);
    
    if (this.storeInventory.isDraining()) {
      drawTexturedModalRect(topLeftX + 87, topLeftY + 16, 176, 0, 8, 8);
    }
    displayStoreGauge(topLeftX, topLeftY, 68, 14, (int)this.storeInventory.getScaledPowerStored(58));
    
    displayPowerGauge(topLeftX, topLeftY, 87, 40, (int)this.storeInventory.getScaledPower(false, 71));
    
    displayPowerGauge(topLeftX, topLeftY, 87, 53, (int)this.storeInventory.getScaledPower(true, 71));
    
    this.storeInventory.UserMaxPowerIn = ((int)this.InSlider.getScaledSliderValue(TileEntityEnergyStore.MaxPowerIn));
    this.storeInventory.UserMaxPowerOut = ((int)this.OutSlider.getScaledSliderValue(TileEntityEnergyStore.MaxPowerOut));
  }
  
  private void displayStoreGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight)
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
  }
  
  private void displayPowerGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarWidth)
  {
    MinecraftForgeClient.preloadTexture(this.guiFile);
    
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
