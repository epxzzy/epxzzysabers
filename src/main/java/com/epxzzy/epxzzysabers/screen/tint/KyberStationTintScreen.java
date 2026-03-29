package com.epxzzy.epxzzysabers.screen.tint;


import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.util.ColourConverter;
import com.epxzzy.epxzzysabers.screen.components.SliderWidget;
import com.epxzzy.epxzzysabers.networking.SaberMessages;
import com.epxzzy.epxzzysabers.networking.packet.ServerboundRecolourItemPacket;
import com.epxzzy.epxzzysabers.util.SaberTags;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class KyberStationTintScreen extends AbstractContainerScreen<KyberStationTintMenu> {
    private static final ResourceLocation RECOLOUR_TEXTURE =
            new ResourceLocation(epxzzySabers.MOD_ID, "textures/gui/kyber_recolour.png");

    private SliderWidget HUE_SLIDER;
    private SliderWidget SAT_SLIDER;
    private SliderWidget LIT_SLIDER;
    private SliderWidget RED_SLIDER;
    private SliderWidget GREEN_SLIDER;
    private SliderWidget BLUE_SLIDER;

    private boolean RGB_MODE = false;
    public boolean GAY_MODE = false;
    public boolean GAY_INPUT = false;

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
        if (menu.canCraft()) {
            if (!this.RGB_MODE) {

            }
        }


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
        //ToggleAllSlidersLoc(!this.menu.canCraft());

        Slot slot = this.menu.getSlot(0);
        if (slot.getItem().is(SaberTags.Items.DYEABLE_LIGHTSABER)) {
            if (this.menu.setItemColour(regbee, GAY_MODE)) {
                //this.minecraft.player.connection.send(new HonkPacket.Serverbound)
                //Color.HSBtoRGB(this.menu.getInputColour())
                SaberMessages.sendToServer(new ServerboundRecolourItemPacket(regbee, GAY_MODE));
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

        //LIST.renderMid(guiGraphics, mouseX, mouseY, delta);


        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
        this.xMouse = (float) mouseX;
        this.yMouse = (float) mouseY;


        guiGraphics.pose().popPose();
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public void setRGBmode(){
        RGB_MODE = !RGB_MODE;

        ToggleSliderSection(!RGB_MODE);

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
        //ToggleAllSliders(this.menu.canCraft());


        if (menu.canCraft()) {
            if (!this.RGB_MODE) {
                //ToggleSliderSection(true);

                this.HUE_SLIDER.render(guiGraphics, mouseX, mouseY, delta);
                this.SAT_SLIDER.render(guiGraphics, mouseX, mouseY, delta);
                this.LIT_SLIDER.render(guiGraphics, mouseX, mouseY, delta);
            }
            if (this.RGB_MODE) {
                //ToggleSliderSection(false);

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
        }
    }

    public void initSliderStuff() {
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

        this.HUE_SLIDER.disable(false);
        this.SAT_SLIDER.disable(false);
        this.LIT_SLIDER.disable(false);

        this.RED_SLIDER.disable(false);
        this.GREEN_SLIDER.disable(false);
        this.BLUE_SLIDER.disable(false);
        epxzzySabers.LOGGER.info("tint screen: all sliders enabled cause init");
    }

    public void ToggleSliderSection(boolean hsl) {
        if(hsl){
            this.HUE_SLIDER.disable(false);
            this.SAT_SLIDER.disable(false);
            this.LIT_SLIDER.disable(false);

            this.RED_SLIDER.disable(true);
            this.GREEN_SLIDER.disable(true);
            this.BLUE_SLIDER.disable(true);
            epxzzySabers.LOGGER.info("tint screen: only hsl sliders showing");
        }
        else {
            this.RED_SLIDER.disable(false);
            this.GREEN_SLIDER.disable(false);
            this.BLUE_SLIDER.disable(false);

            this.HUE_SLIDER.disable(true);
            this.SAT_SLIDER.disable(true);
            this.LIT_SLIDER.disable(true);
            epxzzySabers.LOGGER.info("tint screen: only rgb slider showing");

        }
    }


    public void ToggleAllSlidersLoc(boolean remove) {
        //this.HUE_SLIDER.disable(remove);
        //this.SAT_SLIDER.disable(remove);
        //this.LIT_SLIDER.disable(remove);

        //this.RED_SLIDER.disable(remove);
        //this.GREEN_SLIDER.disable(remove);
        //this.BLUE_SLIDER.disable(remove);
        if (remove) {
            this.HUE_SLIDER.setPosition(0, -99999);
            this.SAT_SLIDER.setPosition(0, -99999);
            this.LIT_SLIDER.setPosition(0, -99999);

            this.RED_SLIDER.setPosition(0, -99999);
            this.GREEN_SLIDER.setPosition(0, -99999);
            this.BLUE_SLIDER.setPosition(0, -99999);
            //epxzzySabers.LOGGER.info("tint screen: all sliders in the void");
        }
        if (!remove) {
            this.HUE_SLIDER.setPosition(this.leftPos + 12, this.topPos + 12);
            this.SAT_SLIDER.setPosition(this.leftPos + 12, this.topPos + 12 * 2);
            this.LIT_SLIDER.setPosition(this.leftPos + 12, this.topPos + 12 * 3);

            this.RED_SLIDER.setPosition(this.leftPos + 12, this.topPos + 12);
            this.GREEN_SLIDER.setPosition(this.leftPos + 12, this.topPos + 12 * 2);
            this.BLUE_SLIDER.setPosition(this.leftPos + 12, this.topPos + 12 * 3);
            //epxzzySabers.LOGGER.info("tint screen: all sliders in place");
        }

    }
}
