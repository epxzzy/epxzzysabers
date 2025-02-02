package com.epxzzy.createsaburs.item;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.rendering.ProtosaberItemRenderer;
import com.epxzzy.createsaburs.sound.ModSounds;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.platform.InputConstants;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class protosaber extends Item {
    public static final int maxStackSize = 1;
    public static final int EFFECTIVE_BLOCK_DELAY = 5;
    private ArrayListMultimap<Attribute, AttributeModifier> defaultModifiers;
    public static final float MINIMUM_DURABILITY_DAMAGE = 3.0F;

    public static ProtosaberItemRenderer THE_RENDURR;
    public boolean isActive;

    public protosaber(Properties pProperties) {
        super(pProperties);
        float attackDamage = (float) 5f;

        ArrayListMultimap<Attribute, AttributeModifier> builder = ArrayListMultimap.create();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) 0, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double) -1f, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder;


    }

    public boolean readtag(ItemStack pStack) {
        CompoundTag temp = pStack.getOrCreateTag();
        boolean temp2 = temp.getBoolean("ActiveBoiii");
        //createsaburs.LOGGER.info("read nbt as " + temp2);
        return temp2;
    }

    public void writeActiveTag(ItemStack pStack, boolean bool) {
        CompoundTag tag = pStack.getOrCreateTag();
        CompoundTag tagsToApply = new CompoundTag();
        CompoundTag displayTag = new CompoundTag();

        if (tag.contains("AttributeModifiers", 9)) {
            tag.remove("AttributeModifiers");
        }
        tagsToApply.putBoolean("ActiveBoiii", bool);
        displayTag.putInt("color", tag.getCompound("display").getInt("color"));
        tagsToApply.put("display", displayTag);
        tagsToApply.put("AttributeModifiers", new ListTag());
        ListTag listtag = tagsToApply.getList("AttributeModifiers", 10);

        CompoundTag baseAttackAttribute= (new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (bool?18:1), AttributeModifier.Operation.ADDITION)).save();
        CompoundTag baseSpeedAttribute= (new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (bool?1:2), AttributeModifier.Operation.ADDITION)).save();

        baseAttackAttribute.putString("AttributeName", BuiltInRegistries.ATTRIBUTE.getKey(Attributes.ATTACK_DAMAGE).toString());
        baseAttackAttribute.putString("Slot", EquipmentSlot.MAINHAND.getName());
        baseSpeedAttribute.putString("AttributeName", BuiltInRegistries.ATTRIBUTE.getKey(Attributes.ATTACK_SPEED).toString());
        baseSpeedAttribute.putString("Slot", EquipmentSlot.MAINHAND.getName());


        listtag.add(baseAttackAttribute);
        listtag.add(baseSpeedAttribute);

        pStack.setTag(tagsToApply);

        createsaburs.LOGGER.info("wrote nbt as" + bool);
        createsaburs.LOGGER.info(pStack.getOrCreateTag().getAllKeys().toString());
    }

    public void ToggleSaberCore(Level pLevel, Player pPlayer, ItemStack pStack) {
        CompoundTag nbeetea = pStack.getOrCreateTag();
        if (!pLevel.isClientSide) {

            if (!readtag(pStack)) {

                createsaburs.LOGGER.info("Saber is now Going Active");
                //nbeetea.putInt("CustomModelData", 1);
                writeActiveTag(pStack, true);

                isActive = true;
            } else {
                pPlayer.stopUsingItem();
                createsaburs.LOGGER.info("Saber is now Turning Off");

                //nbeetea.putInt("CustomModelData", 0);
                writeActiveTag(pStack, false);
                isActive = false;
            }

            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                    (readtag(pStack)? ModSounds.ACTIVATION.get() : ModSounds.DEACTIVATION.get()), SoundSource.NEUTRAL, 0.5f, 1f
            );
        }
    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

    }

    public boolean isPlayerClientPressing(int pInputConstraint, Level pLevel) {
        if (pLevel.isClientSide) {
            long winhandl = Minecraft.getInstance().getWindow().getWindow();
            return InputConstants.isKeyDown(winhandl, pInputConstraint) || InputConstants.isKeyDown(winhandl, pInputConstraint);
        } else {
            return false;
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (readtag(pPlayer.getItemInHand(pHand)) && !pLevel.isClientSide) {
            CompoundTag nbeetea = itemstack.getOrCreateTag();
            nbeetea.putBoolean("BlockBoiii", true);
            itemstack.setTag(nbeetea);
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

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        //createsaburs.LOGGER.info("stopped blocking, changing custom model data");
        CompoundTag nbeetea = pStack.getOrCreateTag();
        nbeetea.putBoolean("BlockBoiii", false);
        pStack.setTag(nbeetea);
    }


    public boolean hasCustomColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99);
    }

    public static int getColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement("display");
        if(compoundtag == null){
            setColor(pStack, 43008);
            return Objects.requireNonNull(pStack.getTagElement("display")).getInt("color");
        }
        return Objects.requireNonNull(pStack.getTagElement("display")).getInt("color");
    }

    public void clearColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement("display");
        if (compoundtag != null && compoundtag.contains("color")) {
            compoundtag.remove("color");
        }

    }

    public static void setColor(ItemStack pStack, int pColor) {
        pStack.getOrCreateTagElement("display").putInt("color", pColor);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        MutableComponent ActiveDetail = Component.literal(readtag(stack) ? "On" : "Off")
                .withStyle(readtag(stack) ? ChatFormatting.WHITE : ChatFormatting.GRAY);
        tooltip.add(ActiveDetail);
        if(Screen.hasControlDown()){
            tooltip.add(Component.literal(" hidden text displayed only when ctrl key is being held down"));
        }
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        if (toolAction == createsaburs.SABER_SWING) return true;
        return toolAction == createsaburs.SABER_BLOCK;

        //return net.minecraftforge.common.ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
    }

    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND? this.defaultModifiers:super.getDefaultAttributeModifiers(pEquipmentSlot);
        //return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers :
        //n super.getDefaultAttributeModifiers(pEquipmentSlot);
    }


    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        if (readtag(pStack)) return UseAnim.BLOCK;
        else return UseAnim.NONE;
    }

    public int getUseDuration(@NotNull ItemStack pStack) {
        return 72000;
    }

    public boolean isActive(CompoundTag tagg) {
        if (tagg != null) return tagg.getBoolean("ActiveBoiii");
        return isActive;
    }

    public boolean isValidRepairItem(@NotNull ItemStack pToRepair, @NotNull ItemStack pRepair) {
        return false;
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_RENDURR = new ProtosaberItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_RENDURR));
    }
}
