package dev.epxzzy.epxzzysabers.content.sabers.SingleBladed;

import dev.epxzzy.epxzzysabers.content.sabers.Proto.Protosaber;
import dev.epxzzy.epxzzysabers.core.foundation.CustomRenderedSaberModelRenderer;

public class SingleBladeSaberItem extends Protosaber {
    public SingleBladeSaberItem(Properties properties) {
        super(properties);
    }

    @Override
    public CustomRenderedSaberModelRenderer getRenderer() {
        return new SingleBladedItemRenderer();
    }
}
