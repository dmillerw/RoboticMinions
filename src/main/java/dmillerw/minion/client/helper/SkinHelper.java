package dmillerw.minion.client.helper;

import com.google.common.collect.Maps;
import dmillerw.minion.client.render.RenderMinion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

import java.util.Map;

/**
 * @author dmillerw
 */
public class SkinHelper {

	public static Map<String, ThreadDownloadImageData> downloadThreads = Maps.newHashMap();
	public static Map<String, ResourceLocation> skins = Maps.newHashMap();

	public static ResourceLocation getSkin(String player) {
		if (!player.isEmpty()) {
			skins.put(player, getLocationSkin(player));
			downloadThreads.put(player, getDownloadImageSkin(skins.get(player), player));
			return skins.get(player) != null ? skins.get(player) : RenderMinion.TEXTURE;
		}

		return RenderMinion.TEXTURE;
	}

	private static ThreadDownloadImageData getTextureSkin(String player) {
		return downloadThreads.get(player);
	}

	private static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation par0ResourceLocation, String par1Str) {
		return getDownloadImage(par0ResourceLocation, getSkinUrl(par1Str), RenderMinion.TEXTURE, new ImageBufferDownload());
	}

	private static ThreadDownloadImageData getDownloadImage(ResourceLocation par0ResourceLocation, String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer) {
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		Object object = texturemanager.getTexture(par0ResourceLocation);

		if (object == null) {
			object = new ThreadDownloadImageData(par1Str, par2ResourceLocation, par3IImageBuffer);
			texturemanager.loadTexture(par0ResourceLocation, (ITextureObject) object);
		}

		return (ThreadDownloadImageData) object;
	}

	private static String getSkinUrl(String par0Str) {
		return String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[]{StringUtils.stripControlCodes(par0Str)});
	}

	private static ResourceLocation getLocationSkin(String par0Str) {
		return new ResourceLocation("skins/" + StringUtils.stripControlCodes(par0Str));
	}

}
