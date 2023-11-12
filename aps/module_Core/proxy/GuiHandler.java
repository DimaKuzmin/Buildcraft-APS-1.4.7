package aps.module_Core.proxy;

import aps.module_Core.ContainerAPS;
import aps.module_Fusion.ContainerTokamakGenerator;
import aps.module_Fusion.GuiTokamakGenerator;
import aps.module_Fusion.TileEntityTokamakGenerator;
import aps.module_Machines.ContainerEnergyStore;
import aps.module_Machines.ContainerExtractor;
import aps.module_Machines.ContainerGrinder;
import aps.module_Machines.ContainerMagmafier;
import aps.module_Machines.ContainerPoweredFurnace;
import aps.module_Machines.GuiEnergyDirector;
import aps.module_Machines.GuiEnergyStore;
import aps.module_Machines.GuiExtractor;
import aps.module_Machines.GuiGrinder;
import aps.module_Machines.GuiMagmafier;
import aps.module_Machines.GuiPoweredFurnace;
import aps.module_Machines.TileEntityEnergyDirector;
import aps.module_Machines.TileEntityEnergyStore;
import aps.module_Machines.TileEntityExtractor;
import aps.module_Machines.TileEntityGrinder;
import aps.module_Machines.TileEntityMagmafier;
import aps.module_Machines.TileEntityPoweredFurnace;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;







public class GuiHandler
  implements IGuiHandler
{
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    if (!world.blockExists(x, y, z)) {
      return null;
    }
    TileEntity tile = world.getBlockTileEntity(x, y, z);
    
    switch (ID)
    {
    case 120: 
      if (!(tile instanceof TileEntityGrinder))
        return null;
      return new GuiGrinder((TileEntityGrinder)tile, player.inventory);
    
    case 121: 
      if (!(tile instanceof TileEntityMagmafier))
        return null;
      return new GuiMagmafier((TileEntityMagmafier)tile, player.inventory);
    
    case 122: 
      if (!(tile instanceof TileEntityPoweredFurnace))
        return null;
      return new GuiPoweredFurnace((TileEntityPoweredFurnace)tile, player.inventory);
    
    case 123: 
      if (!(tile instanceof TileEntityExtractor))
        return null;
      return new GuiExtractor((TileEntityExtractor)tile, player.inventory);
    
    case 124: 
      if (!(tile instanceof TileEntityEnergyDirector))
        return null;
      return new GuiEnergyDirector((TileEntityEnergyDirector)tile, player.inventory);
    
    case 125: 
      if (!(tile instanceof TileEntityEnergyStore))
        return null;
      return new GuiEnergyStore((TileEntityEnergyStore)tile, player.inventory);
    
    case 127: 
      if (!(tile instanceof TileEntityTokamakGenerator))
        return null;
      return new GuiTokamakGenerator((TileEntityTokamakGenerator)tile, player.inventory);
    }
    
    return null;
  }
  


  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    if (!world.blockExists(x, y, z)) {
      return null;
    }
    
    Object tile = world.getBlockTileEntity(x, y, z);
    
    switch (ID)
    {
    case 120: 
      if (!(tile instanceof TileEntityGrinder))
        return null;
      return new ContainerGrinder((TileEntityGrinder)tile, player.inventory);
    
    case 121: 
      if (!(tile instanceof TileEntityMagmafier))
        return null;
      return new ContainerMagmafier((TileEntityMagmafier)tile, player.inventory);
    
    case 122: 
      if (!(tile instanceof TileEntityPoweredFurnace))
        return null;
      return new ContainerPoweredFurnace((TileEntityPoweredFurnace)tile, player.inventory);
    
    case 123: 
      if (!(tile instanceof TileEntityExtractor))
        return null;
      return new ContainerExtractor((TileEntityExtractor)tile, player.inventory);
    
    case 124: 
      if (!(tile instanceof TileEntityEnergyDirector))
        return null;
      return new ContainerAPS(player.inventory);
    
    case 125: 
      if (!(tile instanceof TileEntityEnergyStore))
        return null;
      return new ContainerEnergyStore((TileEntityEnergyStore)tile, player.inventory);
    
    case 127: 
      if (!(tile instanceof TileEntityTokamakGenerator))
        return null;
      return new ContainerTokamakGenerator((TileEntityTokamakGenerator)tile, player.inventory);
    }
    
    return null;
  }
}
