package com.epxzzy.epxzzysabers.screen;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameType;

public class HudStuffRenderer {
    public static final ResourceLocation ATTACK = epxzzySabers.asResource("textures/gui/attack.png");
    public static final ResourceLocation ATTACKS = epxzzySabers.asResource("textures/gui/attacks.png");
    public static final ResourceLocation BLOCK = epxzzySabers.asResource("textures/gui/block.png");
    public static final ResourceLocation BLOCKS = epxzzySabers.asResource("textures/gui/blocks.png");

    public static void renderStuff(GuiGraphics pGuiGraphics, Gui that) {
        Options options = that.minecraft.options;
        if (options.getCameraType().isFirstPerson() && !options.hideGui) {
                if (that.minecraft.options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
                    renderCrosshairHud(pGuiGraphics, that);
                    return;
                }
                if (that.minecraft.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
                    renderHotbarHud(pGuiGraphics, that);
                    return;
                }
        }
    }
  
    public static void renderCrosshairHud(GuiGraphics pGuiGraphics, Gui that) {
        int j = that.screenHeight / 2 - 15;
        int k = that.screenWidth / 2 - 14;
        PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) that.minecraft.player;
        int atk = MixinPlayer.getSaberAttackForm();
        int blk = MixinPlayer.getSaberBlockForm();

        if (MixinPlayer.getSaberAttackAnim() > 0) {
            //pGuiGraphics.blit(ATTACK, k, j, 0, 0, 29, 29, 29, 29);
            pGuiGraphics.blit(ATTACKS, k, j, 0, ((atk)*29), 29, 29, 29, 29*9);
        }
        if (MixinPlayer.getSaberDefendAnim() > 0) {
            //pGuiGraphics.blit(BLOCK, k, j, 0, 0, 29, 29, 29, 29);
            pGuiGraphics.blit(BLOCKS, k, j, 0, ((blk)*29), 29, 29, 29, 29*9);
        }

    }


    public static void renderHotbarHud(GuiGraphics pGuiGraphics, Gui that) {
        int j = that.screenHeight / 2 - 15;
        int k = that.screenWidth / 2 - 14;
        PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) that.minecraft.player;

        if (MixinPlayer.getSaberAttackAnim() > 0) {
            pGuiGraphics.blit(ATTACK, k, j, 0, 0, 49, 49, 49, 49);

        }
        if (MixinPlayer.getSaberDefendAnim() > 0) {
            pGuiGraphics.blit(BLOCK, k, j, 0, 0, 49, 49, 49, 49);
        }
    }
}
