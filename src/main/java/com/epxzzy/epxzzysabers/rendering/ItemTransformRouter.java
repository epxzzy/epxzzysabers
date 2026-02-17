package com.epxzzy.epxzzysabers.rendering;

import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.item.types.RotarySaber;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.parry.heavy.HeavyItemRenderer;
import com.epxzzy.epxzzysabers.rendering.parry.light.LightItemRenderer;
import com.epxzzy.epxzzysabers.rendering.parry.rotary.RotaryItemRenderer;
import com.epxzzy.epxzzysabers.rendering.playerposerenderers.PlayerAttackRenderer;
import com.epxzzy.epxzzysabers.rendering.playerposerenderers.PlayerBlockRenderer;
import com.epxzzy.epxzzysabers.rendering.playerposerenderers.PlayerStanceRenderer;
import com.epxzzy.epxzzysabers.rendering.parry.heavy.HeavyArmRenderer;
import com.epxzzy.epxzzysabers.rendering.parry.light.LightArmRenderer;
import com.epxzzy.epxzzysabers.util.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import static com.epxzzy.epxzzysabers.rendering.parry.rotary.RotaryPoseRenderer.setRotaryFlyPose;

/*
 * a facade for item transforms
 */
public class ItemTransformRouter {

    public static void transform(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity) {
        if((entity.swinging) && StackHelper.isActive(stack)) {
            if ((((PlayerHelperLmao) entity).getSaberAttackAnim() > 0)) return;
            //do not override attack hit

            if(TagHelper.isLightWeapon(stack)){
                LightItemRenderer.setItemPose(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
                return;
            }

            if(TagHelper.isHeavyWeapon(stack)){
                HeavyItemRenderer.setItemPose(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
                return;
            }

            if(TagHelper.isUnusualWeapon(stack)){
                //redirection to light weapon rendering for now
                LightItemRenderer.setItemPose(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
                return;
            }

        }


    }



}
