package com.epxzzy.createsaburs.networking.packet;

import com.epxzzy.createsaburs.createsaburs;
//import com.epxzzy.createsaburs.entity.custom.PlasmaBolt;
import com.epxzzy.createsaburs.entity.custom.PlasmaBolt;
import com.epxzzy.createsaburs.entity.custom.ThrownRotarySaber;
import com.epxzzy.createsaburs.item.ModItems;
import com.epxzzy.createsaburs.item.saburtypes.RotarySaber;
import com.epxzzy.createsaburs.sound.ModSounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ServerboundSaberAbilityPacket {
    public ServerboundSaberAbilityPacket(){
    }


    public ServerboundSaberAbilityPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        //createsaburs.LOGGER.info("message reciveved");
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {
            //createsaburs.LOGGER.info("message processed");

            //createsaburs.LOGGER.info(Objects.requireNonNull(contextt.getSender())+" named bond having a stonk");

            if(contextt.getSender() != null){
                ServerPlayer player = contextt.getSender();
                Level pLevel = player.level();
                ItemStack pStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                
                if(pStack.is(ModItems.ROTARY_SABER.get())) {
                    ThrownRotarySaber thrownsaber = new ThrownRotarySaber(pLevel, player, pStack);
                    createsaburs.LOGGER.warn("colour given is:" + RotarySaber.getColor(pStack));
                    thrownsaber.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F + (float) 8 * 0.5F, 1.0F);
                    if (player.getAbilities().instabuild) {
                        thrownsaber.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }
                    player.swing(InteractionHand.MAIN_HAND, true);
                    pLevel.addFreshEntity(thrownsaber);
                    pLevel.playSound((Player) null, thrownsaber, ModSounds.ACTIVATION.get(), SoundSource.PLAYERS, 0.05F, 1.0F);
                    if (!player.getAbilities().instabuild) {
                        player.getInventory().removeItem(pStack);
                    }
                }

                if(pStack.is(ModItems.BLASTER_HYBRID.get())) {
                    PlasmaBolt blasterbolt = new PlasmaBolt(player, pLevel) {
                    };
                    blasterbolt.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F + (float) 1 * 0.5F, 1.0F);
                    pLevel.addFreshEntity(blasterbolt);
                    pLevel.playSound((Player) null, blasterbolt,SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, SoundSource.PLAYERS, 0.05F, 1.0F);
                }


                
                
            }

            contextt.getSender().sendSystemMessage(Component.literal("serverplayer name:" + contextt.getSender().getTabListDisplayName().getString() + " and thats about it"));


        });
        return true;
    }

}
