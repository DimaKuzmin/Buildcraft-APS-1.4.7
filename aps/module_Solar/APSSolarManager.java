package aps.module_Solar;

import java.util.LinkedList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenDesert;
import net.minecraft.world.biome.BiomeGenEnd;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenHell;
import net.minecraft.world.biome.BiomeGenHills;
import net.minecraft.world.biome.BiomeGenPlains;
import net.minecraft.world.biome.BiomeGenSnow;
import net.minecraft.world.biome.BiomeGenSwamp;
import net.minecraft.world.biome.BiomeGenTaiga;

public class APSSolarManager
{
  LinkedList<TileEntitySolarReflector> Reflectors = new LinkedList<TileEntitySolarReflector>();
  float ReflectorPower = 0.125F;
  


  World w = null;
  public TileEntitySolarCollector collector = null;
  int X = 0;
  int Y = 0;
  int Z = 0;
  



  public void worldSetup(World W, TileEntitySolarCollector c)
  {
    if (this.w == null)
    {
      this.w = W;
      this.X = c.xCoord;
      this.Y = c.yCoord;
      this.Z = c.zCoord;
      this.collector = c;
      module_Solar.controller.addSolarManager(this);
    }
  }
  
  public void addReflector(TileEntitySolarReflector Reflector) { if (!this.Reflectors.contains(Reflector)) this.Reflectors.add(Reflector); }
  
  public void remReflector(TileEntitySolarReflector Reflector) { this.Reflectors.remove(Reflector); }
  
  public int getLightInput()
  {
    int Light = 0;
    for (TileEntitySolarReflector ref : this.Reflectors)
    {
      Light = (int)(Light + ref.getLightAmount());
    }
    return (int)(Light * getTimeLightMult(this.w));
  }
  
  public float getPowerOutput() { return getLightInput() * this.ReflectorPower; }
  
  public static float getTimeLightMult(World w)
  {
    if ((w != null) && (w.isDaytime())) return 1.0F; return 0.0F;
  }
  











  public static float getBiomeLightMult(BiomeGenBase biome)
  {
    if ((biome instanceof BiomeGenDesert))
      return 2.0F;
    if ((biome instanceof BiomeGenHills))
      return 1.5F;
    if ((biome instanceof BiomeGenPlains))
      return 1.25F;
    if (((biome instanceof BiomeGenForest)) || ((biome instanceof BiomeGenTaiga)) || ((biome instanceof BiomeGenSwamp)))
    {

      return 0.75F; }
    if ((biome instanceof BiomeGenSnow))
      return 0.5F;
    if (((biome instanceof BiomeGenEnd)) || ((biome instanceof BiomeGenHell)))
    {
      return 0.0F;
    }
    return 1.0F;
  }
  
  public void kill()
  {
    module_Solar.controller.remSolarManager(this);
    for (TileEntitySolarReflector Ref : this.Reflectors)
    {
      Ref.manager = null;
    }
  }
}
