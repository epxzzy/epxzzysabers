package com.epxzzy.createsaburs.item;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.rendering.ProtosaberItemRenderer;
import com.epxzzy.createsaburs.rendering.poseHandlers.BladeStance;
import com.epxzzy.createsaburs.sound.ModSounds;
import com.epxzzy.createsaburs.utils.ModTags;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.platform.InputConstants;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
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
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Protosaber extends Item {
    private ArrayListMultimap<Attribute, AttributeModifier> defaultModifiers;
    private final int PARRY_RANGE;
    private final int ATTACK_DAMAGE;
    private final int ATTACK_SPEED;
    public static CustomRenderedItemModelRenderer THE_RENDURR;
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
        //createsaburs.LOGGER.info("read nbt as " + temp2);
        return temp2;
    }

    public void writeActiveTag(ItemStack pStack, boolean bool) {
        CompoundTag tag = pStack.getOrCreateTag();
        CompoundTag tagsToApply = pStack.getOrCreateTag().copy();
        CompoundTag displayTag = pStack.getOrCreateTag().getCompound("display").copy();

        if (tag.contains("AttributeModifiers", 9)) {
            tag.remove("AttributeModifiers");
        }

        if (tag.getCompound("display").getInt("color") != 0) {
            int flourishID = tag.getCompound("display").getInt("flourish");
            displayTag.putInt("color", tag.getCompound("display").getInt("color"));
            displayTag.putInt("flourish", flourishID);

        } else {
            displayTag.putInt("color", BASE_COLOUR);
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

        createsaburs.LOGGER.info("wrote nbt as" + bool);
        createsaburs.LOGGER.info(pStack.getOrCreateTag().getAllKeys().toString());
    }

    public void ToggleSaberCore(Level pLevel, Player pPlayer, ItemStack pStack) {
        CompoundTag nbeetea = pStack.getOrCreateTag();
        //if (!pLevel.isClientSide) {

            if (!readActivetag(pStack)) {

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
                    (readActivetag(pStack) ? ModSounds.ACTIVATION.get() : ModSounds.DEACTIVATION.get()), SoundSource.NEUTRAL, 0.5f, 1f
            );
        //}
    }

    public void addFlourishTag(LivingEntity pPlayer, ItemStack pStack) {
        if (!pPlayer.level().isClientSide()) {
            int flourish = (int) ((Math.random() * 3) + 1);

            pStack.getOrCreateTag().getCompound("display").putInt("flourish", flourish);
            //CompoundTag tagsToApply = new CompoundTag();
            //CompoundTag displayTag = new CompoundTag();

            //displayTag.putInt("flourish", flourish);

            //tagsToApply.put("display", displayTag);

            //pStack.setTag(tagsToApply);

            createsaburs.LOGGER.info("Flourish =" + flourish);
            createsaburs.LOGGER.info(pStack.getOrCreateTag().getAllKeys().toString());
        }
    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        CompoundTag tag = pStack.getOrCreateTag();
        tag.putInt("equiper", pEntity.getId());
        tag.putBoolean("offhand", false);
        if(pEntity instanceof LivingEntity pLiving && !(pLevel.isClientSide())){
        //    createsaburs.LOGGER.info("curr slott "+ pSlotId);
            //pEntity.
            if(pLiving instanceof Player pPlayer) {
                if (ItemStack.isSameItemSameTags(pPlayer.getInventory().offhand.get(0), pStack)) {
                    tag.putBoolean("offhand",true);
                    //createsaburs.LOGGER.info("offhanddd biatch");
                }
                else {
                    tag.putBoolean("offhand", false);
                }
            }
        }
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
        //createsaburs.LOGGER.info("stopped blocking, changing custom model data");
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

    public boolean hasCustomColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99);
    }

    public static int getColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getOrCreateTagElement("display");
        if (compoundtag.getInt("color") == 0) {
            //setColor(pStack, 65280);
            return 65280;
        }
        return Objects.requireNonNull(pStack.getTagElement("display")).getInt("color");
    }

    public void clearColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement("display");
        if (compoundtag != null && compoundtag.contains("color")) {
            compoundtag.remove("color");
        }
        pStack.setTag(compoundtag);

    }

    public static void setColor(ItemStack pStack, int pColor) {
        CompoundTag asdf = pStack.getOrCreateTagElement("display");
        asdf.putInt("color", pColor);
        pStack.setTag(asdf);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        MutableComponent ActiveDetail = Component.literal(readActivetag(stack) ? "On" : "Off")
                .withStyle(readActivetag(stack) ? ChatFormatting.WHITE : ChatFormatting.GRAY);
        tooltip.add(ActiveDetail);
        if (Screen.hasControlDown()) {
            tooltip.add(Component.literal(" hidden text displayed only when ctrl key is being held down"));
        }
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        if (toolAction == createsaburs.SABER_BLOCK&&this.readActivetag(stack)) return true;
        return toolAction == createsaburs.SABER_SWING;

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

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        //addFlourishTag(entity, stack);
            Vec3 asdf = entity.blockPosition().getCenter();
            List<Entity> notThat = entity.level().getEntities(null, new AABB(
                    asdf.x - PARRY_RANGE,
                    asdf.y - PARRY_RANGE,
                    asdf.z - PARRY_RANGE,
                    asdf.x + PARRY_RANGE,
                    asdf.y + PARRY_RANGE,
                    asdf.z + PARRY_RANGE)
            );
            notThat.removeIf(new Predicate<Entity>() {
                @Override
                public boolean test(Entity entity) {
                    if (entity instanceof Player) {
                        //createsaburs.LOGGER.warn("PLAYUR???? NAHHHHH!!");
                        return true;
                    }
                    return false;
                }
            });
            //if (!entity.level().isClientSide()) {
            if (!notThat.isEmpty() && stack.getOrCreateTag().getBoolean("ActiveBoiii")) {
                for (Entity entity1 : notThat) {
                    Vec3 vec32 = entity1.position();
                    double speee = entity1.getDeltaMovement().length();
                    Vec3 vec3 = entity.getViewVector(1.0F);
                    Vec3 vec31 = vec32.vectorTo(entity.position()).normalize();
                    vec31 = new Vec3(vec31.x, vec31.y, vec31.z);
                    if (vec31.dot(vec3) < 0.4D && speee > -2.0D) {
                        //createsaburs.LOGGER.warn("oh look what do we have here?");
                        //createsaburs.LOGGER.warn("is on ground: " + entity1.onGround() + " and is decending? " + entity1.isDescending());
                        //createsaburs.LOGGER.warn("avrg speed is " + (speee));

                        //createsaburs.LOGGER.warn("GET DEFELECTED IDIOT");


                        if (Projectile.class.isAssignableFrom(entity1.getClass())) {
                            //createsaburs.LOGGER.warn("its a projectile???");

                            if (AbstractArrow.class.isAssignableFrom(entity1.getClass()) && !((AbstractArrow) entity1).inGround ) {
                                createsaburs.LOGGER.warn("deflected an ordinary arrow");
                                ((AbstractArrow) entity1).shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 1.0F);
                                entity.level().playSound((Player) null, entity.blockPosition(), ModSounds.CLASH.get(), SoundSource.PLAYERS, 0.5f, 1f);

                                continue;
                            }


                            //Vec3 pos = entity.getLookAngle();
                            //entity1.setDeltaMovement(pos);
                            //entity1.setDeltaMovement(vec3);
                            if (AbstractHurtingProjectile.class.isAssignableFrom(entity1.getClass())) {
                                createsaburs.LOGGER.warn("deflected a projectile");

                                ((AbstractHurtingProjectile) entity1).xPower = vec3.x * 0.1D;
                                ((AbstractHurtingProjectile) entity1).yPower = vec3.y * 0.1D;
                                ((AbstractHurtingProjectile) entity1).zPower = vec3.z * 0.1D;

                                Vec3 pos = entity.getLookAngle();
                                entity1.setDeltaMovement(pos);
                                entity1.setDeltaMovement(vec3);
                                ((AbstractHurtingProjectile) entity1).setOwner(entity);

                                entity.level().playSound((Player) null, entity.blockPosition(), ModSounds.CLASH.get(), SoundSource.PLAYERS, 0.5f, 1f);
                            }


                            //entity1.setDeltaMovement(entity1.getDeltaMovement().scale(-10));
                            //entity1.setDeltaMovement(entity1.getDeltaMovement().reverse());
                            //entity1.moveTo(entity1.getDeltaMovement());
                            //entity1.setXRot(entity.getXRot());
                            //entity1.setYRot(entity.getYRot());
                        }
                    }
                }
                //createsaburs.LOGGER.warn("wait was that all of them? dam thats sad");
            } else if (stack.getOrCreateTag().getBoolean("ActiveBoiii")) {
                entity.level().playSound((Player) null, entity.blockPosition(), ModSounds.SWING.get(), SoundSource.PLAYERS, 0.1F, 0.8F + entity.level().random.nextFloat() * 0.4F);

            }
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

    public static boolean checkForSaberBlock(Entity Entityy) {
        if (Entityy instanceof LivingEntity)
            return ((LivingEntity) Entityy).getMainHandItem().is(ModTags.Items.CREATE_DUAL_BLADED) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii") && ((LivingEntity) Entityy).isUsingItem();
        return false;
    }
    public static BladeStance getStance(Entity Entityy) {
        int tagid = 1;
        if (Entityy instanceof LivingEntity){
            tagid = ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getCompound("display").getInt("stance");
        }
        BladeStance returnee;
        switch (tagid) {
            case 2 -> returnee = BladeStance.FORM2;
            case 3 -> returnee = BladeStance.FORM3;
            case 4 -> returnee = BladeStance.FORM4;
            case 5 -> returnee = BladeStance.FORM5;
            case 6 -> returnee = BladeStance.FORM6;
            case 7 -> returnee = BladeStance.FORM7;


            default -> returnee = BladeStance.FORM1;
        }
        return returnee;
    }


    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand) {
        if (Entityy instanceof LivingEntity) {
            if (Mainhand)
                return ((LivingEntity) Entityy).getMainHandItem().is(ModTags.Items.CREATE_DUAL_BLADED) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii");
            return ((LivingEntity) Entityy).getOffhandItem().is(ModTags.Items.CREATE_DUAL_BLADED) && ((LivingEntity) Entityy).getOffhandItem().getOrCreateTag().getBoolean("ActiveBoiii");
        }
        return false;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_RENDURR = new ProtosaberItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_RENDURR));
    }
}
