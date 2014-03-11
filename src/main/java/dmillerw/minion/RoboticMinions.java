package dmillerw.minion;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.minion.core.proxy.CommonProxy;
import dmillerw.minion.lib.ModInfo;
import dmillerw.minion.network.PacketPipeline;

/**
 * @author dmillerw
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class RoboticMinions {

	@Mod.Instance(ModInfo.ID)
	public static RoboticMinions instance;

	@SidedProxy(serverSide = ModInfo.SERVER, clientSide = ModInfo.CLIENT)
	public static CommonProxy proxy;

	public static PacketPipeline pipeline = new PacketPipeline();

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		pipeline.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		pipeline.postInit();
	}

}
