package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Function;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HumanoidModel<T extends LivingEntity> extends AgeableListModel<T> implements ArmedModel, HeadedModel {

    /**
     Empty player model with nothing but the animation logic.
    **/


    public final ModelPart head;
    /**
     * The Biped's Headwear. Used for the outer layer of player skins.
     */
    public final ModelPart hat;
    public final ModelPart body;
    /**
     * The Biped's Right Arm
     */
    public final ModelPart rightArm;
    /**
     * The Biped's Left Arm
     */
    public final ModelPart leftArm;
    /**
     * The Biped's Right Leg
     */
    public final ModelPart rightLeg;
    /**
     * The Biped's Left Leg
     */
    public final ModelPart leftLeg;
    public net.minecraft.client.model.HumanoidModel.ArmPose leftArmPose = net.minecraft.client.model.HumanoidModel.ArmPose.EMPTY;
    public net.minecraft.client.model.HumanoidModel.ArmPose rightArmPose = net.minecraft.client.model.HumanoidModel.ArmPose.EMPTY;

    public HumanoidModel(ModelPart root) {
        this(root, RenderType::entityCutoutNoCull);
    }

    public HumanoidModel(ModelPart root, Function<ResourceLocation, RenderType> renderType) {
        super(renderType, true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);
        this.head = root.getChild("head");
        this.hat = root.getChild("hat");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //Animation has to be written by hand
        // math is changed to the Math Class
        //q.anim_time is any thing that ticks, in this case ageInTicks
        //speed and angle can obviously be hard coded

        leftArm.xRot= Math.sin(ageInTicks * speed) * angle
        rightArm.xRot= Math.cos(ageInTicks * speed) * angle

    }
}
