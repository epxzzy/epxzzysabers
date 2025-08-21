package com.epxzzy.epxzzysabers.item;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.misc.KewlFightsOrchestrator;
import com.epxzzy.epxzzysabers.networking.ModMessages;
import com.epxzzy.epxzzysabers.networking.packet.ServerboundSaberDeflectPacket;
import com.epxzzy.epxzzysabers.rendering.foundation.SimpleCustomRenderer;
import com.epxzzy.epxzzysabers.utils.ColourConverter;
import com.epxzzy.epxzzysabers.rendering.ProtosaberItemRenderer;
import com.epxzzy.epxzzysabers.rendering.playerposerenderers.BladeStance;
import com.epxzzy.epxzzysabers.sound.ModSounds;
import com.epxzzy.epxzzysabers.utils.LevelHelper;
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
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Protosaber extends Item {
    private ArrayListMultimap<Attribute, AttributeModifier> defaultModifiers;
    public final int PARRY_RANGE;
    public final int ATTACK_DAMAGE;
    public final int ATTACK_SPEED;
    //public static com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer THE_RENDURR;
    public static com.epxzzy.epxzzysabers.rendering.foundation.CustomRenderedItemModelRenderer THE_BETTER_RENDERER;

    public static int BASE_COLOUR = 65280;
    public boolean isActive;

    public Protosaber(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
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

            nbeetea.putInt("CustomModelData", 0);
            writeActiveTag(pStack, false);
            isActive = false;
        }

        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                (readActivetag(pStack) ? ModSounds.ACTIVATION.get() : ModSounds.DEACTIVATION.get()), SoundSource.NEUTRAL, 0.5f, 1f
        );
        //}
    }

    public void addFlourishTag(LivingEntity pPlayer, ItemStack pStack) {
        if (!pPlayer.level().isClientSide() && pPlayer instanceof Player) {
            int random = (int) ((Math.random() * 3) + 1);

            int methodic = KewlFightsOrchestrator.DetermineParryAnimation((Player) pPlayer);

            pStack.getOrCreateTag().getCompound("display").putInt("flourish", methodic);
        }
    }

    public static void removeFlourishTag(Entity pEntity, ItemStack pStack) {
        if (pEntity instanceof Player player && !player.swinging) {
            if (!pEntity.level().isClientSide()) {
                pStack.getOrCreateTag().getCompound("display").remove("flourish");
            }
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

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        //epxzzySabers.LOGGER.info("stopped blocking, changing custom model data");
        CompoundTag nbeetea = pStack.getOrCreateTag();
        nbeetea.putBoolean("BlockBoiii", false);
        pStack.setTag(nbeetea);
    }

    @Override
    public void onStopUsing(ItemStack pStack, LivingEntity entity, int count) {
        CompoundTag nbeetea = pStack.getOrCreateTag();
        nbeetea.putBoolean("BlockBoiii", false);
        pStack.setTag(nbeetea);
        super.onStopUsing(pStack, entity, count);
    }

    public static int getColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getOrCreateTagElement("display");
        if (compoundtag.getBoolean("gay")) {
            return ColourConverter.portedRGBtoDecimal(ColourConverter.rainbowColor((int) System.currentTimeMillis() * 2));
        }

        if (compoundtag.getInt("colour") == 0) {
            //setColor(pStack, 65280);
            return 65280;
        }

        return Objects.requireNonNull(pStack.getTagElement("display")).getInt("colour");
    }

    public static boolean isGay(ItemStack pStack) {
        return Objects.requireNonNull(pStack.getTagElement("display")).getBoolean("gay");
    }

    public void clearColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement("display");
        if (compoundtag != null && compoundtag.contains("colour")) {
            compoundtag.remove("colour");
        }
        pStack.setTag(compoundtag);

    }

    public static void setColor(ItemStack pStack, int pColor) {
        CompoundTag asdf = pStack.getOrCreateTagElement("display");
        asdf.putInt("colour", pColor);
        pStack.setTag(asdf);
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

        MutableComponent ActiveDetail = Component.literal(readActivetag(stack) ? "Active" : "Inactive")
                .withStyle(readActivetag(stack) ? ChatFormatting.WHITE : ChatFormatting.GRAY);
        tooltip.add(ActiveDetail);
        MutableComponent ColourDetail = Component.literal(ColourConverter.getHexString(getColor(stack)))
                .withStyle(ChatFormatting.GRAY);
        tooltip.add(ColourDetail);

        if (Screen.hasControlDown()) {
            tooltip.add(Component.literal("this item will not do anything when saberability is key down"));
        }
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        if (toolAction == epxzzySabers.SABER_BLOCK && this.readActivetag(stack)) return true;
        return toolAction == epxzzySabers.SABER_SWING;

        //return net.minecraftforge.common.ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
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

    public static int getSaberParryRange(ItemStack pStack) {
        return ((Protosaber) pStack.getItem().asItem()).PARRY_RANGE;
        //return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity.swingTime >= 3) {
            addFlourishTag(entity, stack);
        }
        ModMessages.sendToServer(new ServerboundSaberDeflectPacket());
        LevelHelper.AnimateDefelctionClient(stack, (Player) entity);
        return false;
        //}
        //return false;
    }
    //else return false;
    //}

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
                    ModSounds.DEACTIVATION.get(), SoundSource.NEUTRAL, 0.5f, 1f
            );
        }

        return super.onDroppedByPlayer(item, player);
    }

    public boolean isValidRepairItem(@NotNull ItemStack pToRepair, @NotNull ItemStack pRepair) {
        return false;
    }

    public static BladeStance getStance(Entity Entityy) {
        int tagid = 0;
        if (Entityy instanceof LivingEntity) {
            tagid = ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getCompound("display").getInt("stance");
        }
        BladeStance returnee;
        switch (tagid) {
            case 1 -> returnee = BladeStance.FORM1;
            case 2 -> returnee = BladeStance.FORM2;
            case 3 -> returnee = BladeStance.FORM3;
            case 4 -> returnee = BladeStance.FORM4;
            case 5 -> returnee = BladeStance.FORM5;
            case 6 -> returnee = BladeStance.FORM6;
            case 7 -> returnee = BladeStance.FORM7;

            default -> returnee = BladeStance.FORM0;
        }
        return returnee;
    }

    public static boolean checkForSaberBlock(Entity Entityy) {
        return LevelHelper.EntityBlockingWithActiveItem(Entityy, ModItems.Protosaber.get());
    }


    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand) {
        return LevelHelper.EntityEquippedActiveItem(Entityy, Mainhand, ModItems.Protosaber.get());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_BETTER_RENDERER = new ProtosaberItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_BETTER_RENDERER));
    }
}
