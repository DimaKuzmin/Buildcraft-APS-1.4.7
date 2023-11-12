package aps.module_Machines;

import aps.BuildcraftAPS;
import aps.GuiAPSButton;
import aps.GuiAPSSlider;
import aps.module_Core.ContainerAPS;
import aps.module_Core.proxy.APSPacketUpdate;
import buildcraft.core.network.PacketPayload;
import buildcraft.core.network.TilePacketWrapper;
import buildcraft.core.proxy.CoreProxy;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;













public class GuiEnergyDirector extends GuiContainer
{
  private TileEntityEnergyDirector directorInventory;
  int CurrentSide = 0;
  float[] minvalue_old = { -1.0F, -1.0F, -1.0F, -1.0F };
  float[] gatevalue_old = { -1.0F, -1.0F, -1.0F, -1.0F };
  int[] priority_old = { -1, -1, -1, -1 };
  
  GuiAPSButton PrevSide;
  GuiAPSButton NextSide;
  GuiAPSButton PrioritySelector;
  GuiAPSButton ModeSelector;
  GuiAPSSlider ValueSlider;
  String guiFile = BuildcraftAPS.imageFilesRoot + "EnergyDirectorGUI.png";
  
  public GuiEnergyDirector(TileEntityEnergyDirector director, InventoryPlayer playerInv, int currentSide)
  {
    super(new ContainerAPS(director, playerInv));
    this.directorInventory = director;
    this.CurrentSide = currentSide;
  }
  
  public GuiEnergyDirector(TileEntityEnergyDirector director, InventoryPlayer playerInv)
  {
    super(new ContainerAPS(playerInv));
    this.directorInventory = director;
  }
  

  public void initGui()
  {
    super.initGui();
    
    this.CurrentSide = this.directorInventory.lastSideViewed;
    
    int topLeftX = (this.width - this.xSize) / 2;
    int topLeftY = (this.height - this.ySize) / 2;
    

    this.PrevSide = new GuiAPSButton(420, topLeftX + 149, topLeftY + 21, getTextureOffsetBySideWrapped(this.CurrentSide - 1), 170, 16, 8, this.guiFile, "");
    this.NextSide = new GuiAPSButton(421, topLeftX + 149, topLeftY + 31, getTextureOffsetBySideWrapped(this.CurrentSide + 1), 170, 16, 8, this.guiFile, "");
    this.PrioritySelector = new GuiAPSButton(422, topLeftX + 12, topLeftY + 61, 18 + (this.directorInventory.routeMode ? 0 : 10), 8 + 10 * this.directorInventory.getPriority(this.CurrentSide), 10, 10, this.guiFile, "");
    this.PrioritySelector.enabled = this.directorInventory.routeMode;
    this.ModeSelector = new GuiAPSButton(423, topLeftX + 24, topLeftY + 62, 18 - (this.directorInventory.routeMode ? 8 : 0), 178, 11, 8, this.guiFile, "");
    

    this.ValueSlider = new GuiAPSSlider(424, topLeftX + 38, topLeftY + 63, 80, 8, 184, 0, 5, 7, this.guiFile, "", 0.0F);
    if (this.directorInventory.routeMode) {
      this.ValueSlider.setSliderValue(this.directorInventory.getScaledMinimumPowerValue(this.CurrentSide, 1));
    } else {
      this.ValueSlider.setSliderValue(this.directorInventory.getScaledGateValue(this.CurrentSide, 1));
    }
//    this.getControlList().add(this.PrevSide);
//    this.getControlList().add(this.NextSide);
//    this.getControlList().add(this.PrioritySelector);
//    this.getControlList().add(this.ModeSelector);
//    
//    this.getControlList().add(this.ValueSlider);
  }
  

