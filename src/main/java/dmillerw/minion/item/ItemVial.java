package dmillerw.minion.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.minion.core.ModCreativeTab;
import dmillerw.minion.lib.ModInfo;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * @author dmillerw
 */
public class ItemVial extends Item {

	public static ItemStack getVial(String player) {
		ItemStack vial = new ItemStack(HandlerItem.itemVial, 1, 1);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("player", player);
		vial.setTagCompound(nbt);
		return vial;
	}

	public static ItemStack getVial(int mob) {
		ItemStack vial = new ItemStack(HandlerItem.itemVial, 1, 1);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("mob", mob);
		vial.setTagCompound(nbt);
		return vial;
	}

	public static boolean hasContents(ItemStack stack) {
		if (!stack.hasTagCompound() || stack.getItemDamage() != 1 || stack.getItemDamage() != 1) {
			return false;
		}

		return (stack.getTagCompound().hasKey("player") || stack.getTagCompound().hasKey("mob"));
	}

	public static int getColor(ItemStack stack) {
		if (!hasContents(stack)) {
			return 0xFFFFFF;
		}

		if (stack.getTagCompound().hasKey("player")) {
			return stack.getTagCompound().getString("player").hashCode();
		} else if (stack.getTagCompound().hasKey("mob")) {
			EntityList.EntityEggInfo entityegginfo = (EntityList.EntityEggInfo) EntityList.entityEggs.get(Integer.valueOf(stack.getTagCompound().getInteger("mob")));
			return entityegginfo != null ? entityegginfo.primaryColor : 16777215;
		} else {
			// Never should get here
			return 0xFFFFFF;
		}
	}

	public static void consumeVial(EntityPlayer player) {
		for (ItemStack stack : player.inventory.mainInventory) {
			if (stack != null && stack.isItemEqual(new ItemStack(HandlerItem.itemVial, 1, 0))) {
				player.inventory.markDirty();
				stack.stackSize--;
				return;
			}
		}
	}

	private IIcon vial;
	private IIcon vialOverlay;

	public ItemVial() {
		super();

		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(ModCreativeTab.TAB);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean idk) {
		if (hasContents(stack)) {
			if (stack.getTagCompound().hasKey("player")) {
				list.add("Vial of " + stack.getTagCompound().getString("player") + "'s DNA");
			} else if (stack.getTagCompound().hasKey("mob")) {
				String mob = EntityList.getStringFromID(stack.getTagCompound().getInteger("mob"));
				list.add("Vial of " + mob + " DNA");
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
		return (pass == 1 && damage == 1) ? vialOverlay : vial;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack stack, int pass) {
		return (pass == 1 && stack.getItemDamage() == 1) ? getColor(stack) : 0xFFFFFF;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register) {
		vial = register.registerIcon(ModInfo.RESOURCE_PREFIX + "vial");
		vialOverlay = register.registerIcon(ModInfo.RESOURCE_PREFIX + "vial_overlay");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + (stack.getItemDamage() == 1 ? "full" : "empty");
	}

}
