package dmillerw.minion.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMinionOld extends ModelBase {

	ModelRenderer legRight;
	ModelRenderer legLeft;
	ModelRenderer body;
	ModelRenderer faceRight;
	ModelRenderer faceLeft;
	ModelRenderer armRight;
	ModelRenderer armLeft;
	ModelRenderer faceMiddle;

	public ModelMinionOld() {
		textureWidth = 64;
		textureHeight = 32;

		legRight = new ModelRenderer(this, 0, 0);
		legRight.addBox(0F, 0F, -3F, 4, 7, 6);
		legRight.setRotationPoint(1F, 17F, 1F);
		legRight.setTextureSize(64, 32);
		legRight.mirror = true;
		setRotation(legRight, 0F, 0F, 0F);
		legLeft = new ModelRenderer(this, 0, 0);
		legLeft.addBox(0F, 0F, -3F, 4, 7, 6);
		legLeft.setRotationPoint(-5F, 17F, 1F);
		legLeft.setTextureSize(64, 32);
		legLeft.mirror = true;
		setRotation(legLeft, 0F, 0F, 0F);
		body = new ModelRenderer(this, 0, 13);
		body.addBox(0F, 0F, 0F, 12, 9, 10);
		body.setRotationPoint(-6F, 8F, -4F);
		body.setTextureSize(64, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		faceRight = new ModelRenderer(this, 20, 0);
		faceRight.mirror = true;
		faceRight.addBox(-4F, 0F, 0F, 4, 7, 4);
		faceRight.setRotationPoint(6F, 9F, -4F);
		faceRight.setTextureSize(64, 32);
		setRotation(faceRight, 0F, -0.3141593F, 0F);
		faceRight.mirror = false;
		faceLeft = new ModelRenderer(this, 36, 0);
		faceLeft.addBox(0F, 0F, 0F, 4, 7, 4);
		faceLeft.setRotationPoint(-6F, 9F, -4F);
		faceLeft.setTextureSize(64, 32);
		faceLeft.mirror = true;
		setRotation(faceLeft, 0F, 0.3141593F, 0F);
		armRight = new ModelRenderer(this, 52, 14);
		armRight.addBox(0F, -1F, -2F, 2, 10, 4);
		armRight.setRotationPoint(6F, 12F, 1F);
		armRight.setTextureSize(64, 32);
		armRight.mirror = true;
		setRotation(armRight, -0.6283185F, 0F, 0F);
		armLeft = new ModelRenderer(this, 52, 0);
		armLeft.addBox(0F, -1F, -2F, 2, 10, 4);
		armLeft.setRotationPoint(-8F, 12F, 1F);
		armLeft.setTextureSize(64, 32);
		armLeft.mirror = true;
		setRotation(armLeft, -0.6283185F, 0F, 0F);
		faceMiddle = new ModelRenderer(this, 37, 12);
		faceMiddle.addBox(0F, 0F, 0F, 5, 7, 2);
		faceMiddle.setRotationPoint(-2.5F, 9F, -5F);
		faceMiddle.setTextureSize(64, 32);
		faceMiddle.mirror = true;
		setRotation(faceMiddle, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		legRight.render(f5);
		legLeft.render(f5);
		body.render(f5);
		faceRight.render(f5);
		faceLeft.render(f5);
		armRight.render(f5);
		armLeft.render(f5);
		faceMiddle.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
	}

}
