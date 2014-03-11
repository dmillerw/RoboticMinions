package dmillerw.minion.block;

import dmillerw.minion.client.entity.EntityCamera;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class BlockController extends Block {

	public BlockController() {
		super(Material.iron);

		setHardness(5F);
		setResistance(5F);
		setCreativeTab(CreativeTabs.tabBrewing);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fx, float fy, float fz) {
		if (world.isRemote) {
			EntityCamera.createCamera();
		}

		return !player.isSneaking();
	}

}
