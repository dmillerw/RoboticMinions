package dmillerw.minion.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

import java.io.IOException;

/**
 * @author dmillerw
 */
public class VanillaPacketHelper {

	public static final int RANGE = 64;

	public static void sendToAllInRange(int dimension, int x, int y, int z, int range, Packet packet) {
		ServerConfigurationManager manager = MinecraftServer.getServer().getConfigurationManager();

		for (Object obj : manager.playerEntityList) {
			EntityPlayerMP player = (EntityPlayerMP) obj;

			if (player.getEntityWorld().provider.dimensionId == dimension && player.getDistance(x, y, z) <= range) {
				player.playerNetServerHandler.sendPacket(packet);
			}
		}
	}

	/**
	 * Writes a compressed NBTTagCompound to the specified buffer
	 */
	public static void writeNBTTagCompound(NBTTagCompound nbt, ByteBuf buffer) throws IOException {
		if (nbt == null) {
			buffer.writeShort(-1);
		} else {
			byte[] bytes = CompressedStreamTools.compress(nbt);
			buffer.writeShort((short) bytes.length);
			buffer.writeBytes(bytes);
		}
	}

	/**
	 * Reads a compressed NBTTagCompound from the specified buffer
	 */
	public static NBTTagCompound readNBTTagCompound(ByteBuf buffer) throws IOException {
		short length = buffer.readShort();

		if (length < 0) {
			return null;
		} else {
			byte[] bytes = new byte[length];
			buffer.readBytes(bytes);
			return CompressedStreamTools.decompress(bytes);
		}
	}

	/**
	 * Writes the ItemStack's ID (short), then size (byte), then damage, (short) to the specified buffer
	 */
	public static void writeItemStack(ItemStack stack, ByteBuf buffer) throws IOException {
		if (stack == null) {
			buffer.writeShort(-1);
		} else {
			buffer.writeShort(Item.getIdFromItem(stack.getItem()));
			buffer.writeByte(stack.stackSize);
			buffer.writeShort(stack.getItemDamage());
			NBTTagCompound nbt = null;

			if (stack.getItem().isDamageable() || stack.getItem().getShareTag()) {
				nbt = stack.stackTagCompound;
			}

			VanillaPacketHelper.writeNBTTagCompound(nbt, buffer);
		}
	}

	/**
	 * Reads an ItemStack from this buffer
	 */
	public static ItemStack readItemStack(ByteBuf buffer) throws IOException {
		ItemStack itemstack = null;
		short itemID = buffer.readShort();

		if (itemID >= 0) {
			byte size = buffer.readByte();
			short damage = buffer.readShort();
			itemstack = new ItemStack(Item.getItemById(itemID), size, damage);
			itemstack.stackTagCompound = VanillaPacketHelper.readNBTTagCompound(buffer);
		}

		return itemstack;
	}

}
