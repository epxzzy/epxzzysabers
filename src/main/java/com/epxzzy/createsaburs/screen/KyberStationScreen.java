package com.epxzzy.createsaburs.screen;


import com.epxzzy.createsaburs.CreateSaburs;
import com.epxzzy.createsaburs.utils.ColourConverter;
import com.epxzzy.createsaburs.screen.components.KyberModes;
import com.epxzzy.createsaburs.screen.components.KyberTabButton;
import com.epxzzy.createsaburs.screen.components.SliderWidget;
import com.epxzzy.createsaburs.networking.ModMessages;
import com.epxzzy.createsaburs.networking.packet.ServerboundRecolourItemPacket;
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

public class KyberStationScreen extends AbstractContainerScreen<KyberStationMenu> {
    private static final ResourceLocation RECOLOUR_TEXTURE =
            new ResourceLocation(CreateSaburs.MOD_ID, "textures/gui/kyber_recolour.png");

    private static final ResourceLocation STANCE_TEXTURE =
            new ResourceLocation(CreateSaburs.MOD_ID, "textures/gui/kyber_stance.png");


    private static final ResourceLocation OFF_TOGGLE=
            new ResourceLocation(CreateSaburs.MOD_ID, "textures/gui/toggle_off.png");
    private static final ResourceLocation ON_TOGGLE =
            new ResourceLocation(CreateSaburs.MOD_ID, "textures/gui/toggle_on.png");



    private KyberTabButton RECOLOUR_TAB_BUTTON;
    private KyberTabButton STANCE_TAB_BUTTON;

            /*

    private List<KyberTabButton> tabButtons = new ArrayList<>().add(
            new KyberTabButton(KyberModes.RECOLOUR, 0,this.topPos+50, this.leftPos+150){
                @Override
                public void onPress() {
                    SelectTab(tabID);
                    super.onPress();
                }
            }
    );

    };

             */

    private int selectedTab = 0;

    private SliderWidget HUE_SLIDER;
    private SliderWidget SAT_SLIDER;
    private SliderWidget LIT_SLIDER;
    private AbstractButton RGB_TOGGLE;
    private SliderWidget RED_SLIDER;
    private SliderWidget GREEN_SLIDER;
    private SliderWidget BLUE_SLIDER;

    private boolean RGB_MODE = false;
    private boolean widthTooNarrow = this.width < 379;
    private int TABLE_MODE = 0;
    //0 = recolour
    //1 = stance

    private float xMouse;
    private float yMouse;

    public void slotChanged() {

    }

    public KyberStationScreen(KyberStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
        int[] gur = menu.getInputColour();
        if (!RGB_MODE) {
            int[] inputColour = ColourConverter.RGBtoHSL(gur[0], gur[1], gur[2]);

            this.HUE_SLIDER.setValue(inputColour[0]);
            this.SAT_SLIDER.setValue(inputColour[1]);
            this.LIT_SLIDER.setValue(inputColour[2]);
        }
        if (RGB_MODE) {
            this.RED_SLIDER.setValue(gur[0]);
            this.GREEN_SLIDER.setValue(gur[1]);
            this.BLUE_SLIDER.setValue(gur[2]);
        }
    }

    @Override
    protected void init() {
        super.init();
        //sloider = new ForgeSlider(this.leftPos + 10,this.topPos + 10, 150, 20, Component.empty(), Component.empty(), 0, 256, 0, 1, 1, false);
        //this.addWidget(this.LIST);
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;


        this.RGB_TOGGLE = new AbstractButton(topPos, leftPos - 140, 20, 10, Component.literal("Toggle")) {
            @Override
            public void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
            }

            @Override
            public void renderString(GuiGraphics pGuiGraphics, Font pFont, int pColor) {
                super.renderString(pGuiGraphics, pFont, pColor);
                //this.renderTexture(pGuiGraphics, RGB_MODE?ON_TOGGLE:OFF_TOGGLE, this.getX()-11, this.getY(), 0, 0, this.getX(), 20, 10, 20, 10);

            }

            @Override
            public void onPress() {
                RGB_MODE = !RGB_MODE;
                if (RGB_MODE) {
                    HUE_SLIDER.setFocused(false);
                    SAT_SLIDER.setFocused(false);
                    LIT_SLIDER.setFocused(false);

                    HUE_SLIDER.active = false;
                    SAT_SLIDER.active = false;
                    LIT_SLIDER.active = false;

                    HUE_SLIDER.visible = false;
                    SAT_SLIDER.visible = false;
                    LIT_SLIDER.visible = false;

                }
                if (!RGB_MODE) {
                    RED_SLIDER.setFocused(false);
                    GREEN_SLIDER.setFocused(false);
                    BLUE_SLIDER.setFocused(false);

                    RED_SLIDER.active = false;
                    GREEN_SLIDER.active = false;
                    BLUE_SLIDER.active = false;

                    RED_SLIDER.visible = false;
                    GREEN_SLIDER.visible = false;
                    BLUE_SLIDER.visible = false;

                }
                int[] gur = !RGB_MODE ? ColourConverter.RGBtoHSL(
                        RED_SLIDER.getValueInt(),
                        GREEN_SLIDER.getValueInt(),
                        BLUE_SLIDER.getValueInt()
                ) : ColourConverter.HSLtoRGB(
                        HUE_SLIDER.getValueInt(),
                        SAT_SLIDER.getValueInt(),
                        LIT_SLIDER.getValueInt()
                );

                if (!RGB_MODE) {

                    HUE_SLIDER.setValue(gur[0]);
                    SAT_SLIDER.setValue(gur[1]);
                    LIT_SLIDER.setValue(gur[2]);
                }
                if (RGB_MODE) {
                    RED_SLIDER.setValue(gur[0]);
                    GREEN_SLIDER.setValue(gur[1]);
                    BLUE_SLIDER.setValue(gur[2]);
                }

                CreateSaburs.LOGGER.info("RGB MODE NOW " + RGB_MODE);
                UpdateServerRecipe();
            }
        };
        this.addWidget(this.RGB_TOGGLE);
        initSliderStuff();