  protected void mouseMovedOrUp(int posX, int posY, int event)
  {
    if (event == 0)
    {
      if (this.ValueSlider.dragging == true)
      {
        this.ValueSlider.dragging = false;
        
        if (this.directorInventory.routeMode) {
          this.directorInventory.setMinimumPowerValue(this.CurrentSide, this.ValueSlider.getScaledSliderValue(TileEntityEnergyDirector.MaxPowerOut));
        } else {
          this.directorInventory.setGateValue(this.CurrentSide, this.ValueSlider.getScaledSliderValue(1.0F));
        }
        sendUpdatePacket();
      }
    }
  }
  

  protected void actionPerformed(GuiButton button)
  {
    if (!button.enabled)
    {
      return;
    }
    
    switch (button.id)
    {
    case 420: 
      prevSide();
      break;
    case 421: 
      nextSide();
      break;
    case 422: 
      nextPriority();
      break;
    case 423: 
      modeSwitch();
      break;
    case 424: 
      return;
    }
    
    sendUpdatePacket();
  }
  
  protected void drawGuiContainerForegroundLayer(int x, int y)
  {
    String string = null;
    
    if (this.directorInventory.routeMode)
    {
      if (this.directorInventory.defaultRoute != this.CurrentSide) {
        string = String.valueOf((int)this.ValueSlider.getScaledSliderValue(TileEntityEnergyDirector.MaxPowerOut));
      }
    }
    else {
      string = String.valueOf((int)this.ValueSlider.getScaledSliderValue(100.0F)) + "%";
    }
    
    this.fontRenderer.drawString(string, 117 - this.fontRenderer.getStringWidth(string), 62, 4210752);
  }
  
  float PowerGaugeMA = 0.0F; int PowerGaugeMANodes = 30;
  
  protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
  {
    int guiTex = this.mc.renderEngine.getTexture(this.guiFile);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    int topLeftX = (this.width - this.xSize) / 2;
    int topLeftY = (this.height - this.ySize) / 2;
    this.mc.renderEngine.bindTexture(guiTex);
    
    drawTexturedModalRect(topLeftX, topLeftY, 0, 0, this.xSize, this.ySize);
    
    if (this.directorInventory.routeMode) {
      if (this.directorInventory.defaultRoute == this.CurrentSide) {
        drawTexturedModalRect(topLeftX + 37, topLeftY + 61, 0, 186, 82, 10);
        this.ValueSlider.enabled = false;
        this.ValueSlider.drawButton = false;
      } else {
        this.ValueSlider.enabled = true;
        this.ValueSlider.drawButton = true;
      }
    }
    





    this.directorInventory.lastSideViewed = this.CurrentSide;
    
    displayBorder(topLeftX + 6, topLeftY + 16, 34, 16);
    
    this.PowerGaugeMA -= this.PowerGaugeMA / this.PowerGaugeMANodes;
    this.PowerGaugeMA += this.directorInventory.getScaledLastPowerOutput(this.CurrentSide, 50);
    displayTempGauge(topLeftX, topLeftY, 127, 23, (int)(this.PowerGaugeMA / this.PowerGaugeMANodes));
    
    displayPowerGauge(topLeftX, topLeftY, 13, 35, (int)this.directorInventory.getScaledLastMinimumPowerOut(this.CurrentSide, 80));
    displayPowerGauge(topLeftX, topLeftY, 13, 56, (int)this.directorInventory.getScaledLastGatePowerOut(this.CurrentSide, 80));
    
    displayScreenOutput(topLeftX, topLeftY);
  }
  
  void nextPriority()
  {
    if (this.directorInventory.defaultRoute == -1) {
      if (this.directorInventory.getPriority(this.CurrentSide) == 3) {
        this.directorInventory.setPriority(this.CurrentSide, 0);
      } else {
        this.directorInventory.setPriority(this.CurrentSide, this.directorInventory.getPriority(this.CurrentSide) + 1);
      }
    }
    else if ((this.directorInventory.getPriority(this.CurrentSide) == 3) || (this.directorInventory.getPriority(this.CurrentSide) == 2)) {
      this.directorInventory.setPriority(this.CurrentSide, 0);
    } else {
      this.directorInventory.setPriority(this.CurrentSide, this.directorInventory.getPriority(this.CurrentSide) + 1);
    }
    

    this.PrioritySelector.setTexYPos(8 + 10 * this.directorInventory.getPriority(this.CurrentSide));
  }
  


