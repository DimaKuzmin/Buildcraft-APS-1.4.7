package aps;

import net.minecraft.client.Minecraft;

public abstract class module
{
  protected int ItemEnergy = 0;
  protected int ItemMachine = 16;
  
  public abstract void preInit();
  
  public abstract void clientInit();
  
  public abstract void initialize();
  
  public abstract void postInit();
  
  public void OnTick(float f, Minecraft minecraft) {}
}
