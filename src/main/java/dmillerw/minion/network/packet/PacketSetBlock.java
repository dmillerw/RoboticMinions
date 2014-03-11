package dmillerw.minion.network.packet;

import dmillerw.minion.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author dmillerw
 */
public class PacketSetBlock extends AbstractPacket {

	private int x;
	private int y;
	private int z;

	private int id;
	private int meta;

	public PacketSetBlock() {

	}

	public PacketSetBlock(int x, int y, int z, Block block, int meta) {
		this(x, y, z, Block.getIdFromBlock(block), meta);
	}

	public PacketSetBlock(int x, int y, int z, int id, int meta) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = id;
		this.meta = meta;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		buffer.writeInt(id);
		buffer.writeInt(meta);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		id = buffer.readInt();
		meta = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		player.worldObj.setBlock(x, y, z, Block.getBlockById(id), meta, 3);
	}

}
