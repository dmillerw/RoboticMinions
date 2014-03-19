package dmillerw.minion.client.render;

import dmillerw.minion.client.helper.SkinHelper;
import dmillerw.minion.client.model.ModelMinion;
import dmillerw.minion.entity.EntityMinion;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

/**
 * @author dmillerw
 */
public class RenderMinion extends RendererLivingEntity {

	public RenderMinion() {
		super(new ModelMinion(0.0F), 0.5F);
	}

	@Override
	protected boolean func_110813_b(EntityLivingBase entity) {
		return false;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return SkinHelper.getSkin(((EntityMinion) entity).getSkin());
	}

}
