package com.epxzzy.epxzzysabers.event;

import com.epxzzy.epxzzysabers.epxzzySabers;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = epxzzySabers.MOD_ID)
public class ModEvents {
    /*
    @SubscribeEvent
    public static void PlayerAttackLOL(AttackEntityEvent event){
        if(event.getEntity().level().isClientSide()){
            epxzzySabers.LOGGER.debug("attackevent runs on client");
        }
        else {
            epxzzySabers.LOGGER.debug("attackevent runs on server");
        }
    }
    @SubscribeEvent
    public static void PlayerHurtLOL(LivingHurtEvent event){
        if(!(event.getEntity() instanceof Player)){
           return;
        }
        if(event.getEntity().level().isClientSide()){
            epxzzySabers.LOGGER.debug("hurtevent runs on client");
        }
        else {
            epxzzySabers.LOGGER.debug("hurtevent runs on server");
        }
    }

     */
    //these are useless, not only do these fire wayy after the mixins
    //these also dont have a before/after
    //ie when the player has actually taken damage only then the hurt event fires.
}