        RECOLOUR_TAB_BUTTON = new KyberTabButton(KyberModes.RECOLOUR, 0,this.topPos+10, this.leftPos-45){
            @Override
            public void onPress() {
                SelectTab(tabID);
                super.onPress();
            }
        };
        STANCE_TAB_BUTTON= new KyberTabButton(KyberModes.STANCE, 1,this.topPos+40, this.leftPos-45){
                    @Override
                    public void onPress(){
                        SelectTab(tabID);
                        super.onPress();
                    }
                };


        this.addWidget(RECOLOUR_TAB_BUTTON);
        this.addWidget(STANCE_TAB_BUTTON);


            /*
            {
                @Override
                public void onPress() {
                    SelectTab(tabID);
                    super.onPress();
                }
            });

             */
        this.SelectTab();
    }

    public void UpdateServerRecipe() {
        int[] regbee = this.RGB_MODE ?
                new int[]{
                        RED_SLIDER.getValueInt(),
                        GREEN_SLIDER.getValueInt(),
                        BLUE_SLIDER.getValueInt()
                } :
                ColourConverter.HSLtoRGB(
                        HUE_SLIDER.getValueInt(),
                        SAT_SLIDER.getValueInt(),
                        LIT_SLIDER.getValueInt()
                );

        int regbeedecimal = ColourConverter.portedRGBtoDecimal(regbee);

        //Color.HSBtoRGB((float) HUE_SLIDER.getValueInt() / 60, (float) SAT_SLIDER.getValueInt() /100, (float) LIT_SLIDER.getValueInt() /100);

        // String asd = Integer.toString("")

        if (!this.RGB_MODE) {
            this.HUE_SLIDER.setTooltip(
                    Tooltip.create(
                            Component.literal(
                                    String.format("#%06X", regbeedecimal & 0xFFFFFF)
                            )
                    )
            );
            this.SAT_SLIDER.setTooltip(
                    Tooltip.create(
                            Component.literal(
                                    String.format("#%06X", regbeedecimal & 0xFFFFFF)
                            )
                    )
            );
            this.LIT_SLIDER.setTooltip(
                    Tooltip.create(
                            Component.literal(
                                    String.format("#%06X", regbeedecimal & 0xFFFFFF)
                            )
                    )
            );
        }
        if (this.RGB_MODE) {
            this.RED_SLIDER.setTooltip(
                    Tooltip.create(
                            Component.literal(
                                    String.format("#%06X", regbeedecimal & 0xFFFFFF)
                            )
                    )
            );
            this.GREEN_SLIDER.setTooltip(
                    Tooltip.create(
                            Component.literal(
                                    String.format("#%06X", regbeedecimal & 0xFFFFFF)
                            )
                    )
            );
            this.BLUE_SLIDER.setTooltip(
                    Tooltip.create(
                            Component.literal(
                                    String.format("#%06X", regbeedecimal & 0xFFFFFF)
                            )
                    )
            );

        }


        Slot slot = this.menu.getSlot(0);
        if (slot.getItem().is(ModTags.Items.CREATE_LIGHTSABER)) {
            if (this.menu.setItemColour(regbee)) {
                //this.minecraft.player.connection.send(new HonkPacket.Serverbound)
                //Color.HSBtoRGB(this.menu.getInputColour())
                ModMessages.sendToServer(new ServerboundRecolourItemPacket(regbee));
            }
        }
    }


    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if(!this.menu.canCraft()) return false;
        UpdateServerRecipe();
        return super.getFocused() != null && super.isDragging() && pButton == 0 && this.getFocused().mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
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
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        UpdateServerRecipe();
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (RGB_MODE) {
            RED_SLIDER.setFocused(false);
            GREEN_SLIDER.setFocused(false);
            BLUE_SLIDER.setFocused(false);
        }
        if (!RGB_MODE) {
            HUE_SLIDER.setFocused(false);
            SAT_SLIDER.setFocused(false);
            LIT_SLIDER.setFocused(false);
        }

        UpdateServerRecipe();
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    public SliderWidget getSliderForColour(int pValue, int pMaxValue, String pString, int pPositionMultiplier) {
        return new SliderWidget(
                this.leftPos + 12,
                this.topPos + (12 * pPositionMultiplier),
                113,
                10,
                Component.literal(pString),
                Component.literal(""),
                0,
                pMaxValue,
                pValue,
                1,
                0,
                true);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        if(selectedTab== 0) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, RECOLOUR_TEXTURE);
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2;

            guiGraphics.blit(RECOLOUR_TEXTURE, x - 15, y, 0, 0, 215, imageHeight);
        }
        if(selectedTab== 1) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, STANCE_TEXTURE);
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2;

            guiGraphics.blit(STANCE_TEXTURE, x - 15, y, 0, 0, 215, imageHeight);
        }

    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        if(selectedTab==0){
            renderRecolourTab(guiGraphics,mouseX,mouseY,delta);
        }
        if(selectedTab==1){
            renderEntityInInventoryFollowsMouse(guiGraphics, this.leftPos + 125, this.topPos + 70, 30, (float)(this.leftPos + 125) - this.xMouse, (float)(this.topPos + 70 - 50) - this.yMouse, this.minecraft.player);
        }

        //LIST.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);


        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
        this.xMouse = (float)mouseX;
        this.yMouse = (float)mouseY;

        /*
        for(KyberTabButton recipebooktabbutton : this.tabButtons) {
            if(recipebooktabbutton.tabID==0){
                //recipebooktabbutton.setPosition(this.leftPos,this.topPos-(35)+5);
            }
            if(recipebooktabbutton.tabID==1){
                //recipebooktabbutton.setPosition(this.leftPos,this.topPos-(2*35)+5);
            }
            recipebooktabbutton.renderWidget(guiGraphics, mouseX, mouseY, delta);
        if(tabButtons != null) {
            tabButtons.get(0).renderWidget(guiGraphics, mouseX, mouseY, delta);
            tabButtons.get(1).renderWidget(guiGraphics, mouseX, mouseY, delta);
        }
            }
         */

        RECOLOUR_TAB_BUTTON.renderWidget(guiGraphics,mouseX,mouseY,delta);
        STANCE_TAB_BUTTON.renderWidget(guiGraphics,mouseX,mouseY,delta);


        guiGraphics.pose().popPose();
    }


    public void renderRecolourTab(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (this.RGB_TOGGLE != null) this.RGB_TOGGLE.render(guiGraphics, mouseX, mouseY, delta);
        manShutYourBitchAssUp(!this.menu.canCraft());

        guiGraphics.blit(RGB_MODE?ON_TOGGLE:OFF_TOGGLE,this.leftPos-30, this.topPos+90, (float)0, (float)0, 20, 10, 20, 10);

        if (menu.canCraft()) {
            if (!this.RGB_MODE) {
                this.HUE_SLIDER.active = true;
                this.SAT_SLIDER.active = true;
                this.LIT_SLIDER.active = true;

                this.HUE_SLIDER.visible = true;
                this.SAT_SLIDER.visible = true;
                this.LIT_SLIDER.visible = true;

                this.HUE_SLIDER.render(guiGraphics, mouseX, mouseY, delta);
                this.SAT_SLIDER.render(guiGraphics, mouseX, mouseY, delta);
                this.LIT_SLIDER.render(guiGraphics, mouseX, mouseY, delta);
            }
            if (this.RGB_MODE) {
                this.RED_SLIDER.active = true;
                this.GREEN_SLIDER.active = true;
                this.BLUE_SLIDER.active = true;

                this.RED_SLIDER.visible = true;
                this.GREEN_SLIDER.visible = true;
                this.BLUE_SLIDER.visible = true;

                this.RED_SLIDER.render(guiGraphics, mouseX, mouseY, delta);
                this.GREEN_SLIDER.render(guiGraphics, mouseX, mouseY, delta);
                this.BLUE_SLIDER.render(guiGraphics, mouseX, mouseY, delta);
            }
            // ^ slider shit
            int[] regbee = this.RGB_MODE ? new int[]{RED_SLIDER.getValueInt(), GREEN_SLIDER.getValueInt(), BLUE_SLIDER.getValueInt()} :
                    ColourConverter.HSLtoRGB(
                            HUE_SLIDER.getValueInt(),
                            SAT_SLIDER.getValueInt(),
                            LIT_SLIDER.getValueInt()
                    );

            guiGraphics.fill(
                    this.leftPos + 137,
                    this.topPos + 10,
                    this.leftPos + 166,
                    this.topPos + 40,
                    FastColor.ARGB32.color(255,
                            regbee[0],
                            regbee[1],
                            regbee[2])
            );


        } else {
            if (!this.RGB_MODE) {
                this.HUE_SLIDER.active = true;
                this.SAT_SLIDER.active = true;
                this.LIT_SLIDER.active = true;

                this.HUE_SLIDER.visible = true;
                this.SAT_SLIDER.visible = true;
                this.LIT_SLIDER.visible = true;
            }
            else {
                this.RED_SLIDER.active = true;
                this.GREEN_SLIDER.active = true;
                this.BLUE_SLIDER.active = true;

                this.RED_SLIDER.visible = true;
                this.GREEN_SLIDER.visible = true;
                this.BLUE_SLIDER.visible = true;
            }
            //^ slider shit
        }


    }

   void initSliderStuff(){
       this.HUE_SLIDER = getSliderForColour(0, 360, "hue ", 1);
       this.SAT_SLIDER = getSliderForColour(0, 100, "sat ", 2);
       this.LIT_SLIDER = getSliderForColour(0, 100, "light ", 3);

       this.RED_SLIDER = getSliderForColour(0, 255, "red ", 1);
       this.GREEN_SLIDER = getSliderForColour(0, 255, "green ", 2);
       this.BLUE_SLIDER = getSliderForColour(0, 255, "blue ", 3);


       this.addWidget(this.HUE_SLIDER);
       this.addWidget(this.SAT_SLIDER);
       this.addWidget(this.LIT_SLIDER);

       this.addWidget(this.RED_SLIDER);
       this.addWidget(this.GREEN_SLIDER);
       this.addWidget(this.BLUE_SLIDER);


       this.HUE_SLIDER.active = false;
       this.SAT_SLIDER.active = false;
       this.LIT_SLIDER.active = false;

       this.HUE_SLIDER.visible = false;
       this.SAT_SLIDER.visible = false;
       this.LIT_SLIDER.visible = false;


       this.RED_SLIDER.active = false;
       this.GREEN_SLIDER.active = false;
       this.BLUE_SLIDER.active = false;

       this.RED_SLIDER.visible = false;
       this.GREEN_SLIDER.visible = false;
       this.BLUE_SLIDER.visible = false;

   }
   void manShutYourBitchAssUp(boolean yesnt){
       this.HUE_SLIDER.kys = yesnt;
       this.SAT_SLIDER.kys = yesnt;
       this.LIT_SLIDER.kys = yesnt;

       this.RED_SLIDER.kys = yesnt;
       this.GREEN_SLIDER.kys = yesnt;
       this.BLUE_SLIDER.kys = yesnt;
        if(yesnt) {
            this.HUE_SLIDER.setPosition(0, -99999);
            this.SAT_SLIDER.setPosition(0, -99999);
            this.LIT_SLIDER.setPosition(0, -99999);

            this.RED_SLIDER.setPosition(0, -99999);
            this.GREEN_SLIDER.setPosition(0, -99999);
            this.BLUE_SLIDER.setPosition(0, -99999);
        }
       if(!yesnt) {
           this.HUE_SLIDER.setPosition(this.leftPos+12, this.topPos+ 12);
           this.SAT_SLIDER.setPosition(this.leftPos+12, this.topPos+ 12*2);
           this.LIT_SLIDER.setPosition(this.leftPos+12, this.topPos+ 12*3);

           this.RED_SLIDER.setPosition(this.leftPos+12, this.topPos+ 12);
           this.GREEN_SLIDER.setPosition(this.leftPos+12, this.topPos+ 12*2);
           this.BLUE_SLIDER.setPosition(this.leftPos+12, this.topPos+ 12*3);
       }

   }
   void SelectTab(){
           this.selectedTab = 0;
           this.RECOLOUR_TAB_BUTTON.setStateTriggered(true);
   }
    void SelectTab(int index){
        (this.selectedTab==0?RECOLOUR_TAB_BUTTON:STANCE_TAB_BUTTON).setStateTriggered(false);
        this.selectedTab = index;
        (this.selectedTab==0?RECOLOUR_TAB_BUTTON:STANCE_TAB_BUTTON).setStateTriggered(true);

        if(selectedTab==0){
            //ModMessages.sendToServer(new ServerboundKyberMenuSlotPosToggle(true));
            this.menu.resetSlotPose();
        }
        if(selectedTab==1){
            //ModMessages.sendToServer(new ServerboundKyberMenuSlotPosToggle(false));

            this.menu.stanceSlotPose();

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
