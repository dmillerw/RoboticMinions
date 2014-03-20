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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class ItemSyringeGun extends Item {

	public static ItemStack getSyringeGun(String player) {
		ItemStack gun = new ItemStack(HandlerItem.itemSyringeGun, 1, 2);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("player", player);
		gun.setTagCompound(nbt);
		return gun;
	}

	public static ItemStack getSyringeGun(int mob) {
		ItemStack gun = new ItemStack(HandlerItem.itemSyringeGun, 1, 2);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("mob", mob);
		gun.setTagCompound(nbt);
		return gun;
	}

	public static boolean hasContents(ItemStack stack) {
		if (!stack.hasTagCompound() || stack.getItemDamage() != 2) {
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

	private IIcon syringeGunEmpty;
	private IIcon syringeGun;
	private IIcon syringeGunOverlay;

	public ItemSyringeGun() {
		super();

		setMaxDamage(0);
		setMaxStackSize(1);
		setHasSubtypes(true);
		setCreativeTab(ModCreativeTab.TAB);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && player.isSneaking()) {
			if (!hasContents(stack) && (player.inventory.hasItemStack(new ItemStack(HandlerItem.itemVial, 1, 0)) || player.capabilities.isCreativeMode)) {
				if (!player.capabilities.isCreativeMode) {
					ItemVial.consumeVial(player);
				}
				player.attackEntityFrom(DamageSource.generic, 1);
				return getSyringeGun(player.getCommandSenderName());
			} else if (hasContents(stack)) {
				player.dropPlayerItemWithRandomChoice(ItemVial.getVial(stack), false);
				return new ItemStack(HandlerItem.itemSyringeGun, 1, 0);
			}
		}

		return stack;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
		if (!player.worldObj.isRemote) {
			if (!hasContents(stack) && (player.inventory.hasItemStack(new ItemStack(HandlerItem.itemVial, 1, 0)) || player.capabilities.isCreativeMode)) {
				if (!player.capabilities.isCreativeMode) {
					ItemVial.consumeVial(player);
				}

				((EntityPlayerMP) player).updateHeldItem();

				if (!(entity instanceof EntityMinion)) {
					if (entity instanceof EntityPlayer) {
						player.setCurrentItemOrArmor(0, getSyringeGun(player.getCommandSenderName()));
					} else {
						player.setCurrentItemOrArmor(0, getSyringeGun(EntityList.getEntityID(entity)));
					}

					entity.attackEntityFrom(DamageSource.generic, 1);
					return true;
				}

				return false;
			}
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
		if (pass == 1) {
			if (damage == 2) {
				return syringeGunOverlay;
			}
		}

		return damage == 1 ? syringeGun : syringeGunEmpty;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack stack, int pass) {
		return (pass == 1 && stack.getItemDamage() == 2) ? getColor(stack) : 0xFFFFFF; // Temporary
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register) {
		syringeGunEmpty = register.registerIcon(ModInfo.RESOURCE_PREFIX + "syringe_gun_empty");
		syringeGun = register.registerIcon(ModInfo.RESOURCE_PREFIX + "syringe_gun");
		syringeGunOverlay = register.registerIcon(ModInfo.RESOURCE_PREFIX + "syringe_gun_overlay");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + (stack.getItemDamage() == 2 ? "full" : stack.getItemDamage() == 1 ? "empty_vial" : "empty");
	}

}
