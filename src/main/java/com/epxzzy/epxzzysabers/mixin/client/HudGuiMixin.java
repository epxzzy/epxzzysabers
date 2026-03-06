package com.epxzzy.epxzzysabers.mixin.client;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.screen.HudStuffRenderer;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.epxzzy.epxzzysabers.screen.HudStuffRenderer.ATTACK;

@Mixin(Gui.class)
public class HudGuiMixin {

    @Inject(
            method = "renderCrosshair",
            at = @At(value= "TAIL"),
            cancellable = true)
    public void renderSaberStatusCrosshair(GuiGraphics pGuiGraphics, CallbackInfo ci) {
        Gui that = ((Gui) (Object) this);
        HudStuffRenderer.renderStuff(pGuiGraphics, that);
    }


}
