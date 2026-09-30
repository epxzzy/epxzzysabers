package dev.epxzzy.epxzzysabers.mixin.client;

import dev.epxzzy.epxzzysabers.core.foundation.misc.SaberBindings;
import dev.epxzzy.epxzzysabers.core.mixin.ISaberCancellableKeyboard;
import net.minecraft.client.KeyboardHandler;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class InterruptInputMixin implements ISaberCancellableKeyboard {
    //stnacing gotta cancle other shi
    private static boolean VoidShi = false;

    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private void onKeyInterrupt(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
       if(SaberBindings.handleKeypress(this, key, scancode, action, modifiers)) ci.cancel();
    }

    @Override
    public void setDown(boolean value) {
        VoidShi = value;
    }

    @Override
    public boolean getDown() {
        return VoidShi;
    }
}
