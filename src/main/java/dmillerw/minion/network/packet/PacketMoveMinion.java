package dmillerw.minion.network.packet;

import dmillerw.minion.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author dmillerw
 */
public class PacketMoveMinion extends AbstractPacket {

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {

	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {

	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) {

	}

}
