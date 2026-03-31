package com.epxzzy.epxzzysabers.screen;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.item.types.RotarySaber;
import com.epxzzy.epxzzysabers.util.LevelHelper;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

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
        int cx = that.screenHeight / 2;
        int cy = that.screenWidth / 2;
        int j = cx - 15;
        int k = cy + 14;
        int rotx = cy - 8;
        int roty = cx - 16;
        Player play = that.minecraft.player;
        PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) play;
        int atk = MixinPlayer.getSaberAttackForm();
        int blk = MixinPlayer.getSaberBlockForm();

        boolean flying = play.getAbilities().flying;
        boolean usingItem = play.isUsingItem();
        float cooldown = (float) (RotarySaber.MAX_FLIGHT_COOLDOWN - MixinPlayer.getFlyCooldown()) / RotarySaber.MAX_FLIGHT_COOLDOWN;
        float duration = (float) MixinPlayer.getFlyDur() / RotarySaber.MAX_FLIGHT_DURATION;
        boolean flyPossible = duration == 0.0F &&cooldown == 1.0F && !usingItem;
        boolean flyHint = flyPossible && !flying && LevelHelper.EntityEquippedActiveItem(play, true, SaberItems.ROTARY_SABER.get());
        //pGuiGraphics.blit(ROTARY, k, j+ 16, 32, 0, 16, 7,16*3,7);

        if (duration >= 0.0F && flying) {
            int l = (int)(duration* 16.0F);
            pGuiGraphics.blit(ROTARY, rotx, roty, 0, 0, 16, 8,64,8);
            pGuiGraphics.blit(ROTARY, rotx, roty, 48, 0, l, 8,64,8);
        }
        if (cooldown <= 1.0F && !flying &&!usingItem) {
            int l = (int)(cooldown * 16.0F);
            pGuiGraphics.blit(ROTARY, rotx, roty, 0, 0, 16, 8, 64,8);
            pGuiGraphics.blit(ROTARY, rotx, roty, 16, 0, l, 8,64,8);
        }
        if (flyHint) {
            pGuiGraphics.blit(ROTARY, rotx, roty, 32, 0, 16, 8,64,8);
        }

        if (MixinPlayer.getSaberAttackAnim() > 0) {
            //pGuiGraphics.blit(ATTACK, k, j, 0, 0, 29, 29, 29, 29);
            pGuiGraphics.blit(ATTACKS, k, j+16, 0, ((atk)*29), 29, 29, 29, 29*9);
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
