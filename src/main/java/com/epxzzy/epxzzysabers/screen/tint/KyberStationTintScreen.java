package com.epxzzy.epxzzysabers.screen.tint;


import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.networking.packet.ServerboundKyberMenuTabChange;
import com.epxzzy.epxzzysabers.screen.stance.KyberStationStanceMenu;
import com.epxzzy.epxzzysabers.utils.ColourConverter;
import com.epxzzy.epxzzysabers.screen.components.KyberModes;
import com.epxzzy.epxzzysabers.screen.components.KyberTabButton;
import com.epxzzy.epxzzysabers.screen.components.SliderWidget;
import com.epxzzy.epxzzysabers.networking.ModMessages;
import com.epxzzy.epxzzysabers.networking.packet.ServerboundRecolourItemPacket;
import com.epxzzy.epxzzysabers.utils.ModTags;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import org.apache.commons.lang3.SerializationUtils;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;

public class KyberStationTintScreen extends AbstractContainerScreen<KyberStationTintMenu> {
    private static final ResourceLocation RECOLOUR_TEXTURE =
            new ResourceLocation(epxzzySabers.MOD_ID, "textures/gui/kyber_recolour.png");


    private static final ResourceLocation OFF_TOGGLE =
            new ResourceLocation(epxzzySabers.MOD_ID, "textures/gui/toggle_off.png");
    private static final ResourceLocation ON_TOGGLE =
            new ResourceLocation(epxzzySabers.MOD_ID, "textures/gui/toggle_on.png");


    private KyberTabButton RECOLOUR_TAB_BUTTON;
    private KyberTabButton STANCE_TAB_BUTTON;
    //private KyberTabButton FLOURISH_TAB_BUTTON;

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
    public boolean GAY_MODE = false;
    public boolean GAY_INPUT = false;
    private boolean widthTooNarrow = this.width < 379;
    private int TABLE_MODE = 0;
    //0 = recolour
    //1 = stance

    private float xMouse;
    private float yMouse;

    public void slotChanged() {

    }

    public KyberStationTintScreen(KyberStationTintMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        pMenu.registerUpdateListener(this::containerChanged);
        pMenu.registerInputUpdateListener(this::TakeInput);

    }

    private void containerChanged() {


        //menu.craftSabur(HUE_SLIDER.getValueInt(), SAT_SLIDER.getValueInt(),LIT_SLIDER.getValueInt());
        //epxzzySabers.LOGGER.info("client kyber station change");

        return;
    }

    public void TakeInput() {
        int[] gur = menu.getInputColour();
        GAY_INPUT = menu.isInputGay();
        if (GAY_INPUT) return;
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

        UpdateServerRecipe();
    }

    @Override
    protected void init() {
        super.init();
        //sloider = new ForgeSlider(this.leftPos + 10,this.topPos + 10, 150, 20, Component.empty(), Component.empty(), 0, 256, 0, 1, 1, false);
        //this.addWidget(this.LIST);
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;


        initSliderStuff();

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

        RECOLOUR_TAB_BUTTON.setStateTriggered(true);
        this.addWidget(RECOLOUR_TAB_BUTTON);
        this.addWidget(STANCE_TAB_BUTTON);
        //this.addWidget(FLOURISH_TAB_BUTTON);



            /*
            {
                @Override
                public void onPress() {
                    SelectTab(tabID);
                    super.onPress();
                }
            });

             */


        //PLAYERpreview = this.minecraft.player;
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
        if (slot.getItem().is(ModTags.Items.DYEABLE_LIGHTSABER)) {
            if (this.menu.setItemColour(regbee, GAY_MODE)) {
                //this.minecraft.player.connection.send(new HonkPacket.Serverbound)
                //Color.HSBtoRGB(this.menu.getInputColour())
                ModMessages.sendToServer(new ServerboundRecolourItemPacket(regbee, GAY_MODE));
            }
        }
    }


    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (!this.menu.canCraft()) return false;
        UpdateServerRecipe();
        return super.getFocused() != null && super.isDragging() && pButton == 0 && this.getFocused().mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == GLFW.GLFW_KEY_PERIOD) {
            GAY_MODE = !GAY_MODE;
        }
        if (pKeyCode == GLFW.GLFW_KEY_SPACE) {
            setRGBmode();
        }
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
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, RECOLOUR_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RECOLOUR_TEXTURE, x - 15, y, 0, 0, 215, imageHeight);

    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderRecolourTab(guiGraphics, mouseX, mouseY, delta);

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

    public void setRGBmode(){
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

        epxzzySabers.LOGGER.info("RGB MODE NOW " + RGB_MODE);
        UpdateServerRecipe();

    }

    public void renderRecolourTab(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (this.RGB_TOGGLE != null) this.RGB_TOGGLE.render(guiGraphics, mouseX, mouseY, delta);
        manShutYourBitchAssUp(!this.menu.canCraft());


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
            } else {
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

    void initSliderStuff() {
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

    void manShutYourBitchAssUp(boolean yesnt) {
        this.HUE_SLIDER.kys = yesnt;
        this.SAT_SLIDER.kys = yesnt;
        this.LIT_SLIDER.kys = yesnt;

        this.RED_SLIDER.kys = yesnt;
        this.GREEN_SLIDER.kys = yesnt;
        this.BLUE_SLIDER.kys = yesnt;
        if (yesnt) {
            this.HUE_SLIDER.setPosition(0, -99999);
            this.SAT_SLIDER.setPosition(0, -99999);
            this.LIT_SLIDER.setPosition(0, -99999);

            this.RED_SLIDER.setPosition(0, -99999);
            this.GREEN_SLIDER.setPosition(0, -99999);
            this.BLUE_SLIDER.setPosition(0, -99999);
        }
        if (!yesnt) {
            this.HUE_SLIDER.setPosition(this.leftPos + 12, this.topPos + 12);
            this.SAT_SLIDER.setPosition(this.leftPos + 12, this.topPos + 12 * 2);
            this.LIT_SLIDER.setPosition(this.leftPos + 12, this.topPos + 12 * 3);

            this.RED_SLIDER.setPosition(this.leftPos + 12, this.topPos + 12);
            this.GREEN_SLIDER.setPosition(this.leftPos + 12, this.topPos + 12 * 2);
            this.BLUE_SLIDER.setPosition(this.leftPos + 12, this.topPos + 12 * 3);
        }

    }

    void SelectTab(int index) {

        if (index == 0) {
            //this.menu.resetSlotPose();
        }
        if (index == 1) {

            //ModMessages.sendToServer(new ServerboundKyberMenuSlotPosToggle(false));
            ModMessages.sendToServer(new ServerboundKyberMenuTabChange(1));

        }
        if (index == 2) {

            ModMessages.sendToServer(new ServerboundKyberMenuTabChange(2));

            //this.menu.resetSlotPose();
        }
    }
}
