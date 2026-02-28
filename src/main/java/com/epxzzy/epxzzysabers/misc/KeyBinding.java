package com.epxzzy.epxzzysabers.misc;


import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_MISC = "key.categories.misc";
    public static final String SABER_ABILITY = "key.epxzzysabers.saber_ability";
    public static final String SABER_STANCE = "key.epxzzysabers.saber_stance";
    public static final KeyMapping SABER_ABILITY_KEY = new KeyMapping(SABER_ABILITY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, KEY_CATEGORY_MISC);
    public static final KeyMapping SABER_STANCE_KEY = new KeyMapping(SABER_STANCE, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_MISC);

}