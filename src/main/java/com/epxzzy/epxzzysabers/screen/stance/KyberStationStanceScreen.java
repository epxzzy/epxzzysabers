package com.epxzzy.epxzzysabers.screen.stance;


import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.networking.ModMessages;
import com.epxzzy.epxzzysabers.networking.packet.ServerboundKyberMenuTabChange;
import com.epxzzy.epxzzysabers.rendering.playerposerenderers.BladeStance;
import com.epxzzy.epxzzysabers.screen.components.KyberModes;
import com.epxzzy.epxzzysabers.screen.components.KyberTabButton;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import javax.annotation.Nullable;
import java.util.List;

public class KyberStationStanceScreen extends AbstractContainerScreen<KyberStationStanceMenu> {
    private static final ResourceLocation STANCE_TEXTURE =
            new ResourceLocation(epxzzySabers.MOD_ID, "textures/gui/kyber_stance.png");
    private static final ResourceLocation LOOMSHISH =
            new ResourceLocation("textures/gui/container/loom.png");


    private KyberTabButton RECOLOUR_TAB_BUTTON;
    private KyberTabButton STANCE_TAB_BUTTON;
    //private KyberTabButton FLOURISH_TAB_BUTTON;
    public boolean displayStances;
    private List<BladeStance> resultStance;

    private boolean widthTooNarrow = this.width < 379;
    private int TABLE_MODE = 0;
    //0 = recolour
    //1 = stance
    private int startRow;
    private boolean hasMaxPatterns = true;

    private float xMouse;
    private float yMouse;
    public LocalPlayer PLAYERpreview;


    public void slotChanged() {

    }

    public KyberStationStanceScreen(KyberStationStanceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        pMenu.registerUpdateListener(this::containerChanged);
        pMenu.registerInputUpdateListener(this::TakeInput);

    }

    private void containerChanged() {
        UpdateServerRecipe();

        ItemStack itemstack = this.menu.resultSlot.getItem();
        if (itemstack.isEmpty()) {
            this.resultStance = null;
        } else {
            //this.resultStance - BladeStance.//= BannerBlockEntity.createPatterns(((BannerItem)itemstack.getItem()).getColor(), BannerBlockEntity.getItemPatterns(itemstack));
        }

        ItemStack itemstack1 = this.menu.input_slot.getItem();
        CompoundTag compoundtag = BlockItem.getBlockEntityData(itemstack1);
        this.hasMaxPatterns = compoundtag != null && compoundtag.contains("Patterns", 9) && !itemstack1.isEmpty() && compoundtag.getList("Patterns", 10).size() >= 6;
        if (this.hasMaxPatterns) {
            this.resultStance = null;
        }

        this.displayStances = !itemstack1.isEmpty() && !this.hasMaxPatterns && !this.menu.selectableStances.isEmpty();



        //menu.craftSabur(HUE_SLIDER.getValueInt(), SAT_SLIDER.getValueInt(),LIT_SLIDER.getValueInt());
        //epxzzySabers.LOGGER.info("client kyber station change");

        return;
    }

    public void TakeInput() {
        UpdateServerRecipe();
    }

    @Override
    protected void init() {
        super.init();
        //sloider = new ForgeSlider(this.leftPos + 10,this.topPos + 10, 150, 20, Component.empty(), Component.empty(), 0, 256, 0, 1, 1, false);
        //this.addWidget(this.LIST);
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;


        RECOLOUR_TAB_BUTTON = new KyberTabButton(KyberModes.RECOLOUR, 0, this.topPos + 10, this.leftPos - 45) {
            @Override
            public void onPress() {
                SelectTab(0);
                super.onPress();
            }
        };

        STANCE_TAB_BUTTON = new KyberTabButton(KyberModes.STANCE, 1, this.topPos + 40, this.leftPos - 45) {
            @Override
            public void onPress() {
                SelectTab(1);
                super.onPress();
            }
        };
        /*
        FLOURISH_TAB_BUTTON = new KyberTabButton(KyberModes.FLOURISH, 2, this.topPos + 70, this.leftPos - 45) {
            @Override
            public void onPress() {
                SelectTab(2);
                super.onPress();
            }
        };

         */


        this.addWidget(RECOLOUR_TAB_BUTTON);
        this.addWidget(STANCE_TAB_BUTTON);
        //this.addWidget(FLOURISH_TAB_BUTTON);
        STANCE_TAB_BUTTON.setStateTriggered(true);
        PLAYERpreview = new LocalPlayer(this.getMinecraft(), this.minecraft.level, this.minecraft.getConnection(), null, null, false, false);
        PLAYERpreview.setCustomNameVisible(true);
        //PLAYERpreview.setShiftKeyDown(true);

        UpdateServerRecipe();
    }

