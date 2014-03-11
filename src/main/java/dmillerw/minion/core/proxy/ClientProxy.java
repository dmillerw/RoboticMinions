package dmillerw.minion.core.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import dmillerw.minion.RoboticMinions;
import dmillerw.minion.client.entity.EntityCamera;
import dmillerw.minion.client.helper.RenderHelper;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author dmillerw
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		EntityRegistry.registerModEntity(EntityCamera.class, "camera", 0, RoboticMinions.instance, 64, 64, true);

		MinecraftForge.EVENT_BUS.register(new RenderHelper());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

}
