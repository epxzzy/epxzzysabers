package com.epxzzy.createsaburs.item.saburtypes;

import com.epxzzy.createsaburs.CreateSaburs;
import com.epxzzy.createsaburs.item.ModItems;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.networking.ModMessages;
import com.epxzzy.createsaburs.networking.packet.ServerboundSaberDeflectPacket;
import com.epxzzy.createsaburs.rendering.RotarySaberItemRenderer;
import com.epxzzy.createsaburs.rendering.foundation.SimpleCustomRenderer;
import com.epxzzy.createsaburs.sound.ModSounds;
import com.epxzzy.createsaburs.utils.ModTags;
import com.epxzzy.createsaburs.utils.StackHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class RotarySaber extends Protosaber {
    public int flightDuration = 200;
    //0 == no more fly, 300 == flyyy

    public RotarySaber(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, pRANGE, pDamage, pSpeed);
    }

    @Override
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
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        //if(pStack.getOrCreateTag().getBoolean("FlyBoiii")) {
        //return UseAnim.CROSSBOW;
        //}
        //UseAnim.
        return super.getUseAnimation(pStack);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pEntity instanceof LivingEntity pLiving && !(pLevel.isClientSide())) {
/*
            if(pLiving instanceof Player pPlayer && pPlayer.getAbilities().flying){
                --this.flightDuration;
                if(this.flightDuration >= 0){
                    pPlayer.stopUsingItem();
                    CreateSaburs.LOGGER.info("you can no longer fly");
                    this.flightDuration = 40;
                }
            }

 */
            if (pLiving instanceof Player pPlayer && (pPlayer.getAbilities().flying)) {
                if (this.flightDuration >= 1) {
                    --this.flightDuration;

                    if (this.flightDuration == 0) {
                        pPlayer.stopUsingItem();
                        CreateSaburs.LOGGER.info("you can no longer fly");
                    }
                }
            }
            if (pLiving instanceof Player pPlayer && !(pPlayer.getAbilities().flying)) {
                this.flightDuration = 200;
            }
        }
    }

    public int getUseDuration(ItemStack pStack) {
        if (pStack.getOrCreateTag().getBoolean("FlyBoiii")) {
            return 20 * 10;
        }
        return 20 * 2;
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        /*if(pRemainingUseDuration > 120){
            //pLivingEntity.stopUsingItem();
            //return;
        }

         */
        if (pRemainingUseDuration % 2 == 0) {
            if (pLivingEntity instanceof Player pPlayer && !(pPlayer.getAbilities().flying)) {
                ModMessages.sendToServer(new ServerboundSaberDeflectPacket());
                pLivingEntity.level().playSound((Player) null,
                        pLivingEntity.blockPosition(), ModSounds.SWING.get(),
                        SoundSource.PLAYERS,
                        0.1F,
                        0.7F
                );

            } else {
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

    public boolean isInAir(Player pPlayer) {
        BlockPos pos = pPlayer.blockPosition();
        Level level = pPlayer.level();
        return level.getBlockState(pos.below()).isAir() &&
                level.getBlockState(pos.below(2)).isAir() &&
                level.getBlockState(pos.below(3)).isAir() &&
                level.getBlockState(pos.above()).isAir();
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        CompoundTag tug = itemstack.getOrCreateTag().copy();
        tug.putBoolean("BlockBoiii", true);
        itemstack.setTag(tug);

        if (readActivetag(pPlayer.getItemInHand(pHand)) && !pLevel.isClientSide) {
            if ((pPlayer.xRotO < -35 || this.isInAir(pPlayer))) {
                if (this.flightDuration >= 1) {
                    pPlayer.getAbilities().flying = true;
                    pPlayer.onUpdateAbilities();

                    CompoundTag tuge = itemstack.getOrCreateTag().copy();
                    tuge.putBoolean("FlyBoiii", true);
                    itemstack.setTag(tuge);
                    //this.flyCooldown = 40;
                    CreateSaburs.LOGGER.info("flying activated");
                }

                if (this.flightDuration == 0) {
                    CreateSaburs.LOGGER.info("you cant seem to fly, flightduration: ");
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

        if (pStack.getOrCreateTag().getBoolean("FlyBoiii")) {
            nbeetea.putBoolean("FlyBoiii", false);
            ((Player) entity).getAbilities().flying = false;
            ((Player) entity).onUpdateAbilities();
            CreateSaburs.LOGGER.info("flying deactivated");
            ((Player) entity).getCooldowns().addCooldown(entity.getUseItem().getItem(), 20 * 6);
        } else {
            ((Player) entity).getCooldowns().addCooldown(entity.getUseItem().getItem(), (int) (20 * 2.5));
        }

        pStack.setTag(nbeetea);
        super.onStopUsing(pStack, entity, count);
    }

    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand) {
        if (Entityy instanceof LivingEntity) {
            if (Mainhand)
                return ((LivingEntity) Entityy).getMainHandItem().is(ModItems.ROTARY_SABER.get()) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii");
            return ((LivingEntity) Entityy).getOffhandItem().is(ModItems.ROTARY_SABER.get()) && ((LivingEntity) Entityy).getOffhandItem().getOrCreateTag().getBoolean("ActiveBoiii");
        }
        return false;
    }

    public static boolean checkForSaberBlock(Player Entityy) {
        /*
        CreateSaburs.LOGGER.info("first:" +Entityy.getMainHandItem().is(ModItems.ROTARY_SABER.get()));
        CreateSaburs.LOGGER.info("second:" +Entityy.getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii") );
        CreateSaburs.LOGGER.info("third:" +Entityy.isUsingItem());

         */
        //Entityy.getMainHandItem().is(RotarySaber)
        return Entityy.getMainHandItem().is(ModItems.ROTARY_SABER.get()) && Entityy.getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii") && Entityy.isUsingItem();
    }

    public static boolean[] checkForSaberFly(Entity Entityy) {

        if (Entityy instanceof Player) {
            ((Player) Entityy).getMainHandItem().getOrCreateTag().getBoolean("FlyBoiii");

            boolean mainhand = (((Player) Entityy).getMainHandItem().is(ModItems.ROTARY_SABER.get()) && ((Player) Entityy).getMainHandItem().getOrCreateTag().getBoolean("FlyBoiii")) && ((Player) Entityy).getAbilities().flying;
            boolean offhand = (((Player) Entityy).getOffhandItem().is(ModItems.ROTARY_SABER.get()) && ((Player) Entityy).getOffhandItem().getOrCreateTag().getBoolean("FlyBoiii")) && ((Player) Entityy).getAbilities().flying;

            //first one meaning flight is true, second specifies the hand, third is both hands have one
            return new boolean[]{mainhand || offhand, offhand, mainhand && offhand};
        }
        return new boolean[]{false, false, false};
    }


}
