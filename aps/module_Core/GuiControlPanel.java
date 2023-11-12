package aps.module_Core;

import aps.BuildcraftAPS;
import aps.GuiAPSSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;









public class GuiControlPanel
  extends GuiScreen
{
  ItemAPSControlPanel controlPanel;
  Mode PanelMode;
  GuiAPSSlider InSlider;
  GuiAPSSlider OutSlider;
  
  static enum Mode
  {
    Param,  Meter;
  }
  
 
  String guiFile = BuildcraftAPS.imageFilesRoot + "EnergyStoreGUI.png";
  

  public GuiControlPanel(ItemAPSControlPanel panel)
  {
    this.controlPanel = panel;
  }
  

  public void initGui()
  {
    super.initGui();
  }
  



  public void handleMouseInput()
  {
    boolean isShift = (Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54));
    int wheel = Mouse.getDWheel() / 120;
    if (wheel == 0) {
      super.handleMouseInput();
      return;
    }
    if (isShift)
    {
      if (wheel > 0) {
        nextMode();
      } else {
        prevMode();
      }
    }
    





    super.handleMouseInput();
  }
  
  void nextMode()
  {
    int nm = this.PanelMode.ordinal() + 1;
    if (nm >= Mode.values().length)
      nm -= Mode.values().length;
    this.PanelMode = Mode.values()[nm];
  }
  
  void prevMode()
  {
    int pm = this.PanelMode.ordinal() + 1;
    if (pm < 0)
      pm += Mode.values().length;
    this.PanelMode = Mode.values()[pm];
  }
  




  protected void drawGuiContainerForegroundLayer() {}
  




  protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {}
  



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
