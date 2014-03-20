package dmillerw.minion.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

/**
 * @author dmillerw
 */
public class HandlerBlock {

	public static Block blockController;

	public static void init() {
		blockController = new BlockController().setBlockName("controller");
		GameRegistry.registerBlock(blockController, "controller");
	}

}
