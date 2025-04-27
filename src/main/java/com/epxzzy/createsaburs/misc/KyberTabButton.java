package com.epxzzy.createsaburs.misc;

import com.epxzzy.createsaburs.createsaburs;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KyberTabButton extends AbstractButton {
    protected static final ResourceLocation texture = new ResourceLocation("textures/gui/recipe_book.png");

    private final KyberModes category;
    private static final float ANIMATION_TIME = 15.0F;
    private float animationTime;
    private boolean isStateTriggered;
    private int xTexStart;
    private int yTexStart;
    private int xDiffTex;
    private int yDiffTex;
    public int tabID;
    private ResourceLocation resourceLocation;

    public KyberTabButton(KyberModes pCategory,int tabID,int toppos,int leftpos) {
        //super(0, 0, 35, 27, false);
        super(leftpos,toppos, 35, 27, Component.literal(""));
        //this.x=x-45;
        this.tabID = tabID;
        this.category = pCategory;
        this.visible = true;
        this.initTextureValues(153, 2, 35, 0, texture);
    }

    public void initTextureValues(int pXTexStart, int pYTexStart, int pXDiffTex, int pYDiffTex, ResourceLocation pResourceLocation) {
        this.xTexStart = pXTexStart;
        this.yTexStart = pYTexStart;
        this.xDiffTex = pXDiffTex;
        this.yDiffTex = pYDiffTex;
        this.resourceLocation = pResourceLocation;
    }
    @Override
    public void onPress() {
        createsaburs.LOGGER.info("whatss sup biatches "+this.isStateTriggered);
    }

    public void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if(this.isMouseOver(pMouseX,pMouseY)) {
            //createsaburs.LOGGER.info("being rendered properly");
        }
        /*
        if (this.animationTime > 0.0F) {
            float f = 1.0F + 0.1F * (float) Math.sin((double) (this.animationTime / 15.0F * (float) Math.PI));
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate((float) (this.getX() + 8), (float) (this.getY() + 12), 0.0F);
            pGuiGraphics.pose().scale(1.0F, f, 1.0F);
            pGuiGraphics.pose().translate((float) (-(this.getX() + 8)), (float) (-(this.getY() + 12)), 0.0F);
        }

         */

        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.disableDepthTest();
        int i = this.xTexStart;
        int j = this.yTexStart;

        if (this.isStateTriggered()) {
            i += this.xDiffTex;
        }

        if (this.isHoveredOrFocused()||this.isMouseOver(pMouseX,pMouseY)) {
            j += this.yDiffTex;
        }
        int k = this.getX();

        if (this.isStateTriggered) {
            k -= 2;
        }



        pGuiGraphics.blit(this.resourceLocation, k, this.getY(), i, j, this.width, this.height);
        RenderSystem.enableDepthTest();
        this.renderIcon(pGuiGraphics, minecraft.getItemRenderer());
        pGuiGraphics.renderOutline(this.getX(),this.getY(),this.width, this.height,ColourConverter.portedRGBtoDecimal(new int[]{255,0,0}));
        /*
        if (this.animationTime > 0.0F) {
            pGuiGraphics.pose().popPose();
            this.animationTime -= pPartialTick;
        }

         */

    }


    private void renderIcon(GuiGraphics pGuiGraphics, ItemRenderer pItemRenderer) {
        List<ItemStack> list = this.category.getIconItems();
        int i = this.isStateTriggered ? -2 : 0;
        if (list.size() == 1) {
            pGuiGraphics.renderFakeItem(list.get(0),this.getX() + 9 + i, this.getY() + 5);
        } else if (list.size() == 2) {
            pGuiGraphics.renderFakeItem(list.get(0), this.getX() + -3 + i, this.getY() + 5);
            pGuiGraphics.renderFakeItem(list.get(1), this.getX() + 10 + i, this.getY() + 5);

        } else if (list.size() == 3) {
            pGuiGraphics.renderFakeItem(list.get(2), this.getX() + 13 + i, this.getY() + 10);
            pGuiGraphics.renderFakeItem(list.get(1), this.getX() + i + 8, this.getY() + 5);
            pGuiGraphics.renderFakeItem(list.get(0), this.getX() + i + 3, this.getY());

        }
    }

    public KyberModes getCategory() {
        return this.category;
    }
    public void setStateTriggered(boolean pTriggered) {
        this.isStateTriggered = pTriggered;
    }

    public boolean isStateTriggered() {
        return this.isStateTriggered;
    }
    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return super.isMouseOver(pMouseX, pMouseY);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }

}
