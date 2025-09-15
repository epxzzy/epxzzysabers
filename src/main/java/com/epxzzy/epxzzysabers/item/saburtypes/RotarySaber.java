package com.epxzzy.epxzzysabers.item.saburtypes;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.ModItems;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.networking.ModMessages;
import com.epxzzy.epxzzysabers.networking.packet.ServerboundSaberDeflectPacket;
import com.epxzzy.epxzzysabers.rendering.RotarySaberItemRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.SimpleCustomRenderer;
import com.epxzzy.epxzzysabers.sound.ModSounds;
import com.epxzzy.epxzzysabers.utils.LevelHelper;
import com.epxzzy.epxzzysabers.utils.ModTags;
import com.epxzzy.epxzzysabers.utils.StackHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class RotarySaber extends Protosaber {
    public int flyCooldown = 40;
    //160 == cant fly, 0 == can fly
    public int flightDuration = 200;
    //0 == no more fly, 300 == flyyy

    public RotarySaber(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, pRANGE , pDamage, pSpeed);
    }
    @Override @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_BETTER_RENDERER = new RotarySaberItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_BETTER_RENDERER));
    }

    public static int getColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getOrCreateTagElement("display");
        if (compoundtag.getInt("colour") == 0) {
            //setColor(pStack, 65280);
            return 16711680;
        }
        return Objects.requireNonNull(pStack.getTagElement("display")).getInt("colour");
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        /*if(pRemainingUseDuration > 120){
            //pLivingEntity.stopUsingItem();
            //return;
        }

         */
        if (pRemainingUseDuration % 2 == 0 && pStack.getOrCreateTag().getBoolean("FlyBoiii")) {
            if (pLivingEntity instanceof Player pPlayer && (pPlayer.getAbilities().flying)) {
                pLivingEntity.level().playSound((Player) null,
                        pLivingEntity.blockPosition(), ModSounds.SWING.get(),
                        SoundSource.PLAYERS,
                        0.1F,
                        0.9F
                );
            }
        }
        //super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if(pEntity instanceof LivingEntity pLiving && !(pLevel.isClientSide())){
/*
            if(pLiving instanceof Player pPlayer && pPlayer.getAbilities().flying){
                --this.flightDuration;
                if(this.flightDuration >= 0){
                    pPlayer.stopUsingItem();
                    createsaburs.LOGGER.info("you can no longer fly");
                    this.flightDuration = 40;
                }
            }

 */
            if(pLiving instanceof Player pPlayer && (pPlayer.getAbilities().flying)){
                if(this.flightDuration >= 1){
                    --this.flightDuration;

                    if(this.flightDuration== 0) {
                        pPlayer.stopUsingItem();
                        epxzzySabers.LOGGER.info("you can no longer fly");
                    }
                }
            }

            if(pLiving instanceof Player pPlayer && !(pPlayer.getAbilities().flying)){
                if(this.flyCooldown >= 1){
                    --this.flyCooldown;
                    if(this.flyCooldown == 0) {
                        this.flightDuration = 200;
                        epxzzySabers.LOGGER.info("you can now fly");
                    }
                }
            }

        }
    }
    public boolean isInAir(Player pPlayer){
        BlockPos pos = pPlayer.blockPosition();
        Level level = pPlayer.level();
        return
                level.getBlockState(pos.below()).isAir()&&
                        level.getBlockState(pos).isAir()&&
                        level.getBlockState(pos.above()).isAir();
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (readActivetag(pPlayer.getItemInHand(pHand)) && !pLevel.isClientSide) {
            if((pPlayer.xRotO < -35 || this.isInAir(pPlayer))){
                if(this.flyCooldown == 0 && this.flightDuration >= 1) {
                    Vec3 bbc = pPlayer.position();
                    pPlayer.teleportTo(bbc.x, bbc.y+0.5, bbc.z);

                    pPlayer.getAbilities().flying = true;
                    pPlayer.onUpdateAbilities();


                    CompoundTag tug = itemstack.getOrCreateTag().copy();
                    tug.putBoolean("FlyBoiii", true);
                    itemstack.setTag(tug);
                    //this.flyCooldown = 40;
                    epxzzySabers.LOGGER.info("flying activated");
                }

                if(this.flyCooldown >= 1 && this.flightDuration == 0){
                    epxzzySabers.LOGGER.info("you cant seem to fly, flightcooldown: "+this.flyCooldown+ " and flightduration: " + this.flightDuration);
                }

            }
            pPlayer.startUsingItem(pHand);
        }

        if (pPlayer.isShiftKeyDown() && pHand == InteractionHand.MAIN_HAND) {
            super.ToggleSaberCore(pLevel, pPlayer, itemstack);
            pPlayer.stopUsingItem();
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
        }
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public void onStopUsing(ItemStack pStack, LivingEntity entity, int count) {
        CompoundTag nbeetea = pStack.getOrCreateTag();
        nbeetea.putBoolean("BlockBoiii", false);

        nbeetea.putBoolean("FlyBoiii", false);
        if (entity instanceof Player pPlayer && pPlayer.getAbilities().flying){
            ((Player) entity).getAbilities().flying = false;
            ((Player) entity).onUpdateAbilities();
            epxzzySabers.LOGGER.info("flying deactivated");
            this.flyCooldown = 40;
        }

        pStack.setTag(nbeetea);
        super.onStopUsing(pStack, entity, count);
    }

    public static boolean checkForSaberBlock(Player Entityy) {
        return LevelHelper.EntityBlockingWithActiveItem(Entityy, ModItems.ROTARY_SABER.get());
    }

    public static boolean[] checkForSaberFly(Entity Entityy) {

        if (Entityy instanceof Player) {
            //((Player) Entityy).getMainHandItem().getOrCreateTag().getBoolean("FlyBoiii");

            boolean mainhand = (((Player) Entityy).getMainHandItem().is(ModItems.ROTARY_SABER.get()) && ((Player) Entityy).getMainHandItem().getOrCreateTag().getBoolean("FlyBoiii"));
            boolean offhand = (((Player) Entityy).getOffhandItem().is(ModItems.ROTARY_SABER.get()) && ((Player) Entityy).getOffhandItem().getOrCreateTag().getBoolean("FlyBoiii"));
            //checking for entity fly ability here makes flight animation fine in singleplayer, but broken in multiplayer

            //first one meaning flight is true, second specifies the hand, third is both hands have one
            //TODO: drop offhand item use support
            return new boolean[]{(mainhand || offhand), offhand, mainhand && offhand};
        }
        return new boolean[]{false, false, false};
    }


}
