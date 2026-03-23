package com.epxzzy.epxzzysabers.screen.stance;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.misc.KeyBinding;
import com.epxzzy.epxzzysabers.rendering.playerposerenderers.BladeStance;
import com.epxzzy.epxzzysabers.screen.components.PentagonButton;
import com.epxzzy.epxzzysabers.util.AngleHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
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

import java.util.List;


public class StancePreferenceScreen extends Screen {
    int Selection;
    public static final EntityDataAccessor<Integer> STANCE_PREFERENCE = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);

    public StancePreferenceScreen(Player player) {
        super(Component.empty());
        this.Selection = player.getEntityData().get(STANCE_PREFERENCE);
    }

    @Override
    public void init(){
        List<BladeStance> iteratur = BladeStance.getStances();
        //addRenderableWidget(new PentagonButton())
        int centerX = this.width / 2,
                centerY = this.height / 2,
                radius = 60;
        float offset4Fancy = AngleHelper.rad(180);
        for (int i = 0; i < iteratur.size(); i++) {
            boolean flipped = i%2 == 0;
            double angle = ((2 * Math.PI / 10) * i - Math.PI / 2)+(offset4Fancy);

            int vertX = (int) (centerX + radius * Math.cos(angle));
            int vertY = (int) (centerY+ radius * Math.sin(angle));

            this.addRenderableWidget(
                    new PentagonButton.Builder(CommonComponents.GUI_CANCEL)
                            .bounds(vertX, vertY, 44)
                            .rot(flipped?180:0)
                            .build());

        }
        /*
        this.addRenderableWidget(
                new PentagonButton
                        .Builder(CommonComponents.GUI_CANCEL)
                        .bounds(this.width/2,this.height/2, 50, 50)
                        .rot(180)
                        .build()
        );
        */
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        List<BladeStance> iteratur = BladeStance.getStances();
        int h = gui.guiHeight() / 2 + 35;
        int w = gui.guiWidth() / 2 - 85;
        //for (int i = 0; i < iteratur.size(); i++) {
        //    int tsw = w+(20*i)+15;
        //todo make buttons and shi
        //}
        super.render(gui, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        int next = pDelta > 0 ? Selection + 1 : Selection - 1;

        if (next > 8) next = 1;
        if (next < 1) next = 8;

        Selection = next;
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
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
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
}

