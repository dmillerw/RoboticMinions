package dmillerw.minion.network.packet.server;

import dmillerw.minion.core.handler.MinionHandler;
import dmillerw.minion.entity.EntityMinion;
import dmillerw.minion.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

/**
 * @author dmillerw
 */
public class PacketMoveMinion extends AbstractPacket {

	public static void move(double x, double y, double z) {
		PacketMoveMinion packet = new PacketMoveMinion(x, y, z);
		packet.sendToServer();
	}

	private double x;
	private double y;
	private double z;

	public PacketMoveMinion() {

	}

	public PacketMoveMinion(double x, double y, double z) {
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
		EntityMinion entity = MinionHandler.getSelectedMinion(player);

		if (entity != null) {
			entity.setTarget(Vec3.createVectorHelper(x, y, z));
		}
	}

}
