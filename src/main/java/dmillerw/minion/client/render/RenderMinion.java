package dmillerw.minion.client.render;

import dmillerw.minion.client.entity.EntityCamera;
import dmillerw.minion.client.model.ModelMinion;
import dmillerw.minion.lib.ModInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class RenderMinion extends RendererLivingEntity {

	public static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/model/minion.png");

	public RenderMinion() {
		super(new ModelMinion(), 1F);
	}

	@Override
	public void doRender(EntityLivingBase entity, double x, double y, double z, float id, float fk) {
		super.doRender(entity, x, y, z, id, fk);

		//FIXME :(
		if (EntityCamera.selectedMinion != null && entity.getEntityId() == EntityCamera.selectedMinion.getEntityId()) {
			GL11.glPushMatrix();

			GL11.glTranslated(x, y, z);

			Tessellator t = Tessellator.instance;

			t.startDrawingQuads();
			t.setColorOpaque(255, 0, 0);

			t.addVertex(0.5, 0.5, 0);
			t.addVertex(0.5, 0, 0);
			t.addVertex(0, 0, 0);
			t.addVertex(0, 0.5, 0);

			t.draw();

			GL11.glPopMatrix();
		}
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
