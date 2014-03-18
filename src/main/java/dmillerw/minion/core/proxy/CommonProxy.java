package dmillerw.minion.core.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import dmillerw.minion.RoboticMinions;
import dmillerw.minion.block.BlockHandler;
import dmillerw.minion.core.handler.GuiHandler;
import dmillerw.minion.entity.EntityMinion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

/**
 * @author dmillerw
 */
public class CommonProxy {

	/* INITIALIZERS */
	public void preInit(FMLPreInitializationEvent event) {
		BlockHandler.init();

		EntityRegistry.registerModEntity(EntityMinion.class, "minion", 1, RoboticMinions.instance, 64, 3, true);

		NetworkRegistry.INSTANCE.registerGuiHandler(RoboticMinions.instance, new GuiHandler());
	}

	public void init(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	/* MINION HANDLING */
	public void onEntityClicked(EntityPlayer player, EntityMinion minion, int button) {

	}

	public void onBlockClicked(EntityPlayer player, MovingObjectPosition block, int button) {

	}

}