  void nextSide()
  {
    if (this.CurrentSide == 3) this.CurrentSide = 0; else {
      this.CurrentSide += 1;
    }
    if (this.directorInventory.routeMode) {
      this.ValueSlider.setSliderValue(this.directorInventory.getScaledMinimumPowerValue(this.CurrentSide, 1));
    } else {
      this.ValueSlider.setSliderValue(this.directorInventory.getScaledGateValue(this.CurrentSide, 1));
    }
    this.PrioritySelector.setTexYPos(8 + 10 * this.directorInventory.getPriority(this.CurrentSide));
    this.PrevSide.setTexXPos(getTextureOffsetBySideWrapped(this.CurrentSide - 1));
    this.NextSide.setTexXPos(getTextureOffsetBySideWrapped(this.CurrentSide + 1));
    this.PowerGaugeMA = this.directorInventory.getScaledLastPowerOutput(this.CurrentSide, 50 + this.PowerGaugeMANodes);
  }
  


  void prevSide()
  {
    if (this.CurrentSide == 0) this.CurrentSide = 3; else {
      this.CurrentSide -= 1;
    }
    if (this.directorInventory.routeMode) {
      this.ValueSlider.setSliderValue(this.directorInventory.getScaledMinimumPowerValue(this.CurrentSide, 1));
    } else {
      this.ValueSlider.setSliderValue(this.directorInventory.getScaledGateValue(this.CurrentSide, 1));
    }
    this.PrioritySelector.setTexYPos(8 + 10 * this.directorInventory.getPriority(this.CurrentSide));
    this.PrevSide.setTexXPos(getTextureOffsetBySideWrapped(this.CurrentSide - 1));
    this.NextSide.setTexXPos(getTextureOffsetBySideWrapped(this.CurrentSide + 1));
    this.PowerGaugeMA = this.directorInventory.getScaledLastPowerOutput(this.CurrentSide, 50 + this.PowerGaugeMANodes);
  }
  



  void modeSwitch()
  {
    this.directorInventory.routeMode = (!this.directorInventory.routeMode);
    

    this.ModeSelector.setTexXPos(18 - (this.directorInventory.routeMode ? 8 : 0));
    

    this.PrioritySelector.setTexXPos(18 + (this.directorInventory.routeMode ? 0 : 10));
    this.PrioritySelector.enabled = this.directorInventory.routeMode;
    
    if (this.directorInventory.routeMode) {
      this.ValueSlider.setSliderValue(this.directorInventory.getScaledMinimumPowerValue(this.CurrentSide, 1));
    } else {
      this.ValueSlider.setSliderValue(this.directorInventory.getScaledGateValue(this.CurrentSide, 1));
    }
  }
  

  int getTextureOffset()
  {
    if ((this.CurrentSide < 0) || (this.CurrentSide > 3)) return 10;
    return 10 + this.CurrentSide * 20;
  }
  
  int getTextureOffsetBySideWrapped(int Side)
  {
    if (Side < 0) Side += 4;
    if (Side > 3) Side -= 4;
    return 10 + Side * 20;
  }
  
