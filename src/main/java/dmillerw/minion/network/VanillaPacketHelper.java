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
import net.minecraft.util.Vec3;

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
			buffer.writeBoolean(false);
		} else {
			buffer.writeBoolean(true);
			byte[] bytes = CompressedStreamTools.compress(nbt);
			buffer.writeShort((short) bytes.length);
			buffer.writeBytes(bytes);
		}
	}

	/**
	 * Reads a compressed NBTTagCompound from the specified buffer
	 */
	public static NBTTagCompound readNBTTagCompound(ByteBuf buffer) throws IOException {
		if (!buffer.readBoolean()) {
			return null;
		}

		byte[] bytes = new byte[buffer.readShort()];
		buffer.readBytes(bytes);
		return CompressedStreamTools.decompress(bytes);
	}

	/**
	 * Writes the ItemStack's ID (short), then size (byte), then damage, (short) to the specified buffer
	 */
	public static void writeItemStack(ItemStack stack, ByteBuf buffer) throws IOException {
		if (stack == null) {
			buffer.writeBoolean(false);
		} else {
			buffer.writeBoolean(true);
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
	 * Reads an ItemStack from the specified buffer
	 */
	public static ItemStack readItemStack(ByteBuf buffer) throws IOException {
		if (!buffer.readBoolean()) {
			return null;
		}

		short itemID = buffer.readShort();
		byte size = buffer.readByte();
		short damage = buffer.readShort();
		ItemStack stack = new ItemStack(Item.getItemById(itemID), size, damage);
		stack.stackTagCompound = VanillaPacketHelper.readNBTTagCompound(buffer);

		return stack;
	}

	/**
	 * Writes a vanilla Vec3 object to the specified buffer
	 */
	public static void writeVec3(Vec3 vec, ByteBuf buffer) {
		if (vec == null) {
			buffer.writeBoolean(false);
		} else {
			buffer.writeBoolean(true);
			buffer.writeDouble(vec.xCoord);
			buffer.writeDouble(vec.yCoord);
			buffer.writeDouble(vec.zCoord);
		}
	}

	/**
	 * Reads a vanilla Vec3 object from this buffer
	 */
	public static Vec3 readVec3(ByteBuf buffer) {
		if (!buffer.readBoolean()) {
			return null;
		}

		return Vec3.createVectorHelper(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}

}
