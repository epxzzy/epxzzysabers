package com.epxzzy.epxzzysabers.screen.hints;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.item.types.RotarySaber;
import com.epxzzy.epxzzysabers.item.types.SaberGauntlet;
import com.epxzzy.epxzzysabers.util.ConfigHolder;
import com.epxzzy.epxzzysabers.util.LevelHelper;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class GauntletHint extends HudHint{
    public static final ResourceLocation GAUNTLET = epxzzySabers.asResource("textures/gui/gauntlet_cooldown.png");

    public static void renderOnCrosshair(GuiGraphics pGuiGraphics, Gui that, int frame) {
        int cx = that.screenHeight / 2;
        int cy = that.screenWidth / 2;
        int rotx = cy - 8;
        int roty = cx - 16;
        Player play = that.minecraft.player;
        PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) play;

        boolean surging = SaberGauntlet.checkForSaberCharge(play, true);

        boolean ShasRightItem = LevelHelper.EntityEquippedActiveItem(play, true, SaberItems.SABER_GAUNTLET.get());
        boolean SusingItem = play.isUsingItem()&&ShasRightItem;

        float Scooldown = (float) (ConfigHolder.GAUNTLET_SURGE_CHARGEUP - play.getUseItemRemainingTicks()) / ConfigHolder.GAUNTLET_SURGE_CHARGEUP;
        float Sduration = (float) MixinPlayer.getChargeDuration() / ConfigHolder.GAUNTLET_SURGE_CHARGEUP;

        boolean surgePossible =  Scooldown == 1.0F && !SusingItem;
        boolean surgeHint = Sduration == 0.0F&&surgePossible && !surging && LevelHelper.EntityEquippedActiveItem(play, true, SaberItems.SABER_GAUNTLET.get());

        if (Sduration > 0.0F && !SusingItem) {
            pGuiGraphics.blit(GAUNTLET, rotx, roty, 48, frame, 16, 8, 64, 48);
        }

        if (Scooldown < 1.0F && SusingItem) {
            int l = (int)(Scooldown * 16.0F);
            pGuiGraphics.blit(GAUNTLET, rotx, roty, 0, frame, 16, 8, 64, 48);
            pGuiGraphics.blit(GAUNTLET, rotx, roty, 16, frame, l, 8, 64, 48);
        }

        if (surgeHint) {
            pGuiGraphics.blit(GAUNTLET, rotx, roty, 32, frame, 16, 8, 64, 48);
        }
    }

    public static void renderOnHotbar(GuiGraphics pGuiGraphics, Gui that,int frame) {
        int cx = that.screenHeight;
        int cy = that.screenWidth / 2;
        int rotx = cy - 8;
        int roty = cx - 31 - 14;

        Player play = that.minecraft.player;
        PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) play;

        boolean surging = SaberGauntlet.checkForSaberCharge(play, true);

        boolean ShasRightItem = LevelHelper.EntityEquippedActiveItem(play, true, SaberItems.SABER_GAUNTLET.get());
        boolean SusingItem = play.isUsingItem()&&ShasRightItem;

        float Scooldown = (float) (ConfigHolder.GAUNTLET_SURGE_CHARGEUP - play.getUseItemRemainingTicks()) / ConfigHolder.GAUNTLET_SURGE_CHARGEUP;
        float Sduration = (float) MixinPlayer.getChargeDuration() / ConfigHolder.GAUNTLET_SURGE_DURATION;

        boolean surgePossible =  Scooldown == 1.0F && !SusingItem;
        boolean surgeHint = Sduration == 0.0F&&surgePossible && !surging && LevelHelper.EntityEquippedActiveItem(play, true, SaberItems.SABER_GAUNTLET.get());

        if (Sduration > 0.0F && !SusingItem) {
            pGuiGraphics.blit(GAUNTLET, rotx, roty, 48, frame, 16, 8, 64, 48);
        }

        if (Scooldown < 1.0F && SusingItem) {
            int l = (int)(Scooldown * 16.0F);
            pGuiGraphics.blit(GAUNTLET, rotx, roty, 0, frame, 16, 8, 64, 48);
            pGuiGraphics.blit(GAUNTLET, rotx, roty, 16, frame, l, 8, 64, 48);
        }

        if (surgeHint) {
            pGuiGraphics.blit(GAUNTLET, rotx, roty, 32, frame, 16, 8, 64, 48);
        }
    }
}
