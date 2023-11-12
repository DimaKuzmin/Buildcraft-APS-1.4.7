package aps.module_Core;

import aps.BuildcraftAPS;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;





















public abstract class BlockAPSMeta
  extends BlockContainer
{
  int[] texes = new int[6];
  
  protected BlockAPSMeta(int i, Material m)
  {
    super(i, m);
    
    setHardness(0.5F);
  }
  



  public abstract TileEntity getBlockEntity(int paramInt);
  


  public TileEntity createNewTileEntity(World world, int md)
  {
    return getBlockEntity(md);
  }
  
  public TileEntity createNewTileEntity(World world)
  {
    return null;
  }
  


  public String getTextureFile()
  {
    return BuildcraftAPS.imageFilesRoot + "APSBlockTexes.png";
  }
  
  public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
  {
    int meta = iblockaccess.getBlockMetadata(i, l, k);
    TileEntity Ent = iblockaccess.getBlockTileEntity(i, j, k);
    
    if ((Ent instanceof TileEntityAPS)) {
      TileEntityAPS apsEnt = (TileEntityAPS)Ent;
      this.texes = ((int[])apsEnt.texes.clone());
      return this.texes[l];
    }
    return getBlockTextureFromSideAndMetadata(l, meta);
  }
  

  public int getBlockTextureFromSideAndMetadata(int i, int j)
  {
    TileEntity Ent = getBlockEntity(j);
    if (Ent != null) return ((TileEntityAPS)Ent).getTexture(i);
    return 0;
  }
  

  public int getBlockTextureFromSide(int side)
  {
    return this.texes[side];
  }
  


  public void breakBlock(World world, int i, int j, int k, int l, int m)
  {
    TileEntity Ent = world.getBlockTileEntity(i, j, k);
    if ((Ent instanceof TileEntityAPS)) ((TileEntityAPS)Ent).kill();
    super.breakBlock(world, i, j, k, l, m);
  }
  
  public int damageDropped(int i)
  {
    return i;
  }
  
  public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float m, float n, float o)
  {
    if (entityplayer.isSneaking()) { return false;
    }
    TileEntityAPS Ent = (TileEntityAPS)getBlockEntity(world.getBlockMetadata(i, j, k));
    if ((Ent instanceof TileEntityAPS))
    {
      if (Ent.hasGUI)
      {
        if ((!world.isRemote) && (Ent.GuiID > 0))
        {
          entityplayer.openGui(BuildcraftAPS.instance, Ent.GuiID, world, i, j, k); }
        return true;
      }
      return false;
    }
    

    return false;
  }
}
