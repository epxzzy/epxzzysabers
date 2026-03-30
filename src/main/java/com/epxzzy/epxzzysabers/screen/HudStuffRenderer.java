package com.epxzzy.epxzzysabers.screen;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.types.RotarySaber;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

public class HudStuffRenderer {
    public static final ResourceLocation ATTACK = epxzzySabers.asResource("textures/gui/attack.png");
    public static final ResourceLocation ATTACKS = epxzzySabers.asResource("textures/gui/attacks.png");
    public static final ResourceLocation BLOCK = epxzzySabers.asResource("textures/gui/block.png");
    public static final ResourceLocation BLOCKS = epxzzySabers.asResource("textures/gui/blocks.png");
    public static final ResourceLocation ROTARY = epxzzySabers.asResource("textures/gui/rotary_cooldown.png");

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
        Player play = that.minecraft.player;
        PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) play;
        int atk = MixinPlayer.getSaberAttackForm();
        int blk = MixinPlayer.getSaberBlockForm();

        int f = MixinPlayer.getFlyCooldown() / RotarySaber.MAX_FLIGHT_COOLDOWN;

        boolean canfly = MixinPlayer.getFlightDuration() == RotarySaber.MAX_FLIGHT_DURATION;

        //pGuiGraphics.blit(ROTARY, k, j+ 16, 32, 0, 16, 7,16*3,7);

        /*
        if ( canfly) {
            pGuiGraphics.blit(ROTARY, k, j, 32, 0, 16, 7,16*3,7);
        }
        if (f > 1.0F) {
            int l = (int)(f * 17.0F);
            pGuiGraphics.blit(ROTARY, k, j+16, 0, 0, 16, 7,16*3,7);
            pGuiGraphics.blit(ROTARY, k, j+16, 16, 0, l, 7,16*3,7);
        }
         */

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
