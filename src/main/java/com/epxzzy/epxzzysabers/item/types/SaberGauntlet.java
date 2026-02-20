package com.epxzzy.epxzzysabers.item.types;

import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.rendering.item.SaberGauntletItemRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.SimpleCustomRenderer;
import com.epxzzy.epxzzysabers.util.AnimationTickHolder;
import com.epxzzy.epxzzysabers.util.LevelHelper;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber
public class SaberGauntlet extends Protosaber {
    int supercharredTime = 0;
    int CHARGEDDAMAGE = 8;

    //0 == natty/normal, 300 == goddamn this thing is hot
    public static final AttributeModifier singleRangeAttributeModifier =
            new AttributeModifier(UUID.fromString("7f7dbdb2-0d0d-458a-aa40-ac7633691f66"), "Range modifier", 3,
                    AttributeModifier.Operation.ADDITION);

    private static final Supplier<Multimap<Attribute, AttributeModifier>> rangeModifier = Suppliers.memoize(() ->
            ImmutableMultimap.of(ForgeMod.BLOCK_REACH.get(), singleRangeAttributeModifier));


    public static final String EXTENDO_MARKER = "gauntletMarker";

    public SaberGauntlet(Properties pProperties, float pRANGE, int pDamage, int pSpeed) {
        super(pProperties, pRANGE, pDamage, pSpeed);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_BETTER_RENDERER = new SaberGauntletItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_BETTER_RENDERER));
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(pLevel.isClientSide()) return;

        if (supercharredTime > 1) {
            supercharredTime--;
        }

        if (supercharredTime == 1) {
            pStack.getOrCreateTag().putBoolean("ChargedBoiii", false);
            pLevel.playSound(null, pEntity.blockPosition(), SoundEvents.AXE_STRIP, SoundSource.PLAYERS);
            supercharredTime--;

            CompoundTag tagsToApply = pStack.getOrCreateTag().copy();
            tagsToApply.put("AttributeModifiers", new ListTag());
            ListTag listtag = tagsToApply.getList("AttributeModifiers", 10);


            CompoundTag baseAttackAttribute = (new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", ATTACK_DAMAGE, AttributeModifier.Operation.ADDITION)).save();

            baseAttackAttribute.putString("AttributeName", BuiltInRegistries.ATTRIBUTE.getKey(Attributes.ATTACK_DAMAGE).toString());
            baseAttackAttribute.putString("Slot", EquipmentSlot.MAINHAND.getName());

            listtag.add(baseAttackAttribute);
            pStack.setTag(tagsToApply);

        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if(pLevel.isClientSide()) return;
        if (pRemainingUseDuration == 1) {
            pStack.getOrCreateTag().putBoolean("ChargedBoiii", true);
            supercharredTime = 160;

            CompoundTag tagsToApply = pStack.getOrCreateTag().copy();
            tagsToApply.put("AttributeModifiers", new ListTag());
            ListTag listtag = tagsToApply.getList("AttributeModifiers", 10);


            CompoundTag baseAttackAttribute = (new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", ATTACK_DAMAGE + CHARGEDDAMAGE, AttributeModifier.Operation.ADDITION)).save();

            baseAttackAttribute.putString("AttributeName", BuiltInRegistries.ATTRIBUTE.getKey(Attributes.ATTACK_DAMAGE).toString());
            baseAttackAttribute.putString("Slot", EquipmentSlot.MAINHAND.getName());

            listtag.add(baseAttackAttribute);
            pStack.setTag(tagsToApply);

            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundEvents.AXE_SCRAPE, SoundSource.PLAYERS);
            pLivingEntity.stopUsingItem();
        }
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        if(pStack.getOrCreateTag().getBoolean("ActiveBoiii")) return 40;
        return super.getUseDuration(pStack);
    }


    @SubscribeEvent
    public static void holdingChargedGauntletIncreasesRange(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        CompoundTag persistentData = player.getPersistentData();
        boolean inOff = checkForSaberCharge((Entity) player, false);
        boolean inMain = checkForSaberCharge((Entity) player, true);

        boolean holdingExtendo = inOff ^ inMain;
        boolean wasHoldingExtendo = persistentData.contains(EXTENDO_MARKER);

        if (holdingExtendo != wasHoldingExtendo) {
            if (!holdingExtendo) {
                player.getAttributes()
                        .removeAttributeModifiers(rangeModifier.get());
                persistentData.remove(EXTENDO_MARKER);
            } else {
                player.getAttributes()
                        .addTransientAttributeModifiers(rangeModifier.get());
                persistentData.putBoolean(EXTENDO_MARKER, true);
            }
        }
    }

    @SubscribeEvent
    public static void addReachToJoiningPlayersHoldingExtendo(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        CompoundTag persistentData = player.getPersistentData();

        if (persistentData.contains(EXTENDO_MARKER))
            player.getAttributes()
                    .addTransientAttributeModifiers(rangeModifier.get());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void dontMissEntitiesWhenYouHaveHighReachDistance(InputEvent.InteractionKeyMappingTriggered event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (mc.level == null || player == null)
            return;
        if (!checkForSaberEquipment(player, true))
            return;
        if (mc.hitResult instanceof BlockHitResult && mc.hitResult.getType() != HitResult.Type.MISS)
            return;

        // Modified version of GameRenderer#getMouseOver
        double d0 = player.getAttribute(ForgeMod.BLOCK_REACH.get())
                .getValue();
        if (!player.isCreative())
            d0 -= 0.5f;
        Vec3 Vector3d = player.getEyePosition(AnimationTickHolder.getPartialTicks());
        Vec3 Vector3d1 = player.getViewVector(1.0F);
        Vec3 Vector3d2 = Vector3d.add(Vector3d1.x * d0, Vector3d1.y * d0, Vector3d1.z * d0);
        AABB AABB = player.getBoundingBox()
                .expandTowards(Vector3d1.scale(d0))
                .inflate(1.0D, 1.0D, 1.0D);
        EntityHitResult entityraytraceresult =
                ProjectileUtil.getEntityHitResult(player, Vector3d, Vector3d2, AABB, (e) -> {
                    return !e.isSpectator() && e.isPickable();
                }, d0 * d0);
        if (entityraytraceresult != null) {
            Entity entity1 = entityraytraceresult.getEntity();
            Vec3 Vector3d3 = entityraytraceresult.getLocation();
            double d2 = Vector3d.distanceToSqr(Vector3d3);
            if (d2 < d0 * d0 || mc.hitResult == null || mc.hitResult.getType() == HitResult.Type.MISS) {
                mc.hitResult = entityraytraceresult;
                if (entity1 instanceof LivingEntity || entity1 instanceof ItemFrame)
                    mc.crosshairPickEntity = entity1;
            }
        }
    }

    public static boolean checkForSaberCharge(Entity Entityy, boolean Mainhand){
        if(Entityy instanceof LivingEntity) {
            if(Mainhand) return ((LivingEntity) Entityy).getMainHandItem().is(SaberItems.SABER_GAUNTLET.get()) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ChargedBoiii");
            return ((LivingEntity) Entityy).getOffhandItem().is(SaberItems.SABER_GAUNTLET.get()) && ((LivingEntity) Entityy).getOffhandItem().getOrCreateTag().getBoolean("ChargedBoiii");
        }
        return false;
    }

    public static boolean checkForSaberBlock(Entity Entityy) {
        return LevelHelper.EntityBlockingWithActiveItem(Entityy, SaberItems.SABER_GAUNTLET.get());
    }

    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand) {
        return LevelHelper.EntityEquippedActiveItem(Entityy, Mainhand, SaberItems.SABER_GAUNTLET.get());
    }

}
