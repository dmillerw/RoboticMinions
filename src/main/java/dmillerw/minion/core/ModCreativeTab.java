package dmillerw.minion.core;

import dmillerw.minion.block.HandlerBlock;
import dmillerw.minion.lib.ModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * @author dmillerw
 */
public class ModCreativeTab extends CreativeTabs {

	public static final CreativeTabs TAB = new ModCreativeTab(ModInfo.ID);

	public ModCreativeTab(String label) {
		super(label);
	}

	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(HandlerBlock.blockController);
	}

}
