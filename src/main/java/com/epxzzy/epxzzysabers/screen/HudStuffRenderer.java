package com.epxzzy.epxzzysabers.screen;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.item.types.RotarySaber;
import com.epxzzy.epxzzysabers.item.types.SaberGauntlet;
import com.epxzzy.epxzzysabers.screen.hints.GauntletHint;
import com.epxzzy.epxzzysabers.screen.hints.RotaryHint;
import com.epxzzy.epxzzysabers.util.LevelHelper;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import net.minecraft.Util;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class HudStuffRenderer {
    public static final ResourceLocation ATTACKS = epxzzySabers.asResource("textures/gui/hints/attacks.png");
    public static final ResourceLocation BLOCKS = epxzzySabers.asResource("textures/gui/hints/blocks.png");
    public static final int[] OFFSETS = {0, 8, 16, 24, 32, 40};
    public static void renderStuff(GuiGraphics pGuiGraphics, Gui that) {
        Options options = that.minecraft.options;
        if (options.getCameraType().isFirstPerson() && !options.hideGui) {
            int frame = OFFSETS[(that.getGuiTicks()/2) % 6];

                if (that.minecraft.options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
                    renderCrosshairHud(pGuiGraphics, that, frame);
                    return;
                }
                if (that.minecraft.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
                    renderHotbarHud(pGuiGraphics, that, frame);
                    return;
                }
        }
    }
  
    public static void renderCrosshairHud(GuiGraphics pGuiGraphics, Gui that,int frame) {
        int cx = that.screenHeight / 2;
        int cy = that.screenWidth / 2;
        int j = cx - 14;
        int k = cy - 14;
        Player play = that.minecraft.player;
        PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) play;


        RotaryHint.renderOnCrosshair(pGuiGraphics, that, frame);

        GauntletHint.renderOnCrosshair(pGuiGraphics, that, frame);

        int atk = MixinPlayer.getSaberAttackForm();
        int blk = MixinPlayer.getSaberBlockForm();

        if (MixinPlayer.getSaberAttackAnim() > 0) {
            pGuiGraphics.blit(ATTACKS, k, j, 0, ((atk)*29), 29, 29, 29, 29*9);
        }
        if (MixinPlayer.getSaberDefendAnim() > 0) {
            pGuiGraphics.blit(BLOCKS, k, j, 0, ((blk)*29), 29, 29, 29, 29*9);
        }
    }


    public static void renderHotbarHud(GuiGraphics pGuiGraphics, Gui that, int frame) {
        int cx = that.screenHeight / 2;
        int cy = that.screenWidth / 2;
        int j = cx - 14;
        int k = cy - 14;

        PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) that.minecraft.player;

        RotaryHint.renderOnHotbar(pGuiGraphics, that, frame);
        GauntletHint.renderOnHotbar(pGuiGraphics, that, frame);

        int atk = MixinPlayer.getSaberAttackForm();
        int blk = MixinPlayer.getSaberBlockForm();

        if (MixinPlayer.getSaberAttackAnim() > 0) {
            pGuiGraphics.blit(ATTACKS, k, j, 0, ((atk)*29), 29, 29, 29, 29*9);
        }
        if (MixinPlayer.getSaberDefendAnim() > 0) {
            pGuiGraphics.blit(BLOCKS, k, j, 0, ((blk)*29), 29, 29, 29, 29*9);
        }
    }
}
