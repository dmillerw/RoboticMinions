package dmillerw.minion.network.packet;

import dmillerw.minion.entity.EntityMinion;
import dmillerw.minion.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author dmillerw
 */
public class PacketSpawnMinion extends AbstractPacket {

	public static void spawnMinion(double x, double y, double z) {
		PacketSpawnMinion packet = new PacketSpawnMinion(x, y, z);
		packet.sendToServer();
	}

	private double x;
	private double y;
	private double z;

	public PacketSpawnMinion() {

	}

	public PacketSpawnMinion(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		EntityMinion minion = new EntityMinion(player.worldObj);
		minion.setPosition(x, y, z);
		player.worldObj.spawnEntityInWorld(minion);
	}

}
