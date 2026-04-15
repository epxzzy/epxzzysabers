package com.epxzzy.epxzzysabers.item;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.misc.KewlFightsOrchestrator;
import com.epxzzy.epxzzysabers.networking.SaberMessages;
import com.epxzzy.epxzzysabers.networking.packet.player.ClientboundPlayerFlourishPacket;
import com.epxzzy.epxzzysabers.networking.packet.saber.ServerboundSaberDeflectPacket;
import com.epxzzy.epxzzysabers.rendering.foundation.CustomRenderedItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.SimpleCustomRenderer;
import com.epxzzy.epxzzysabers.util.ColourConverter;
import com.epxzzy.epxzzysabers.misc.ProtosaberItemRenderer;
import com.epxzzy.epxzzysabers.sound.SaberSounds;
import com.epxzzy.epxzzysabers.util.LevelHelper;
import com.epxzzy.epxzzysabers.util.TagHelper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Protosaber extends Item {
    private ArrayListMultimap<Attribute, AttributeModifier> defaultModifiers;
    public final float PARRY_RANGE;
    public final int ATTACK_DAMAGE;
    public final int ATTACK_SPEED;
    public static int SOFT_PARRY = 25;
    public static CustomRenderedItemModelRenderer THE_BETTER_RENDERER;

    public static int BASE_COLOUR = 65280;
    public boolean isActive;

    public Protosaber(Properties pProperties, float pRANGE, int pDamage, int pSpeed) {
        super(pProperties);
        float attackDamage = (float) 5f;
        ArrayListMultimap<Attribute, AttributeModifier> builder = ArrayListMultimap.create();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) 0, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double) -1f, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder;
        this.PARRY_RANGE = pRANGE;
        this.ATTACK_DAMAGE = pDamage;
        this.ATTACK_SPEED = pSpeed;
    }

    public boolean readActivetag(ItemStack pStack) {
        CompoundTag temp = pStack.getOrCreateTag();
        boolean temp2 = temp.getBoolean("ActiveBoiii");
        //epxzzySabers.LOGGER.info("read nbt as " + temp2);
        return temp2;
    }

    public void writeActiveTag(ItemStack pStack, boolean bool) {
        CompoundTag tag = pStack.getOrCreateTag();
        CompoundTag tagsToApply = pStack.getOrCreateTag().copy();
        CompoundTag displayTag = pStack.getOrCreateTag().getCompound("display").copy();

        if (tag.contains("AttributeModifiers", 9)) {
            tag.remove("AttributeModifiers");
        }

        if (tag.getCompound("display").getInt("colour") != 0) {
            int flourishID = tag.getCompound("display").getInt("flourish");
            displayTag.putInt("colour", tag.getCompound("display").getInt("colour"));
            displayTag.putInt("flourish", flourishID);

        } else {
            displayTag.putInt("colour", BASE_COLOUR);
        }

        tagsToApply.putBoolean("ActiveBoiii", bool);
        tagsToApply.put("display", displayTag);
        tagsToApply.put("AttributeModifiers", new ListTag());

        ListTag listtag = tagsToApply.getList("AttributeModifiers", 10);

        CompoundTag baseAttackAttribute = (new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (bool ? ATTACK_DAMAGE : 1), AttributeModifier.Operation.ADDITION)).save();
        CompoundTag baseSpeedAttribute = (new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (bool ? ATTACK_SPEED : 2), AttributeModifier.Operation.ADDITION)).save();

        baseAttackAttribute.putString("AttributeName", BuiltInRegistries.ATTRIBUTE.getKey(Attributes.ATTACK_DAMAGE).toString());
        baseAttackAttribute.putString("Slot", EquipmentSlot.MAINHAND.getName());
        baseSpeedAttribute.putString("AttributeName", BuiltInRegistries.ATTRIBUTE.getKey(Attributes.ATTACK_SPEED).toString());
        baseSpeedAttribute.putString("Slot", EquipmentSlot.MAINHAND.getName());

        listtag.add(baseAttackAttribute);
        listtag.add(baseSpeedAttribute);

        pStack.setTag(tagsToApply);

        //epxzzySabers.LOGGER.info("wrote nbt as" + bool);
        //epxzzySabers.LOGGER.info(pStack.getOrCreateTag().getAllKeys().toString());
    }

    public void ToggleSaberCore(Level pLevel, Player pPlayer, ItemStack pStack) {
        CompoundTag nbeetea = pStack.getOrCreateTag();
        //if (!pLevel.isClientSide) {

        if (!readActivetag(pStack)) {

            //epxzzySabers.LOGGER.info("Saber is now Going Active");
            //nbeetea.putInt("CustomModelData", 1);
            writeActiveTag(pStack, true);

            isActive = true;
        } else {
            pPlayer.stopUsingItem();
            //epxzzySabers.LOGGER.info("Saber is now Turning Off");

            //nbeetea.putInt("CustomModelData", 0);
            writeActiveTag(pStack, false);
            isActive = false;
        }

        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                (readActivetag(pStack) ? SaberSounds.ACTIVATION.get() : SaberSounds.DEACTIVATION.get()), SoundSource.NEUTRAL, 0.5f, 1f
        );
    }

    public static void getFlourishTag(LivingEntity pEntity, ItemStack pStack) {
        if (!pEntity.level().isClientSide() && pEntity instanceof Player pPlayer) {
            int methodic = KewlFightsOrchestrator.DetermineParryAnimation((Player) pPlayer);
            //epxzzySabers.LOGGER.info("FLOURRRISHHH " + methodic);
            SaberMessages.fuckingAnnounce(new ClientboundPlayerFlourishPacket(pPlayer.getId(), methodic), pPlayer);
        }
    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        CompoundTag tag = pStack.getOrCreateTag();
        tag.putInt("equiper", pEntity.getId());
        tag.putBoolean("offhand", false);
        if (!(pLevel.isClientSide())) {
            //    epxzzySabers.LOGGER.info("curr slott "+ pSlotId);
            //pEntity.
            if (pEntity instanceof LivingEntity pLiving) {
                if (pLiving instanceof Player pPlayer) {
                    if (ItemStack.isSameItemSameTags(pPlayer.getInventory().offhand.get(0), pStack)) {
                        tag.putBoolean("offhand", true);
                        //epxzzySabers.LOGGER.info("offhanddd biatch");
                    } else {
                        tag.putBoolean("offhand", false);
                    }
                    pStack.setTag(tag);
                }
            }

        } else {
        }


    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (readActivetag(pPlayer.getItemInHand(pHand)) && !pLevel.isClientSide) {
            pPlayer.startUsingItem(pHand);
            //return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
        }
        if (pPlayer.isShiftKeyDown() && pHand == InteractionHand.MAIN_HAND) {
            ToggleSaberCore(pLevel, pPlayer, itemstack);
            pPlayer.stopUsingItem();
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
        }
        return InteractionResultHolder.fail(itemstack);
    }

    public static int getColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getOrCreateTagElement("display");
        if (compoundtag.getBoolean("gay")) {
            return ColourConverter.portedRGBtoDecimal(ColourConverter.rainbowColor((int) System.currentTimeMillis()));
        }

        if (compoundtag.getInt("colour") == 0) {
            //return 65280;
            return 0;
        }

        return Objects.requireNonNull(pStack.getTagElement("display")).getInt("colour");
    }

    public static boolean isGay(ItemStack pStack) {
        return Objects.requireNonNull(pStack.getTagElement("display")).getBoolean("gay");
    }

    @Override
    public boolean canBeHurtBy(DamageSource pDamageSource) {
        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        MutableComponent HintDetail = Component.literal("[SHIFT]")
                .withStyle(Screen.hasShiftDown() ? ChatFormatting.YELLOW: ChatFormatting.GOLD).append(Component.literal( " to show additional details").withStyle(ChatFormatting.GRAY));
        tooltip.add(HintDetail);

        if (Screen.hasShiftDown()) {
            MutableComponent ActiveDetail = Component.literal(readActivetag(stack) ? "Active" : "Inactive")
                    .withStyle(readActivetag(stack) ? ChatFormatting.WHITE : ChatFormatting.GRAY);
            tooltip.add(ActiveDetail);
            MutableComponent ColourDetail = Component.literal(ColourConverter.getHexString(getColor(stack)))
                    .withStyle(ChatFormatting.GRAY);
            tooltip.add(ColourDetail);
            MutableComponent AbilityDetail = Component.literal("SaberAbilities:\n")
                    .withStyle(ChatFormatting.WHITE);
            AbilityDetail.append(getAbilityTooltipDetail());
            tooltip.add(AbilityDetail);
        }
    }

    public MutableComponent getAbilityTooltipDetail(){
        MutableComponent detail = Component.literal("         None: this item does not react to saberability key nor does it posses any inherent special abilities\n");
        return detail;
    }

    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
        //return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers :
        //n super.getDefaultAttributeModifiers(pEquipmentSlot);
    }


    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        if (readActivetag(pStack)) return UseAnim.BLOCK;
        return UseAnim.NONE;
    }

    public int getUseDuration(@NotNull ItemStack pStack) {
        return 72000;
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }

    public static float getSaberParryRange(ItemStack pStack) {
        return ((Protosaber) pStack.getItem().asItem()).PARRY_RANGE;
        //return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if(!(entity instanceof Player pPlayer)) return false;
        if ((!pPlayer.level().isClientSide) && pPlayer.swingTime >= 3) {
            getFlourishTag(entity, stack);
        }

        if(entity.level().isClientSide){
            SaberMessages.sendToServer(new ServerboundSaberDeflectPacket());
            LevelHelper.AnimateDefelctionClient(stack, (Player) entity);
        }
        return false;
        //}
        //return false;
    }
    //else return false;
    //}


    @Override
    public Component getName(ItemStack pStack) {
        if(TagHelper.isActive(pStack)) {
            int[] RGB = ColourConverter.PortedDecimaltoRGB(getColor(pStack));
            int[] HSL = ColourConverter.RGBtoHSL(RGB[0], RGB[1], RGB[2]);
            HSL[2] = HSL[2] > 30 ? HSL[2]: 30 ;
            int L = (int) Mth.clamp((double) HSL[2] + (HSL[2]*0.25) * (float) (Math.sin((double) System.currentTimeMillis() / 250)), 0, 100);
            return super.getName(pStack)
                    .copy()
                    .withStyle(style ->
                            style.withColor(ColourConverter.portedRGBtoDecimal(ColourConverter.HSLtoRGB(HSL[0], HSL[1], L)))
                    );
        }
        return super.getName(pStack);
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        if (readActivetag(item)) {
            writeActiveTag(item, false);
            isActive = false;

            player.level().playSound((Player) null, player.getX(), player.getY(), player.getZ(),
                    SaberSounds.DEACTIVATION.get(), SoundSource.NEUTRAL, 0.5f, 1f
            );
        }

        return super.onDroppedByPlayer(item, player);
    }

    public boolean isValidRepairItem(@NotNull ItemStack pToRepair, @NotNull ItemStack pRepair) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_BETTER_RENDERER = new ProtosaberItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_BETTER_RENDERER));
    }
}
