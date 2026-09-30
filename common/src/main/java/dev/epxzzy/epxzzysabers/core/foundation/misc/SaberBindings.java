package dev.epxzzy.epxzzysabers.core.foundation.misc;

import com.mojang.blaze3d.platform.InputConstants;
import dev.epxzzy.epxzzysabers.core.mixin.ISaberCancellableKeyboard;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class SaberBindings {
    public static final String KEY_CATEGORY_MISC = "key.categories.misc";
    public static final String SABER_ABILITY = "key.epxzzysabers.saber_ability";
    public static final String SABER_STANCE = "key.epxzzysabers.saber_stance";

    public static final KeyMapping SABER_ABILITY_KEY = new KeyMapping(
        SABER_ABILITY,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_LEFT_ALT,
        KEY_CATEGORY_MISC
    );
    public static final KeyMapping SABER_STANCE_KEY = new KeyMapping(
        SABER_STANCE,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_V,
        KEY_CATEGORY_MISC
    );


    public static boolean handleKeypress(ISaberCancellableKeyboard obj, int key, int scancode, int action, int modifiers) {
        Minecraft client = Minecraft.getInstance();
        if (client.screen != null && (client.player != null && client.level != null)) return false;
        //ISaberUserEntity MixinPlayer = (ISaberUserEntity) ((ISaberUserEntity) client.player);

        if(SABER_STANCE_KEY.matches(key, scancode)){
            //bitwise and cuase modifiers are given in binary
            boolean plusShift = (modifiers & GLFW.GLFW_MOD_SHIFT) != 0;
            if((action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT)&&plusShift){
                //Minecraft.getInstance().setScreen(new StancePreferenceScreen(player));
                return true;
            }

            if(action == GLFW.GLFW_PRESS){
                obj.setDown(true);
                client.player.displayClientMessage(Component.literal("stance down"), true);
                return true;
            }
            else if(action == GLFW.GLFW_RELEASE){
                obj.setDown(false);
                client.player.displayClientMessage(Component.literal("stance up"), true);
                return true;
            }

        }

        //gotta find a better way to stop the player
        //if(obj.getDown()) return true;

        if (SABER_ABILITY_KEY.matches(key, scancode)) {
            client.player.displayClientMessage(Component.literal("saber ability key"), true);
            return true;
        }

        return false;
    }

}