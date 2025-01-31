package com.epxzzy.createsaburs.misc;

import com.epxzzy.createsaburs.createsaburs;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class SliderWidget extends AbstractSliderButton {
    private final int minValue;
    private final int maxValue;
    private int currentValue;

    public SliderWidget(int x, int y, int width, int height, int minValue, int maxValue, int initialValue) {
        super(x, y, width, height, Component.literal(""), 256);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = initialValue;
        this.updateMessage();
    }


    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0xFFAAAAAA);
        int sliderPosition = this.getX() + (int) ((this.currentValue - this.minValue) / (float) (this.maxValue - this.minValue) * this.width);
        guiGraphics.fill(sliderPosition - 2, this.getY(), sliderPosition + 2, this.getY() + this.height, 0xFF0000FF);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
        this.currentValue = this.minValue + (int) ((mouseX - this.getX()) / this.width * (this.maxValue - this.minValue));
        this.currentValue = Math.min(Math.max(this.currentValue, this.minValue), this.maxValue);
        this.updateMessage();
        createsaburs.LOGGER.info("slider mooved to "+ this.currentValue);

    }

    @Override
    public void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }

    public void updateMessage() {
        this.setMessage(Component.literal(String.valueOf(this.currentValue)));
    }

    @Override
    protected void applyValue() {

    }

    public int getValue() {
        return this.currentValue;
    }

    @Override
    public void mouseMoved(double pMouseX, double pMouseY) {
        super.mouseMoved(pMouseX, pMouseY);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        return super.keyReleased(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        return super.charTyped(pCodePoint, pModifiers);
    }

    @Nullable
    @Override
    public ComponentPath getCurrentFocusPath() {
        return super.getCurrentFocusPath();
    }

    @Override
    public void setPosition(int pX, int pY) {
        super.setPosition(pX, pY);
    }
}
