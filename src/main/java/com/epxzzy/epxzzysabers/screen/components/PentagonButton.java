package com.epxzzy.epxzzysabers.screen.components;

import com.epxzzy.epxzzysabers.misc.KeyBinding;
import com.epxzzy.epxzzysabers.util.AngleHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PentagonButton extends AbstractButton {
    public List<Pair<Integer, Integer>> vertices = new ArrayList<>();

    public PentagonButton(int pX, int pY, int pWidth, int pHeight,int pRotation, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);

        int rad = pWidth/2;

        for (int i = 0; i < 6; i++) {
            double rot = AngleHelper.rad(pRotation);
            double angle = (2 * Math.PI / 5) * i - Math.PI / 2 + rot;

            int vertX = (int) (pX + rad * Math.cos(angle));
            int vertY = (int) (pY + rad * Math.sin(angle));

            this.vertices.add(i, Pair.of(vertX, vertY));
        }
    }


    protected PentagonButton(Builder builder) {
        this(builder.x, builder.y, builder.radius, builder.radius, builder.rot, builder.message);
        setTooltip(builder.tooltip); // Forge: Make use of the Builder tooltip
    }
    @Override
    public void onPress() {

    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        // --- OUTLINE ---
        buffer.begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);

        for (Pair<Integer, Integer> vertex : this.vertices) {
            buffer.vertex(vertex.getFirst(), vertex.getSecond(), 0)
                    .color(1.0f, 1.0f, 1.0f, 1.0f)
                    .endVertex();
        }

        // close the loop back to vertex 0
        buffer.vertex(this.vertices.get(0).getFirst(), this.vertices.get(0).getSecond(), 0)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .endVertex();

        tesselator.end();

        RenderSystem.disableBlend();
    }

    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return this.active && this.visible && pnpoly(pMouseX, pMouseY);
    }

    @Override
    public boolean clicked(double pMouseX, double pMouseY) {
        return this.active && this.visible && pnpoly(pMouseX, pMouseY);
    }

    /*
    source: https://wrfranklin.org/Research/Short_Notes/pnpoly.html
     */
    private boolean pnpoly(double pointX, double pointY) {
        int i, j;
        boolean c = false;
        int size = this.vertices.size();

        for (i = 0, j = size - 1; i < size; j = i++) {
            double vYi = this.vertices.get(i).getSecond();
            double vYj = this.vertices.get(j).getSecond();
            double vXi = this.vertices.get(i).getFirst();
            double vXj = this.vertices.get(j).getFirst();

            if (((vYi > pointY) != (vYj > pointY))
                    && (pointX < (vXj - vXi) * (pointY - vYi) / (vYj - vYi) + vXi)) {
                c = !c;
            }
        }
        return c;
    }



    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }

    @OnlyIn(Dist.CLIENT)
    public static class Builder {
        private final Component message;
        //private final Button.OnPress onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int radius;
        private int rot = 0;

        public Builder(Component pMessage
                //, Button.OnPress pOnPress
                        ) {
            this.message = pMessage;
            //this.onPress = pOnPress;
        }

        public Builder pos(int pX, int pY) {
            this.x = pX;
            this.y = pY;
            return this;
        }
        public Builder rot(int prot) {
            this.rot = prot;
            return this;
        }

        public Builder rad(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder bounds(int pX, int pY, int rad) {
            return this.pos(pX, pY).rad(rad);
        }

        public Builder tooltip(@Nullable Tooltip pTooltip) {
            this.tooltip = pTooltip;
            return this;
        }

        public Builder createNarration(Button.CreateNarration pCreateNarration) {
            //this.createNarration = pCreateNarration;
            return this;
        }

        public PentagonButton build() {
            return build(PentagonButton::new);
        }

        public PentagonButton build(java.util.function.Function<Builder, PentagonButton> builder) {
            return builder.apply(this);
        }
    }
}
