package com.epxzzy.epxzzysabers.misc;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.utils.StackHelper;
import net.minecraft.world.entity.player.Player;

public class KewlFightsOrchestrator {
    public static int DetermineNextPossibleAttack(int old, Player pPlayer){
        if(pPlayer.fallDistance > 1)
            return 8;
        return StackHelper.random1to8(old);
    };


    public static int DetermineParryAnimation(Player pPlayer){
        if(pPlayer.getXRot() >= 40){
            epxzzySabers.LOGGER.debug("behind the bacc lol");
            return 2;

        }

        else if(!pPlayer.isSprinting()) {
            epxzzySabers.LOGGER.debug("yoda move");
            return 1;
        }

        epxzzySabers.LOGGER.debug("evil spin");
        return 3;
    };




    public static boolean IsPlayerStationary(Player player){
        epxzzySabers.LOGGER.debug("len {}, normal len {}", player.getDeltaMovement().scale(2).length(), player.getDeltaMovement().normalize().length());
        return !(player.getDeltaMovement().lengthSqr() > 0.0784);
    }
}
