package dmillerw.minion.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.minion.core.ModCreativeTab;
import dmillerw.minion.entity.EntityMinion;
import dmillerw.minion.lib.ModInfo;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class ItemSyringeGun extends Item {

	private IIcon syringeGunEmpty;

	public ItemSyringeGun() {
		super();

		setMaxDamage(0);
		setMaxStackSize(1);
		setHasSubtypes(true);
		setCreativeTab(ModCreativeTab.TAB);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.isSneaking() && (player.inventory.hasItemStack(new ItemStack(HandlerItem.itemVial, 1, 0)) || player.capabilities.isCreativeMode)) {
			if (!player.capabilities.isCreativeMode) {
				ItemVial.consumeVial(player);
			}
			player.attackEntityFrom(DamageSource.generic, 1);

			ItemStack vial = ItemVial.getVial(player.getCommandSenderName());
			if (!player.inventory.addItemStackToInventory(vial)) {
				player.dropPlayerItemWithRandomChoice(vial, false);
			}
		}

		return stack;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
		if ((player.inventory.hasItemStack(new ItemStack(HandlerItem.itemVial, 1, 0)) || player.capabilities.isCreativeMode)) {
			if (!(entity instanceof EntityMinion)) {
				if (!player.capabilities.isCreativeMode) {
					ItemVial.consumeVial(player);
				}

				ItemStack vial = null;
				if (entity instanceof EntityPlayer) {
					vial = ItemVial.getVial(player.getCommandSenderName());
				} else {
					vial = ItemVial.getVial(EntityList.getEntityID(entity));
				}

				entity.attackEntityFrom(DamageSource.generic, 1);

				if (!player.inventory.addItemStackToInventory(vial)) {
					player.dropPlayerItemWithRandomChoice(vial, false);
				}
				return true;
			}

			return false;
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int damage) {
		return syringeGunEmpty;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register) {
		syringeGunEmpty = register.registerIcon(ModInfo.RESOURCE_PREFIX + "syringe_gun_empty");
	}

}
