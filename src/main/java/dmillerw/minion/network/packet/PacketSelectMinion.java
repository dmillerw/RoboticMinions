package dmillerw.minion.network.packet;

import dmillerw.minion.core.handler.MinionHandler;
import dmillerw.minion.entity.EntityMinion;
import dmillerw.minion.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author dmillerw
 */
public class PacketSelectMinion extends AbstractPacket {

	public static void select(EntityMinion minion) {
		PacketSelectMinion packet = new PacketSelectMinion(minion);
		packet.sendToServer();
	}

	private int target;

	public PacketSelectMinion() {

	}

	public PacketSelectMinion(Entity entity) {
		if (entity != null) {
			target = entity.getEntityId();
		} else {
			target = -1;
		}
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(target);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		target = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if (target != -1) {
			MinionHandler.selectMinion(player, (EntityMinion) player.worldObj.getEntityByID(target));
		} else {
			MinionHandler.deselectMinion(player);
		}
	}

}
