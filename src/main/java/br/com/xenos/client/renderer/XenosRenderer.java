package br.com.xenos.client.renderer;

import br.com.xenos.Xenos;
import br.com.xenos.client.model.XenosModel;
import br.com.xenos.entity.XenosEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class XenosRenderer extends HumanoidMobRenderer<XenosEntity, XenosModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Xenos.MODID, "textures/entity/xenos.png");

    public XenosRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new XenosModel(pContext.bakeLayer(XenosModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(XenosEntity pEntity) {
        Xenos.LOGGER.info("Loading texture: " + TEXTURE);
        return TEXTURE;
    }
}