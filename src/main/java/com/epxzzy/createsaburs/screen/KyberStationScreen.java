package com.epxzzy.createsaburs.screen;


import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.misc.ColourConverter;
import com.epxzzy.createsaburs.misc.SliderWidget;
import com.epxzzy.createsaburs.networking.ModMessages;
import com.epxzzy.createsaburs.networking.packet.ServerboundRecolourItemPacket;
import com.epxzzy.createsaburs.utils.ModTags;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class KyberStationScreen extends AbstractContainerScreen<KyberStationMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(createsaburs.MOD_ID, "textures/gui/gem_polishing_station_gui.png");

    private SliderWidget HUE_SLIDER;
    private SliderWidget SAT_SLIDER;
    private SliderWidget LIT_SLIDER;

    private boolean HSL_toggle = false;

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
        int[] inputColour = ColourConverter.RGBtoHSL(gur[0], gur[1], gur[2]);

        this.HUE_SLIDER.setValue(inputColour[0]);
        this.SAT_SLIDER.setValue(inputColour[1]);
        this.LIT_SLIDER.setValue(inputColour[2]);
    }

    @Override
    protected void init() {
        super.init();
        //sloider = new ForgeSlider(this.leftPos + 10,this.topPos + 10, 150, 20, Component.empty(), Component.empty(), 0, 256, 0, 1, 1, false);
        //this.addWidget(this.LIST);
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;

        //this.HUE_SLIDER = getSliderForColour(0, 255, "hue ", 1);
        //this.SAT_SLIDER = getSliderForColour(0, 255, "sat ", 2);
        //this.LIT_SLIDER = getSliderForColour(0, 255, "light ", 3);

        this.HUE_SLIDER = getSliderForColour(0, 360, "hue ", 1);
        this.SAT_SLIDER = getSliderForColour(0, 100, "sat ", 2);
        this.LIT_SLIDER = getSliderForColour(0, 100, "light ", 3);

        this.addWidget(this.HUE_SLIDER);
        this.addWidget(this.SAT_SLIDER);
        this.addWidget(this.LIT_SLIDER);

        this.HUE_SLIDER.active = false;
        this.SAT_SLIDER.active = false;
        this.LIT_SLIDER.active = false;

        this.HUE_SLIDER.visible = false;
        this.SAT_SLIDER.visible = false;
        this.LIT_SLIDER.visible = false;

        //this.HUE_SLIDER.setResponder(this::)

        //this.addWidget(sloider);

        //this.addRenderableWidget(
        //this.addRenderableWidget(new SliderWidget(this.leftPos + 10, this.topPos + 10, 150, 20, 0, 100, 50));

    }

    public void UpdateServerRecipe() {
        int[] regbee =
                ColourConverter.HSLtoRGB(
                        HUE_SLIDER.getValueInt(),
                        SAT_SLIDER.getValueInt(),
                        LIT_SLIDER.getValueInt()

                );
        int regbeedecimal = ColourConverter.portedRGBtoDecimal(regbee);

        //Color.HSBtoRGB((float) HUE_SLIDER.getValueInt() / 60, (float) SAT_SLIDER.getValueInt() /100, (float) LIT_SLIDER.getValueInt() /100);

        // String asd = Integer.toString("")

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
        UpdateServerRecipe();
        return super.getFocused() != null && super.isDragging() && pButton == 0 && this.getFocused().mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        UpdateServerRecipe();
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        UpdateServerRecipe();
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        HUE_SLIDER.setFocused(false);
        SAT_SLIDER.setFocused(false);
        LIT_SLIDER.setFocused(false);

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
                false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        //renderProgressArrow(guiGraphics, x, y);
    }

    // private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
    //if (menu.canCraft()) {
    //guiGraphics.blit(TEXTURE, x + 85, y + 30, 176, 0, 8, menu.getScaledProgress());
    //}
    //}

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        if (menu.canCraft()) {
            this.HUE_SLIDER.active = true;
            this.SAT_SLIDER.active = true;
            this.LIT_SLIDER.active = true;

            this.HUE_SLIDER.visible = true;
            this.SAT_SLIDER.visible = true;
            this.LIT_SLIDER.visible = true;

            this.HUE_SLIDER.render(guiGraphics, mouseX, mouseY, delta);
            this.SAT_SLIDER.render(guiGraphics, mouseX, mouseY, delta);
            this.LIT_SLIDER.render(guiGraphics, mouseX, mouseY, delta);
            /*
            int[] colouur = ColourConverter.hslToRgb(
                    HUE_SLIDER.getValueInt(),
                    SAT_SLIDER.getValueInt(),
                    LIT_SLIDER.getValueInt()
            );
            guiGraphics.fill(
                    this.leftPos + 137,
                    this.topPos + 10,
                    this.leftPos + 166,
                    this.topPos + 40,
                    FastColor.ARGB32.color(
                            255,
                            colouur[0],
                            colouur[1],
                            colouur[2]
                    )
            );*/


            /*
            guiGraphics.fill(
                    this.leftPos + 137,
                    this.topPos + 10,
                    this.leftPos + 166,
                    this.topPos + 40,
                    FastColor.ARGB32.color(
                            255,
                            HUE_SLIDER.getValueInt(),
                            SAT_SLIDER.getValueInt(),
                            LIT_SLIDER.getValueInt()
                    )
            );

             */

            int[] regbee =
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
            this.HUE_SLIDER.active = true;
            this.SAT_SLIDER.active = true;
            this.LIT_SLIDER.active = true;

            this.HUE_SLIDER.visible = true;
            this.SAT_SLIDER.visible = true;
            this.LIT_SLIDER.visible = true;
        }

        //LIST.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}