  private void displayBorder(int BorderTopLeftX, int BorderTopLeftY, int BorderWidth, int BorderHeight)
  {
    MinecraftForgeClient.preloadTexture(this.guiFile);
    
    for (int i = 0; i < BorderWidth; i++)
    {
      for (int j = 0; j < BorderHeight; j++)
      {
        int BorderSectionX = BorderTopLeftX + i * 4;
        int BorderSectionY = BorderTopLeftY + j * 4;
        if (i == 0)
        {
          if (j == 0) {
            drawTexturedModalRect(BorderSectionX, BorderSectionY, getTextureOffset() + 4, 166, 4, 4);
          } else if (j == BorderHeight - 1) {
            drawTexturedModalRect(BorderSectionX, BorderSectionY, getTextureOffset() + 12, 166, 4, 4);
          } else {
            drawTexturedModalRect(BorderSectionX, BorderSectionY, getTextureOffset(), 166, 4, 4);
          }
        } else if (i == BorderWidth - 1)
        {
          if (j == 0) {
            drawTexturedModalRect(BorderSectionX, BorderSectionY, getTextureOffset() + 8, 166, 4, 4);
          } else if (j == BorderHeight - 1) {
            drawTexturedModalRect(BorderSectionX, BorderSectionY, getTextureOffset() + 16, 166, 4, 4);
          } else {
            drawTexturedModalRect(BorderSectionX, BorderSectionY, getTextureOffset(), 166, 4, 4);
          }
          
        }
        else if ((j == 0) || (j == BorderHeight - 1)) {
          drawTexturedModalRect(BorderSectionX, BorderSectionY, getTextureOffset(), 166, 4, 4);
        }
      }
    }
  }
  
  private void displayTempGauge(int TopLeftX, int TopLeftY, int GaugeTopLeftX, int GaugeTopLeftY, int BarHeight)
  {
    MinecraftForgeClient.preloadTexture(this.guiFile);
    
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
      
      drawTexturedModalRect(TopLeftX + GaugeTopLeftX, TopLeftY + GaugeTopLeftY + 50 - x - start, 176, 8 - x, 8, x);
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
      
      drawTexturedModalRect(TopLeftX + GaugeTopLeftX + start, TopLeftY + GaugeTopLeftY, 176, 0, x, 8);
      start += 8;
      
      if ((x == 0) || (BarWidth == 0)) {
        break;
      }
    }
  }
  
  public void displayScreenOutput(int posX, int posY)
  {
    for (int i = 0; i < 3; i++)
    {
      String text = this.directorInventory.text[i];
      
      if ((text != null) && (!text.equals("")))
      {
        if (text.contains("[r]")) {
          this.fontRenderer.drawString(text.replace("[r]", ""), posX + 13, posY + 26 + this.fontRenderer.FONT_HEIGHT * i + 1, 15597568);
        } else if (text.contains("[b]")) {
          this.fontRenderer.drawString(text.replace("[b]", ""), posX + 13, posY + 26 + this.fontRenderer.FONT_HEIGHT * i + 1, 238);
        } else {
          this.fontRenderer.drawString(text, posX + 13, posY + 26 + this.fontRenderer.FONT_HEIGHT * i + 1, 60928);
        }
      }
    }
  }
  
  public void sendUpdatePacket() {
    TileEntityEnergyDirector.UpdateMessage message = new TileEntityEnergyDirector.UpdateMessage();
    
    message.Side = this.CurrentSide;
    message.minValue = this.directorInventory.PowerData[1][this.CurrentSide];
    message.gateValue = this.directorInventory.getScaledGateValue(this.CurrentSide, 1);
    message.priority = this.directorInventory.getPriority(this.CurrentSide);
    message.mode = this.directorInventory.routeMode;
    
    if (CoreProxy.proxy.isRenderWorld(this.directorInventory.worldObj))
    {
      PacketPayload payload = TileEntityEnergyDirector.updateMessageWrapper.toPayload(this.directorInventory.xCoord, this.directorInventory.yCoord, this.directorInventory.zCoord, message);
      
      APSPacketUpdate packet = new APSPacketUpdate(0, payload);
      packet.posX = this.directorInventory.xCoord;
      packet.posY = this.directorInventory.yCoord;
      packet.posZ = this.directorInventory.zCoord;
      
      CoreProxy.proxy.sendToServer(packet.getPacket());
    }
  }
}
