package com.epxzzy.createsaburs.screen;


import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.misc.ColourConverter;
import com.epxzzy.createsaburs.misc.KyberModes;
import com.epxzzy.createsaburs.misc.KyberTabButton;
import com.epxzzy.createsaburs.misc.SliderWidget;
import com.epxzzy.createsaburs.networking.ModMessages;
import com.epxzzy.createsaburs.networking.packet.ServerboundRecolourItemPacket;
import com.epxzzy.createsaburs.utils.ModTags;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class KyberStationScreen extends AbstractContainerScreen<KyberStationMenu> {
    private static final ResourceLocation RECOLOUR_TEXTURE =
            new ResourceLocation(createsaburs.MOD_ID, "textures/gui/kyber_recolour.png");

    private static final ResourceLocation STANCE_TEXTURE =
            new ResourceLocation(createsaburs.MOD_ID, "textures/gui/kyber_stance.png");


    private static final ResourceLocation OFF_TOGGLE=
            new ResourceLocation(createsaburs.MOD_ID, "textures/gui/toggle_off.png");
    private static final ResourceLocation ON_TOGGLE =
            new ResourceLocation(createsaburs.MOD_ID, "textures/gui/toggle_on.png");



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

    private KyberTabButton selectedTab = RECOLOUR_TAB_BUTTON;

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
        //createsaburs.LOGGER.info("client kyber station change");

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


        this.RGB_TOGGLE = new AbstractButton(topPos + 50, leftPos - 50, 20, 10, Component.literal("Toggle")) {
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

                createsaburs.LOGGER.info("RGB MODE NOW " + RGB_MODE);
                UpdateServerRecipe();
            }
        };
        this.addWidget(this.RGB_TOGGLE);
        initSliderStuff();

        RECOLOUR_TAB_BUTTON = new KyberTabButton(KyberModes.RECOLOUR, 0,this.topPos, this.leftPos){
            @Override
            public void onPress() {
                SelectTab(tabID);
                super.onPress();
            }
        };
        STANCE_TAB_BUTTON= new KyberTabButton(KyberModes.STANCE, 1,this.topPos+40, this.leftPos){
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
        this.updateTabs();
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
        if(TABLE_MODE == 0) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, RECOLOUR_TEXTURE);
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2;

            guiGraphics.blit(RECOLOUR_TEXTURE, x - 15, y, 0, 0, 215, imageHeight);
        }
        if(TABLE_MODE == 1) {
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

        //LIST.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);


        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);

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

 void updateTabs() {
        int i = (this.width - 147) / 2 - (this.widthTooNarrow ? 0:86) - 30;
        int j = (this.height - 166) / 2 + 3;
        int k = 27;
        int l = 0;

        /*
        for(KyberTabButton recipebooktabbutton : this.tabButtons) {
            KyberModes recipebookcategories = recipebooktabbutton.getCategory();
                recipebooktabbutton.visible = false;
                recipebooktabbutton.setPosition(i, j + 27 * l++);
        }

         */
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
       if (this.selectedTab != null) {
           this.selectedTab = RECOLOUR_TAB_BUTTON;
           this.selectedTab.setStateTriggered(true);

       }
   }
    void SelectTab(int index){
        if(this.selectedTab != null){
            this.selectedTab.setStateTriggered(false);

            this.selectedTab = index==0?RECOLOUR_TAB_BUTTON:STANCE_TAB_BUTTON;

            this.selectedTab.setStateTriggered(true);

        }

    }

}
