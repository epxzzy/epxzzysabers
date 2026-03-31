package com.epxzzy.epxzzysabers.screen.stance;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.misc.KeyBinding;
import com.epxzzy.epxzzysabers.networking.SaberMessages;
import com.epxzzy.epxzzysabers.networking.packet.saber.ServerboundSaberStancePacket;
import com.epxzzy.epxzzysabers.rendering.playerposerenderers.BladeStance;
import com.epxzzy.epxzzysabers.screen.components.PentagonButton;
import com.epxzzy.epxzzysabers.util.AngleHelper;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

import static com.epxzzy.epxzzysabers.util.PlayerHelperLmao.STANCE_PREFERENCE;

public class StancePreferenceScreen extends Screen {
    int Selection;

    private static final ResourceLocation TEMP = new ResourceLocation("textures/gui/container/loom.png");

    public List<PentagonButton> Choices = new ArrayList<>();
    public StancePreferenceScreen(Player player) {
        super(Component.empty());
        this.Selection = player.getEntityData().get(STANCE_PREFERENCE);
    }

    @Override
    public void init(){
        List<BladeStance> iteratur = BladeStance.getStances();
        int centerX = this.width / 2,
                centerY = this.height / 2,
                radius = 60;
        float offset4Fancy = AngleHelper.rad(180);
        for (int i = 0; i < iteratur.size(); i++) {
            boolean flipped = i%2 == 0;
            double angle = -((2 * Math.PI / 10) * i - Math.PI / 2)-(offset4Fancy)+AngleHelper.rad(36);

            int vertX = (int) (centerX + radius * Math.cos(angle));
            int vertY = (int) (centerY+ radius * Math.sin(angle));

            PentagonButton button = new PentagonButton.Builder(CommonComponents.GUI_CANCEL)
                    .bounds(vertX, vertY, 44)
                    .rot(flipped?180:0)
                    .texture(epxzzySabers.asResource("textures/gui/stance_"+iteratur.get(i).ordinal()+".png"))
                    .build();

            this.Choices.add(i, button);
            this.addRenderableWidget(button);
        }
        setSelection(Selection);
    }

    @Override
    public void mouseMoved(double pMouseX, double pMouseY) {
        for (int i = 0; i < Choices.size(); i++) {
            if (Choices.get(i).isMouseOver(pMouseX, pMouseY)){
                Selection = i+1;
                Choices.get(i).selected = true;
            }
            else {
                Choices.get(i).selected = false;
            }
        }

        super.mouseMoved(pMouseX, pMouseY);
    }

    public void setSelection(int i){
        for (int j = 0; j < Choices.size(); j++) {
            Choices.get(j).selected = (i-1)==j;
        }
    }

    public void rollSelection(int p) {
        int next = p > 0 ? Selection + 1 : Selection - 1;

        if (next > Choices.size()) next = 1;
        if (next <= 0) next = Choices.size();

        Selection = next;
        setSelection(next);
    }


    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        rollSelection((int)pDelta);
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            confirm();
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void confirm(){
        if (Selection >= 1 && Selection <= BladeStance.getStances().size()) {
            PlayerHelperLmao mixinplayer = (PlayerHelperLmao) minecraft.player;
            mixinplayer.setSaberStanceForm(Selection);
        }
        this.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == KeyBinding.SABER_STANCEPREF_KEY.getKey().getValue()) {
            this.onClose();
            return true;
        }
        if(pKeyCode == GLFW.GLFW_KEY_UP || pKeyCode == GLFW.GLFW_KEY_RIGHT || pKeyCode == GLFW.GLFW_KEY_D || pKeyCode == GLFW.GLFW_KEY_W ){
            rollSelection(-1);
        }
        if(pKeyCode == GLFW.GLFW_KEY_DOWN || pKeyCode == GLFW.GLFW_KEY_LEFT || pKeyCode == GLFW.GLFW_KEY_A || pKeyCode == GLFW.GLFW_KEY_S ){
            rollSelection(1);
        }
        if(pKeyCode == GLFW.GLFW_KEY_ENTER || pKeyCode == GLFW.GLFW_KEY_SPACE ){
            confirm();
        }

        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
}