    public void UpdateServerRecipe() {
        //send packet to craft
        PLAYERpreview.setItemInHand(InteractionHand.MAIN_HAND, this.menu.resultSlot.getItem());

        //PLAYERpreview.setShiftKeyDown(this.menu.resultSlot.hasItem());
        //PLAYERpreview.setShiftKeyDown(true);

        setShiftForThyPlayer(PLAYERpreview, true);
        //PLAYERpreview.setShiftKeyDown(false);

        PLAYERpreview.startUsingItem(InteractionHand.MAIN_HAND);
        //epxzzySabers.LOGGER.debug("is shifty downy {}", PLAYERpreview.isShiftKeyDown());
    }
    public static void setShiftForThyPlayer(LocalPlayer pPlayer, boolean value){
        Input inpoo = new Input();
        inpoo.shiftKeyDown = true;
        pPlayer.input = inpoo;
    }
    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        int i = this.totalRowCount() - 4;
        return true;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (this.displayStances) {
            int i = this.leftPos + 27; //+ 30;
            int j = this.topPos + 8;

            for (int k = 0; k < 4; ++k) {
                for (int l = 0; l < 4; ++l) {
                    double d0 = pMouseX - (double) (i + l * 14);
                    double d1 = pMouseY - (double) (j + k * 14);
                    int i1 = k + this.startRow;
                    int j1 = i1 * 4 + l;
                    if (d0 >= 0.0D && d1 >= 0.0D && d0 < 14.0D && d1 < 14.0D && this.menu.clickMenuButton(this.minecraft.player, j1)) {
                        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_LOOM_SELECT_PATTERN, 1.0F));
                        this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, j1);
                        UpdateServerRecipe();
                        return true;
                    }
                }
            }
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, STANCE_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(STANCE_TEXTURE, x - 15, y, 0, 0, 215, imageHeight);
        if (this.resultStance != null && !this.hasMaxPatterns) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate((float) (i + 139), (float) (j + 52), 0.0F);
            guiGraphics.pose().scale(24.0F, -24.0F, 1.0F);
            guiGraphics.pose().translate(0.5F, 0.5F, 0.5F);
            float f = 0.6666667F;
            guiGraphics.pose().scale(0.6666667F, -0.6666667F, -0.6666667F);
            guiGraphics.pose().popPose();
            guiGraphics.flush();
        } else if (this.hasMaxPatterns) {
            guiGraphics.blit(LOOMSHISH, i + this.menu.resultSlot.x - 2, j + this.menu.resultSlot.y - 2, this.imageWidth, 17, 17, 16);
        }
        if (this.displayStances) {
            int l2 = i + 27;
            int l = j + 8; //+ 13;
            List<BladeStance> list = this.menu.getSelectableStances();

            label64:
            for (int i1 = 0; i1 < 4; ++i1) {
                for (int j1 = 0; j1 < 4; ++j1) {
                    int k1 = i1 + this.startRow;
                    int l1 = k1 * 4 + j1;
                    if (l1 >= list.size()) {
                        break label64;
                    }

                    int i2 = l2 + j1 * 14;
                    int j2 = l + i1 * 14;
                    boolean flag = pMouseX >= i2 && pMouseY >= j2 && pMouseX < i2 + 14 && pMouseY < j2 + 14;
                    int k2;
                    if (l1 == this.menu.getSelectedStanceIndex()) {
                        k2 = this.imageHeight + 14;
                    } else if (flag) {
                        k2 = this.imageHeight + 28;
                    } else {
                        k2 = this.imageHeight;
                    }

                    guiGraphics.blit(new ResourceLocation("textures/gui/container/loom.png"), i2, j2, 0, k2, 14, 14);
                    this.renderStanceButton(guiGraphics, list.get(l1).ordinal(), i2, j2);
                }
            }
        }

        Lighting.setupFor3DItems();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderEntityInInventoryFollowsMouse(guiGraphics, this.leftPos + 125, this.topPos + 70, 30, (float) (this.leftPos + 125) - this.xMouse, (float) (this.topPos + 70 - 50) - this.yMouse, PLAYERpreview);

        //LIST.render(guiGraphics, mouseX, mouseY, delta);


        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
        this.xMouse = (float) mouseX;
        this.yMouse = (float) mouseY;

        RECOLOUR_TAB_BUTTON.renderWidget(guiGraphics, mouseX, mouseY, delta);
        STANCE_TAB_BUTTON.renderWidget(guiGraphics, mouseX, mouseY, delta);
        //FLOURISH_TAB_BUTTON.renderWidget(guiGraphics, mouseX, mouseY, delta);


        guiGraphics.pose().popPose();
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void renderStanceButton(GuiGraphics pGuiGraphics, int num, int pX, int pY) {
        ItemStack itemstack = new ItemStack(Items.BELL);
        pGuiGraphics.renderFakeItem(itemstack, pX, pY);
    }

    private int totalRowCount() {
        return Mth.positiveCeilDiv(this.menu.getSelectableStances().size(), 4);
    }

    void SelectTab(int index) {
        if (index == 0) {

            ModMessages.sendToServer(new ServerboundKyberMenuTabChange(0));
        }
        if (index == 2) {
            ModMessages.sendToServer(new ServerboundKyberMenuTabChange(2));
        }

    }

    public static void renderEntityInInventoryFollowsMouse(GuiGraphics pGuiGraphics, int pX, int pY, int pScale, float p_275604_, float p_275546_, LivingEntity pEntity) {
        float f = (float) Math.atan((double) (p_275604_ / 40.0F));
        float f1 = (float) Math.atan((double) (p_275546_ / 40.0F));
        // Forge: Allow passing in direct angle components instead of mouse position
        renderEntityInInventoryFollowsAngle(pGuiGraphics, pX, pY, pScale, f, f1, pEntity);
    }

    public static void renderEntityInInventoryFollowsAngle(GuiGraphics pGuiGraphics, int pX, int pY, int pScale, float angleXComponent, float angleYComponent, LivingEntity pEntity) {
        float f = angleXComponent;
        float f1 = angleYComponent;
        Quaternionf quaternionf = (new Quaternionf()).rotateZ((float) Math.PI);
        Quaternionf quaternionf1 = (new Quaternionf()).rotateX(f1 * 20.0F * ((float) Math.PI / 180F));
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
        pEntity.setShiftKeyDown(true);
        renderEntityInInventory(pGuiGraphics, pX, pY, pScale, quaternionf, quaternionf1, pEntity);
        pEntity.yBodyRot = f2;
        pEntity.setYRot(f3);
        pEntity.setXRot(f4);
        pEntity.yHeadRotO = f5;
        pEntity.yHeadRot = f6;
    }

    public static void renderEntityInInventory(GuiGraphics pGuiGraphics, int pX, int pY, int pScale, Quaternionf p_281880_, @Nullable Quaternionf pCameraOrientation, LivingEntity pEntity) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate((double) pX, (double) pY, 50.0D);
        pGuiGraphics.pose().mulPoseMatrix((new Matrix4f()).scaling((float) pScale, (float) pScale, (float) (-pScale)));
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
