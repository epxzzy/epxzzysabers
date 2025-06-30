package com.epxzzy.createsaburs.misc;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NonEntityKeyframeAnimations {

    public NonEntityKeyframeAnimations() {
    }

    // Literally just vanilla's KeyframeAnimations but modified to not use the entity HierarchicalModel

    public static void animate(NonEntityHierarchicalModel pModel, AnimationDefinition pAnimationDefinition, long pAccumulatedTime, float pScale, Vector3f pAnimationVecCache) {
        float elapsedSeconds = getElapsedSeconds(pAnimationDefinition, pAccumulatedTime);

        for (Map.Entry<String, List<AnimationChannel>> stringListEntry : pAnimationDefinition.boneAnimations().entrySet()) {
            Optional<ModelPart> modelParts = pModel.getAnyDescendantWithName( stringListEntry.getKey());
            List<AnimationChannel> animationChannels = stringListEntry.getValue();
            modelParts.ifPresent((p_232330_) -> animationChannels.forEach((p_288241_) -> {
                Keyframe[] keyframes = p_288241_.keyframes();
                int max = Math.max(0, Mth.binarySearch(0, keyframes.length, (p_232315_) -> elapsedSeconds <= keyframes[p_232315_].timestamp()) - 1);
                int min = Math.min(keyframes.length - 1, max + 1);
                Keyframe maxKeyframe = keyframes[max];
                Keyframe minKeyframe = keyframes[min];
                float time = elapsedSeconds - maxKeyframe.timestamp();
                float f;
                if (min != max) {
                    f = Mth.clamp(time / (minKeyframe.timestamp() - maxKeyframe.timestamp()), 0.0F, 1.0F);
                } else {
                    f = 0.0F;
                }

                minKeyframe.interpolation().apply(pAnimationVecCache, f, keyframes, max, min, pScale);
                p_288241_.target().apply(p_232330_, pAnimationVecCache);
            }));
        }

    }

    private static float getElapsedSeconds(AnimationDefinition pAnimationDefinition, long pAccumulatedTime) {
        float accumulatedTime = (float)pAccumulatedTime / 1000.0F;
        return pAnimationDefinition.looping() ? accumulatedTime % pAnimationDefinition.lengthInSeconds() : accumulatedTime;
    }
}