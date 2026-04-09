package com.epxzzy.epxzzysabers.item.types;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.rendering.item.RotarySaberItemRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.SimpleCustomRenderer;
import com.epxzzy.epxzzysabers.sound.SaberSounds;
import com.epxzzy.epxzzysabers.util.ConfigHolder;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RotarySaber extends Protosaber {
    public RotarySaber(Properties pProperties, float pRANGE, int pDamage, int pSpeed) {
        super(pProperties, pRANGE, pDamage, pSpeed);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_BETTER_RENDERER = new RotarySaberItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_BETTER_RENDERER));
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pLivingEntity instanceof ServerPlayer pPlayer) {
            if (!(pPlayer.gameMode.isSurvival())) {
                pPlayer.displayClientMessage(Component.literal("flight is only available in survival mode"), true);
            }

        }

        if (pRemainingUseDuration % 2 == 0 && pStack.getOrCreateTag().getBoolean("FlyBoiii")) {
            if (pLivingEntity instanceof Player pPlayer && (pPlayer.getAbilities().flying)) {
                pLivingEntity.level().playSound((Player) null,
                        pLivingEntity.blockPosition(), SaberSounds.SWING_RAPID.get(),
                        SoundSource.PLAYERS,
                        0.1F,
                        1F
                );
            }
        }
        //super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof ServerPlayer pPlayer && !(pLevel.isClientSide())) {
            PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) pPlayer;

            //forcefully stop flight and put on cooldown
            if (pPlayer.isCreative()) {
                MixinPlayer.setFlyCooldown(ConfigHolder.ROTARY_FLIGHT_COOLDOWN);
                return;
            }

            //flight cooldown tick
            if (!(pPlayer.getAbilities().flying)&&!pPlayer.isCreative()) {
                if (MixinPlayer.getFlyCooldown() >= 1) {
                    MixinPlayer.setFlyCooldown(MixinPlayer.getFlyCooldown() - 1);

                    if (MixinPlayer.getFlyCooldown() == 0) {
                        epxzzySabers.LOGGER.info("you can now fly");
                    }
                }
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public boolean isInAir(Player pPlayer) {
        BlockPos pos = pPlayer.blockPosition();
        Level level = pPlayer.level();
        return
                level.getBlockState(pos.below()).isAir() &&
                        level.getBlockState(pos).isAir() &&
                        level.getBlockState(pos.above()).isAir();
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        if(pStack.getOrCreateTag().getBoolean("FlyBoiii")) return ConfigHolder.ROTARY_FLIGHT_DURATION;
        return super.getUseDuration(pStack);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        boolean Justified = !pPlayer.isCreative() ? pHand != InteractionHand.OFF_HAND : false;

        if (readActivetag(pPlayer.getItemInHand(pHand)) && (!pLevel.isClientSide && Justified)) {
            PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) pPlayer;
            if ((pPlayer.xRotO < -35 || this.isInAir(pPlayer))) {
                if (MixinPlayer.getFlyCooldown() == 0){
                    Vec3 bbc = pPlayer.position();
                    pPlayer.teleportTo(bbc.x, bbc.y + 0.5, bbc.z);

                    pPlayer.getAbilities().flying = true;
                    pPlayer.onUpdateAbilities();


                    CompoundTag tug = itemstack.getOrCreateTag().copy();
                    tug.putBoolean("FlyBoiii", true);
                    itemstack.setTag(tug);
                    //this.flyCooldown = 40;
                    epxzzySabers.LOGGER.info("flying activated");
                }

                if (MixinPlayer.getFlyCooldown() >= 1) {
                    epxzzySabers.LOGGER.info(
                            "you cant seem to fly, flightcooldown: " +
                                    MixinPlayer.getFlyCooldown()
                                //+ " and flightduration: " +
                                //MixinPlayer.getFlightDuration()
                    );
                }
            }
            pPlayer.startUsingItem(pHand);
        }

        if (pPlayer.isShiftKeyDown() && pHand == InteractionHand.MAIN_HAND) {
            super.ToggleSaberCore(pLevel, pPlayer, itemstack);
            pPlayer.stopUsingItem();
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
        }

        return super.use(pLevel, pPlayer, pHand);
    }

    @Override
    public void onStopUsing(ItemStack pStack, LivingEntity pLivingEntity, int count) {
        CompoundTag nbeetea = pStack.getOrCreateTag();
        nbeetea.putBoolean("BlockBoiii", false);

        nbeetea.putBoolean("FlyBoiii", false);
        if (pLivingEntity instanceof Player pPlayer && pPlayer.getAbilities().flying && !pPlayer.level().isClientSide()) {
            PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) pPlayer;
            pPlayer.getAbilities().flying = false;
            pPlayer.onUpdateAbilities();
            MixinPlayer.setFlyCooldown(ConfigHolder.ROTARY_FLIGHT_COOLDOWN);
            epxzzySabers.LOGGER.info("flying deactivated");
        }

        pStack.setTag(nbeetea);
        super.onStopUsing(pStack, pLivingEntity, count);
    }


    public static boolean checkForSaberFly(Entity Entityy) {
        if (Entityy instanceof Player) {
            return (((Player) Entityy).getMainHandItem().is(SaberItems.ROTARY_SABER.get()) && ((Player) Entityy).getMainHandItem().getOrCreateTag().getBoolean("FlyBoiii"));
            //return LevelHelper.EntityBlockingWithActiveItem(Entityy, SaberItems.ROTARY_SABER.get())&&((Player) Entityy).getAbilities().flying;
        }
        return false;
    }

    public static boolean checkForSaberCooldown(Entity Entityy) {
        if (Entityy instanceof Player pPlayer) {
            PlayerHelperLmao MixinPlayer = (PlayerHelperLmao) pPlayer;
            //MixinPlayer.getFlyCooldown() > 30;
            return MixinPlayer.getFlyCooldown() > 30;
        }
        return false;
    }

}
