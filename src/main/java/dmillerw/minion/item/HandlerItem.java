package dmillerw.minion.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * @author dmillerw
 */
public class HandlerItem {

	public static Item itemVial;
	public static Item itemSyringeGun;

	public static void init() {
		itemVial = new ItemVial().setUnlocalizedName("vial");
		GameRegistry.registerItem(itemVial, "vial");

		itemSyringeGun = new ItemSyringeGun().setUnlocalizedName("syringe_gun");
		GameRegistry.registerItem(itemSyringeGun, "syringe_gun");
	}

}
