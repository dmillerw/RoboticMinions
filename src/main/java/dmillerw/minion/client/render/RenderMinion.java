package dmillerw.minion.client.render;

import dmillerw.minion.client.model.ModelMinion;
import dmillerw.minion.lib.ModInfo;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

/**
 * @author dmillerw
 */
public class RenderMinion extends RendererLivingEntity {

	public static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/model/minion.png");

	public RenderMinion() {
		super(new ModelMinion(), 1F);
	}

	@Override
	protected boolean func_110813_b(EntityLivingBase entity) {
		return false;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}

}
