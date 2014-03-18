package dmillerw.minion.client.render;

import com.google.common.collect.Maps;
import dmillerw.minion.client.entity.EntityCamera;
import dmillerw.minion.client.helper.SkinHelper;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

/**
 * @author dmillerw
 */
public class RenderMinion extends RendererLivingEntity {

	public static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/steve.png");

	public static Map<String, AbstractClientPlayer> cachedSkins = Maps.newHashMap();

	public RenderMinion() {
		super(new ModelBiped(), 1F);
	}

	@Override
	public void doRender(EntityLivingBase entity, double x, double y, double z, float id, float fk) {
		if (EntityCamera.selectedMinion != null && entity.getEntityId() == EntityCamera.selectedMinion.getEntityId()) {
			//FIXME
		}

		super.doRender(entity, x, y, z, id, fk);
	}

	@Override
	protected boolean func_110813_b(EntityLivingBase entity) {
		return false;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return SkinHelper.getSkin("Notch");
	}

}
