package com.epxzzy.createsaburs.misc;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class KyberTabButton extends StateSwitchingButton {
    protected static final ResourceLocation texture = new ResourceLocation("textures/gui/recipe_book.png");

    private final KyberModes category;
    private static final float ANIMATION_TIME = 15.0F;
    private float animationTime;

    public KyberTabButton(KyberModes pCategory) {
        super(0, 0, 35, 27, false);
        this.category = pCategory;
        this.initTextureValues(153, 2, 35, 0, texture);
    }

    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.animationTime > 0.0F) {
            float f = 1.0F + 0.1F * (float)Math.sin((double)(this.animationTime / 15.0F * (float)Math.PI));
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate((float)(this.getX() + 8), (float)(this.getY() + 12), 0.0F);
            pGuiGraphics.pose().scale(1.0F, f, 1.0F);
            pGuiGraphics.pose().translate((float)(-(this.getX() + 8)), (float)(-(this.getY() + 12)), 0.0F);
        }

        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.disableDepthTest();
        int i = this.xTexStart;
        int j = this.yTexStart;
        if (this.isStateTriggered) {
            i += this.xDiffTex;
        }

        if (this.isHoveredOrFocused()) {
            j += this.yDiffTex;
        }

        int k = this.getX();
        if (this.isStateTriggered) {
            k -= 2;
        }

        pGuiGraphics.blit(this.resourceLocation, k, this.getY(), i, j, this.width, this.height);
        RenderSystem.enableDepthTest();
        this.renderIcon(pGuiGraphics, minecraft.getItemRenderer());
        if (this.animationTime > 0.0F) {
            pGuiGraphics.pose().popPose();
            this.animationTime -= pPartialTick;
        }

    }

    private void renderIcon(GuiGraphics pGuiGraphics, ItemRenderer pItemRenderer) {
        List<ItemStack> list = this.category.getIconItems();
        int i = this.isStateTriggered ? -2 : 0;
        if (list.size() == 1) {
            pGuiGraphics.renderFakeItem(list.get(0), this.getX() + 9 + i, this.getY() + 5);
        } else if (list.size() == 2) {
            pGuiGraphics.renderFakeItem(list.get(0), this.getX() + 3 + i, this.getY() + 5);
            pGuiGraphics.renderFakeItem(list.get(1), this.getX() + 14 + i, this.getY() + 5);
        }

    }

    public KyberModes getCategory() {
        return this.category;
    }

}
