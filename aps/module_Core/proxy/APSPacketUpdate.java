package aps.module_Core.proxy;

import buildcraft.core.network.PacketPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class APSPacketUpdate
  extends APSPacket
{
  private int packetId;
  public int posX;
  public int posY;
  public int posZ;
  public PacketPayload payload;
  
  public APSPacketUpdate() {}
  
  public APSPacketUpdate(int packetId, PacketPayload payload)
  {
    this(packetId, 0, 0, 0, payload);
  }
  
  public APSPacketUpdate(int packetId, int posX, int posY, int posZ, PacketPayload payload) {
    this(packetId);
    
    this.posX = posX;
    this.posY = posY;
    this.posZ = posZ;
    
    this.payload = payload;
  }
  
  public APSPacketUpdate(int packetId) {
    this.packetId = packetId;
    this.isChunkDataPacket = true;
  }
  

  public void writeData(DataOutputStream data)
    throws IOException
  {
    data.writeInt(this.posX);
    data.writeInt(this.posY);
    data.writeInt(this.posZ);
    

    if (this.payload == null) {
      data.writeInt(0);
      data.writeInt(0);
      data.writeInt(0);
      return;
    }
    
    data.writeInt(this.payload.intPayload.length);
    data.writeInt(this.payload.floatPayload.length);
    data.writeInt(this.payload.stringPayload.length);
    
    for (int intData : this.payload.intPayload)
      data.writeInt(intData);
    for (float floatData : this.payload.floatPayload)
      data.writeFloat(floatData);
    for (String stringData : this.payload.stringPayload) {
      data.writeUTF(stringData);
    }
  }
  
  public void readData(DataInputStream data)
    throws IOException
  {
    this.posX = data.readInt();
    this.posY = data.readInt();
    this.posZ = data.readInt();
    
    this.payload = new PacketPayload();
    
    this.payload.intPayload = new int[data.readInt()];
    this.payload.floatPayload = new float[data.readInt()];
    this.payload.stringPayload = new String[data.readInt()];
    
    for (int i = 0; i < this.payload.intPayload.length; i++)
      this.payload.intPayload[i] = data.readInt();
    for (int i = 0; i < this.payload.floatPayload.length; i++)
      this.payload.floatPayload[i] = data.readFloat();
    for (int i = 0; i < this.payload.stringPayload.length; i++) {
      this.payload.stringPayload[i] = data.readUTF();
    }
  }
  
  public int getID()
  {
    return this.packetId;
  }
}
