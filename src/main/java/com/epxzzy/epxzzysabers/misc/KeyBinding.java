package com.epxzzy.epxzzysabers.misc;


import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_TUTORIAL = "key.categories.misc";
    public static final String SABER_ABILITY = "key.epxzzySabers.saber_ability";
    public static final KeyMapping SABER_ABILITY_KEY = new KeyMapping(SABER_ABILITY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_ALT, KEY_CATEGORY_TUTORIAL);
}