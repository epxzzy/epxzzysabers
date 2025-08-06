package com.epxzzy.createsaburs.screen.stance;


import com.epxzzy.createsaburs.CreateSaburs;
import com.epxzzy.createsaburs.networking.ModMessages;
import com.epxzzy.createsaburs.networking.packet.ServerboundKyberMenuTabChange;
import com.epxzzy.createsaburs.networking.packet.ServerboundRecolourItemPacket;
import com.epxzzy.createsaburs.screen.components.KyberModes;
import com.epxzzy.createsaburs.screen.components.KyberTabButton;
import com.epxzzy.createsaburs.screen.components.SliderWidget;
import com.epxzzy.createsaburs.utils.ColourConverter;
import com.epxzzy.createsaburs.utils.ModTags;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import javax.annotation.Nullable;

public class KyberStationStanceScreen extends AbstractContainerScreen<KyberStationStanceMenu> {
    private static final ResourceLocation STANCE_TEXTURE =
            new ResourceLocation(CreateSaburs.MOD_ID, "textures/gui/kyber_stance.png");

    private KyberTabButton RECOLOUR_TAB_BUTTON;
    private KyberTabButton STANCE_TAB_BUTTON;
    private KyberTabButton FLOURISH_TAB_BUTTON;


    private boolean widthTooNarrow = this.width < 379;
    private int TABLE_MODE = 0;
    //0 = recolour
    //1 = stance

    private float xMouse;
    private float yMouse;

    public void slotChanged() {

    }

    public KyberStationStanceScreen(KyberStationStanceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        pMenu.registerUpdateListener(this::containerChanged);
        pMenu.registerInputUpdateListener(this::TakeInput);

    }

    private void containerChanged() {
        //UpdateServerRecipe();


        //menu.craftSabur(HUE_SLIDER.getValueInt(), SAT_SLIDER.getValueInt(),LIT_SLIDER.getValueInt());
        //CreateSaburs.LOGGER.info("client kyber station change");

        return;
    }

    public void TakeInput() {

    }

    @Override
    protected void init() {
        super.init();
        //sloider = new ForgeSlider(this.leftPos + 10,this.topPos + 10, 150, 20, Component.empty(), Component.empty(), 0, 256, 0, 1, 1, false);
        //this.addWidget(this.LIST);
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;


        RECOLOUR_TAB_BUTTON = new KyberTabButton(KyberModes.RECOLOUR, 0,this.topPos+10, this.leftPos-45){
            @Override
            public void onPress() {
                SelectTab(0);
                super.onPress();
            }
        };

        STANCE_TAB_BUTTON= new KyberTabButton(KyberModes.STANCE, 1,this.topPos+40, this.leftPos-45){
                    @Override
                    public void onPress(){
                        SelectTab(1);
                        super.onPress();
                    }
                };

        FLOURISH_TAB_BUTTON= new KyberTabButton(KyberModes.FLOURISH, 2,this.topPos+70, this.leftPos-45){
            @Override
            public void onPress(){
                SelectTab(2);
                super.onPress();
            }
        };


        this.addWidget(RECOLOUR_TAB_BUTTON);
        this.addWidget(STANCE_TAB_BUTTON);
        this.addWidget(FLOURISH_TAB_BUTTON);
        STANCE_TAB_BUTTON.setStateTriggered(true);

    }

    public void UpdateServerRecipe() {
        //send packet to craft
    }


    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        UpdateServerRecipe();
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, STANCE_TEXTURE);
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2;

            guiGraphics.blit(STANCE_TEXTURE, x - 15, y, 0, 0, 215, imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderEntityInInventoryFollowsMouse(guiGraphics, this.leftPos + 125, this.topPos + 70, 30, (float)(this.leftPos + 125) - this.xMouse, (float)(this.topPos + 70 - 50) - this.yMouse, this.minecraft.player);

        //LIST.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);


        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
        this.xMouse = (float)mouseX;
        this.yMouse = (float)mouseY;

        RECOLOUR_TAB_BUTTON.renderWidget(guiGraphics,mouseX,mouseY,delta);
        STANCE_TAB_BUTTON.renderWidget(guiGraphics,mouseX,mouseY,delta);
        FLOURISH_TAB_BUTTON.renderWidget(guiGraphics,mouseX,mouseY,delta);


        guiGraphics.pose().popPose();
    }



    void SelectTab(int index){

        if(index==0){

            ModMessages.sendToServer(new ServerboundKyberMenuTabChange(0));
        }
        if(index==2){
            ModMessages.sendToServer(new ServerboundKyberMenuTabChange(2));
        }

    }

    public static void renderEntityInInventoryFollowsMouse(GuiGraphics pGuiGraphics, int pX, int pY, int pScale, float p_275604_, float p_275546_, LivingEntity pEntity) {
        float f = (float)Math.atan((double)(p_275604_ / 40.0F));
        float f1 = (float)Math.atan((double)(p_275546_ / 40.0F));
        // Forge: Allow passing in direct angle components instead of mouse position
        renderEntityInInventoryFollowsAngle(pGuiGraphics, pX, pY, pScale, f, f1, pEntity);
    }

    public static void renderEntityInInventoryFollowsAngle(GuiGraphics pGuiGraphics, int pX, int pY, int pScale, float angleXComponent, float angleYComponent, LivingEntity pEntity) {
        float f = angleXComponent;
        float f1 = angleYComponent;
        Quaternionf quaternionf = (new Quaternionf()).rotateZ((float)Math.PI);
        Quaternionf quaternionf1 = (new Quaternionf()).rotateX(f1 * 20.0F * ((float)Math.PI / 180F));
        quaternionf.mul(quaternionf1);
        float f2 = pEntity.yBodyRot;
        float f3 = pEntity.getYRot();
        float f4 = pEntity.getXRot();
        float f5 = pEntity.yHeadRotO;
        float f6 = pEntity.yHeadRot;
        pEntity.yBodyRot = 180.0F + f * 20.0F;
        pEntity.setYRot(180.0F + f * 40.0F);
        pEntity.setXRot(-f1 * 20.0F);
        pEntity.yHeadRot = pEntity.getYRot();
        pEntity.yHeadRotO = pEntity.getYRot();
        renderEntityInInventory(pGuiGraphics, pX, pY, pScale, quaternionf, quaternionf1, pEntity);
        pEntity.yBodyRot = f2;
        pEntity.setYRot(f3);
        pEntity.setXRot(f4);
        pEntity.yHeadRotO = f5;
        pEntity.yHeadRot = f6;
    }

    public static void renderEntityInInventory(GuiGraphics pGuiGraphics, int pX, int pY, int pScale, Quaternionf p_281880_, @Nullable Quaternionf pCameraOrientation, LivingEntity pEntity) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate((double)pX, (double)pY, 50.0D);
        pGuiGraphics.pose().mulPoseMatrix((new Matrix4f()).scaling((float)pScale, (float)pScale, (float)(-pScale)));
        pGuiGraphics.pose().mulPose(p_281880_);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (pCameraOrientation != null) {
            pCameraOrientation.conjugate();
            entityrenderdispatcher.overrideCameraOrientation(pCameraOrientation);
        }

        entityrenderdispatcher.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(pEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, pGuiGraphics.pose(), pGuiGraphics.bufferSource(), 15728880);
        });
        pGuiGraphics.flush();
        entityrenderdispatcher.setRenderShadow(true);
        pGuiGraphics.pose().popPose();
        Lighting.setupFor3DItems();
    }
}
