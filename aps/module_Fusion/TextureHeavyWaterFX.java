package aps.module_Fusion;

import buildcraft.core.render.TextureLiquidsFX;
import net.minecraft.item.Item;










public class TextureHeavyWaterFX
  extends TextureLiquidsFX
{
  public TextureHeavyWaterFX()
  {
    super(80, 140, 115, 175, 160, 220, module_Fusion.heavyWater.getIconFromDamage(0), module_Fusion.heavyWater.getTextureFile());
  }
}
