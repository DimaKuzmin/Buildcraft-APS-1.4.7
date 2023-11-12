package aps.module_Core.proxy;

import aps.module_Machines.TileEntityEnergyDirector;
import aps.module_Machines.TileEntityEnergyDirector.UpdateMessage;
import aps.module_Machines.TileEntityEnergyStore;
import buildcraft.core.network.PacketUpdate;
import buildcraft.core.network.TilePacketWrapper;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;




public class APSPacketHandler
  implements IPacketHandler
{
  public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
  {
    DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
    
    try
    {
      int packetID = data.read();
      switch (packetID)
      {

      case 0: 
        PacketUpdate packetR = new PacketUpdate();
        packetR.readData(data);
        onUpdateEnergyRedirector((EntityPlayer)player, packetR);
        break;
      case 1: 
        PacketUpdate packetS = new PacketUpdate();
        packetS.readData(data);
        onUpdateEnergyStore((EntityPlayer)player, packetS);
      }
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  private void onUpdateEnergyRedirector(EntityPlayer player, PacketUpdate packet)
  {
    if (!player.worldObj.blockExists(packet.posX, packet.posY, packet.posZ)) {
      return;
    }
    if (!(player.worldObj.getBlockTileEntity(packet.posX, packet.posY, packet.posZ) instanceof TileEntityEnergyDirector)) {
      return;
    }
    TileEntityEnergyDirector tile = (TileEntityEnergyDirector)player.worldObj.getBlockTileEntity(packet.posX, packet.posY, packet.posZ);
    
    TileEntityEnergyDirector.UpdateMessage message = new TileEntityEnergyDirector.UpdateMessage();
    TileEntityEnergyDirector.updateMessageWrapper.fromPayload(message, packet.payload);
    tile.handleUpdateMessage(message);
  }
  
  private void onUpdateEnergyStore(EntityPlayer player, PacketUpdate packet)
  {
    if (!player.worldObj.blockExists(packet.posX, packet.posY, packet.posZ)) {
      return;
    }
    if (!(player.worldObj.getBlockTileEntity(packet.posX, packet.posY, packet.posZ) instanceof TileEntityEnergyStore)) {
      return;
    }
    TileEntityEnergyStore tile = (TileEntityEnergyStore)player.worldObj.getBlockTileEntity(packet.posX, packet.posY, packet.posZ);
    
 
  }
}
