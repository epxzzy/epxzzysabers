package com.epxzzy.epxzzysabers.screen.hints;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.util.ConfigHolder;
import com.epxzzy.epxzzysabers.util.LevelHelper;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class RotaryHint extends HudHint{
    public static final ResourceLocation ROTARY = epxzzySabers.asResource("textures/gui/hints/rotary_cooldown.png");

    public static void renderOnCrosshair(GuiGraphics pGuiGraphics, Gui that, int frame) {
        int cx = that.screenHeight / 2;
        int cy = that.screenWidth / 2;
        int rotx = cy - 8;
        int roty = cx - 16;
        Player play = that.minecraft.player;
        PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) play;

        boolean incorrectGamemode = play.isCreative() || play.isSpectator();
        boolean flyingNotCreative = play.getAbilities().flying && !incorrectGamemode;
        boolean RotaryhasRightItem = LevelHelper.EntityEquippedActiveItem(play, true, SaberItems.ROTARY_SABER.get());
        boolean RotusingItem = play.isUsingItem()&& RotaryhasRightItem;

        float Rcooldown = (float) (ConfigHolder.ROTARY_FLIGHT_COOLDOWN - MixinPlayer.getFlyCooldown()) / ConfigHolder.ROTARY_FLIGHT_COOLDOWN;
        float Rduration = (float) MixinPlayer.getFlyDur() / ConfigHolder.ROTARY_FLIGHT_DURATION;

        boolean flyPossible = Rduration == 0.0F &&Rcooldown == 1.0F && !RotusingItem;

        boolean flyHint = flyPossible && !flyingNotCreative && LevelHelper.EntityEquippedActiveItem(play, true, SaberItems.ROTARY_SABER.get());

        if (Rduration >= 0.0F && flyingNotCreative) {
            int l = (int)(Rduration* 16.0F);
            pGuiGraphics.blit(ROTARY, rotx, roty, 0, frame, 16, 8, 64, 48);
            pGuiGraphics.blit(ROTARY, rotx, roty, 48, frame, l, 8, 64, 48);
        }
        if (Rcooldown < 1.0F && !flyingNotCreative) {
            if(incorrectGamemode) return;
            int l = (int)(Rcooldown * 16.0F);
            pGuiGraphics.blit(ROTARY, rotx, roty, 0, frame, 16, 8, 64, 48);
            pGuiGraphics.blit(ROTARY, rotx, roty, 16, frame, l, 8, 64, 48);
        }
        if (flyHint) {
            pGuiGraphics.blit(ROTARY, rotx, roty, 32, frame, 16, 8, 64, 48);
        }
    }

    public static void renderOnHotbar(GuiGraphics pGuiGraphics, Gui that,int frame) {
        int cx = that.screenHeight;
        int cy = that.screenWidth / 2;
        int rotx = cy - 8;
        int roty = cx - 31 - 14;
        Player play = that.minecraft.player;
        PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) play;

        boolean incorrectGamemode = play.isCreative() || play.isSpectator();
        boolean flyingNotCreative = play.getAbilities().flying && !incorrectGamemode;
        boolean RotaryhasRightItem = LevelHelper.EntityEquippedActiveItem(play, true, SaberItems.ROTARY_SABER.get());
        boolean RotusingItem = play.isUsingItem()&& RotaryhasRightItem;

        float Rcooldown = (float) (ConfigHolder.ROTARY_FLIGHT_COOLDOWN - MixinPlayer.getFlyCooldown()) / ConfigHolder.ROTARY_FLIGHT_COOLDOWN;
        float Rduration = (float) MixinPlayer.getFlyDur() / ConfigHolder.ROTARY_FLIGHT_DURATION;

        boolean flyPossible = Rduration == 0.0F &&Rcooldown == 1.0F && !RotusingItem;

        boolean flyHint = flyPossible && !flyingNotCreative && LevelHelper.EntityEquippedActiveItem(play, true, SaberItems.ROTARY_SABER.get());

        if (Rduration >= 0.0F && flyingNotCreative) {
            int l = (int)(Rduration* 16.0F);
            pGuiGraphics.blit(ROTARY, rotx, roty, 0, frame, 16, 8, 64, 48);
            pGuiGraphics.blit(ROTARY, rotx, roty, 48, frame, l, 8, 64, 48);
        }
        if (Rcooldown < 1.0F && !flyingNotCreative) {
            if(incorrectGamemode) return;
            int l = (int)(Rcooldown * 16.0F);
            pGuiGraphics.blit(ROTARY, rotx, roty, 0, frame, 16, 8, 64, 48);
            pGuiGraphics.blit(ROTARY, rotx, roty, 16, frame, l, 8, 64, 48);
        }
        if (flyHint) {
            pGuiGraphics.blit(ROTARY, rotx, roty, 32, frame, 16, 8, 64, 48);
        }
    }
}
