package com.epxzzy.epxzzysabers.misc;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.utils.AngleHelper;
import com.epxzzy.epxzzysabers.utils.StackHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class KewlFightsOrchestrator {
    public static int DetermineNextPossibleAttack(int old, Player pPlayer){
        /*
        float headrotted = AngleHelper.normalizeAngle(pPlayer.getYRot());
        float bodyrotted = AngleHelper.normalizeAngle(pPlayer.rot);

        float rot = AngleHelper.calculateHeadOffset(bodyrotted,headrotted);
        epxzzySabers.LOGGER.debug("rot diff {} = {} - {}", rot, headrotted, bodyrotted);

         */



        if(pPlayer.fallDistance > 1)
            return 8;
        return StackHelper.random1to8(old);
    };

    public static int DetermineNextPossibleAttack(int old, Player pPlayer, Entity pEntity){
        if(pEntity instanceof LivingEntity pLiving){
            Player attacker = pPlayer;
            Entity target = pLiving;

            // Only proceed if the attacker is holding your lightsaber item (add your check here)
            // if (!isLightsaber(attacker.getMainHandItem())) return;

            // Get ray start (attacker's eye position) and end (extended look direction)
            Vec3 start = attacker.getEyePosition(1.0F);
            Vec3 look = attacker.getViewVector(1.0F);
            double reach = 3; // Or hardcode ~3.0 for default melee reach
            Vec3 end = start.add(look.scale(reach));

            // Get target's bounding box and clip the ray to find hit position
            AABB targetBox = target.getBoundingBox();
            Optional<Vec3> hitOpt = targetBox.clip(start, end);

            if (hitOpt.isPresent()) {
                Vec3 hitWorld = hitOpt.get();
                // Convert to relative position (local to target)
                Vec3 relative = hitWorld.subtract(target.position());

                // Get target's facing vectors for rotation-aware left/right
                Vec3 forward = target.getForward().normalize();
                Vec3 up = target.getUpVector(1).normalize();
                Vec3 right = forward.cross(up).normalize(); // Right vector relative to target's facing

                // Calculate lateral offset (positive = right, negative = left)
                double lateral = relative.dot(right);

                // Classify vertical position (adjust thresholds based on entity height; e.g., for Player ~1.8 tall)
                double height = relative.y;
                int bodyPart;
                if (height > 1.5) { // Head region
                    bodyPart = 8;
                    // Check for inverted: e.g., if attacker's Y is below target's feet or pitch is upward
                    if (attacker.getY() < target.getY() || attacker.getXRot() < -45) { // Example condition; tune this
                        bodyPart = 7;
                    }
                } else if (height > 1.0) { // Shoulder region
                    bodyPart = (lateral > 0) ? 1 : 2;
                } else if (height > 0.5) { // Torso region
                    bodyPart = (lateral > 0) ? 5 : 6;
                } else { // Knee/leg region
                    bodyPart = (lateral > 0) ? 3 : 4;
                }

                // Now play the deterministic animation based on bodyPart
                // Your animation code here, e.g., trigger packet to client for animation playback
                epxzzySabers.LOGGER.debug("found hit to be on: {}", bodyPart);
                return bodyPart;
            }
        }


        return 0;

    };


    public static int DetermineParryAnimation(Player pPlayer){
        if(pPlayer.getXRot() >= 40){
            //epxzzySabers.LOGGER.debug("behind the bacc lol");
            return 2;

        }

        else if(!pPlayer.isSprinting()) {
            //epxzzySabers.LOGGER.debug("yoda move");
            return 1;
        }

        //epxzzySabers.LOGGER.debug("evil spin");
        return 3;
    };




    public static boolean IsPlayerStationary(Player player){
        epxzzySabers.LOGGER.debug("len {}, normal len {}", player.getDeltaMovement().scale(2).length(), player.getDeltaMovement().normalize().length());
        return !(player.getDeltaMovement().lengthSqr() > 0.0784);
    }
}
