package com.epxzzy.createsaburs.event;

import com.epxzzy.createsaburs.CreateSaburs;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateSaburs.MOD_ID)
public class ModEvents {
    /*
    @SubscribeEvent
    public static void PlayerAttackLOL(AttackEntityEvent event){
        if(event.getEntity().level().isClientSide()){
            CreateSaburs.LOGGER.debug("attackevent runs on client");
        }
        else {
            CreateSaburs.LOGGER.debug("attackevent runs on server");
        }
    }
    @SubscribeEvent
    public static void PlayerHurtLOL(LivingHurtEvent event){
        if(!(event.getEntity() instanceof Player)){
           return;
        }
        if(event.getEntity().level().isClientSide()){
            CreateSaburs.LOGGER.debug("hurtevent runs on client");
        }
        else {
            CreateSaburs.LOGGER.debug("hurtevent runs on server");
        }
    }

     */
    //these are useless, not only do these fire wayy after the mixins
    //these also dont have a before/after
    //ie when the player has actually taken damage only then the hurt event fires.
}