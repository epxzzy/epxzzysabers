package com.epxzzy.createsaburs.item.saburtypes;

import com.epxzzy.createsaburs.item.protosaber;
import com.epxzzy.createsaburs.rendering.SingleBladedItemRenderer;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class SingleBladed extends protosaber {

    public SingleBladed(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, 3 , 14, 2);
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_RENDURR = new SingleBladedItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_RENDURR));
    }

}
