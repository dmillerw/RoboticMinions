package dmillerw.minion.core.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import dmillerw.minion.RoboticMinions;
import dmillerw.minion.block.BlockHandler;
import dmillerw.minion.core.handler.GuiHandler;

/**
 * @author dmillerw
 */
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		BlockHandler.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(RoboticMinions.instance, new GuiHandler());
	}

	public void init(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {

	}

}
