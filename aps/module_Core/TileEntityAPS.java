package aps.module_Core;

import java.util.HashMap;
import java.util.Map;

import aps.module_Core.module_Core.APSBlockTypes;
import buildcraft.core.DefaultProps;
import buildcraft.core.network.ISynchronizedTile;
import buildcraft.core.network.PacketPayload;
import buildcraft.core.network.PacketTileUpdate;
import buildcraft.core.network.PacketUpdate;
import buildcraft.core.network.TilePacketWrapper;
import buildcraft.core.proxy.CoreProxy;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;







public abstract class TileEntityAPS
  extends TileEntity
  implements ISynchronizedTile
{
  int[] texes = new int[6];
  
  int defTopTex;
  int defBottomTex;
  int defSideTex;
  private final TilePacketWrapper updatePacket;
  private final TilePacketWrapper descriptionPacket;
  public APSBlockTypes APSBlockType;
  public int GuiID = 0;
  

  private static Map updateWrappers = new HashMap();
  

  private static Map descriptionWrappers = new HashMap();
  
  public TileEntityAPS(int BottomTex, int TopTex, int DefSideTex, APSBlockTypes type)
  {
    this.defBottomTex = BottomTex;this.texes[0] = this.defBottomTex;
    this.defTopTex = TopTex;this.texes[1] = this.defTopTex;
    this.defSideTex = DefSideTex; for (int i = 2; i < 6; i++) { this.texes[i] = this.defSideTex;
    }
    this.APSBlockType = type;
    
    if (!updateWrappers.containsKey(getClass())) {
      updateWrappers.put(getClass(), new TilePacketWrapper(getClass()));
    }
    if (!descriptionWrappers.containsKey(getClass())) {
      descriptionWrappers.put(getClass(), new TilePacketWrapper(getClass()));
    }
    this.updatePacket = ((TilePacketWrapper)updateWrappers.get(getClass()));
    this.descriptionPacket = ((TilePacketWrapper)descriptionWrappers.get(getClass()));
  }
  
  public int getTexture(int Face)
  {
    return this.texes[Face];
  }
  
  public void setTexture(int Face, int TexIndex)
  {
    if ((Face < 0) || (Face > 5)) return;
    this.texes[Face] = TexIndex;
  }
  
  public void resetTexture(int Face)
  {
    if ((Face < 0) || (Face > 5)) return;
    if (Face == 0) { this.texes[Face] = this.defBottomTex;
    } else if (Face == 5) this.texes[Face] = this.defTopTex; else
      this.texes[Face] = this.defSideTex;
  }
  
  public void resetAllTextures() {
    this.texes[0] = this.defBottomTex;
    this.texes[1] = this.defTopTex;
    for (int i = 2; i < 6; i++) this.texes[i] = this.defSideTex;
  }
  
  public abstract void kill();
  
  public IInventory getInventory() { return null; }
  
  public boolean hasGUI = false;
  


  public void sendNetworkUpdate()
  {
    if (CoreProxy.proxy.isSimulating(this.worldObj)) {
      CoreProxy.proxy.sendToPlayers(getUpdatePacket(), this.worldObj, this.xCoord, this.yCoord, this.zCoord, DefaultProps.NETWORK_UPDATE_RANGE);
    }
  }
  
  public Packet getAuxillaryInfoPacket() {
    return new PacketTileUpdate(this).getPacket();
  }
  
  public PacketPayload getPacketPayload()
  {
    return this.updatePacket.toPayload(this);
  }
  
  public Packet getUpdatePacket()
  {
    return new PacketTileUpdate(this).getPacket();
  }
  
  public void handleDescriptionPacket(PacketUpdate packet)
  {
    this.updatePacket.fromPayload(this, packet.payload);
  }
  
  public void handleUpdatePacket(PacketUpdate packet)
  {
    this.updatePacket.fromPayload(this, packet.payload);
  }
  
  public void postPacketHandling(PacketUpdate packet) {}
  
  public void sendGUIUpdates(Container container, ICrafting iCrafting) {}
  
  public void getGUIUpdates(int channel, int value) {}
}
