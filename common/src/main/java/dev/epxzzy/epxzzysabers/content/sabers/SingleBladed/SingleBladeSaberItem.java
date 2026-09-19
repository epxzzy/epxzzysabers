package dev.epxzzy.epxzzysabers.content.sabers.SingleBladed;

import dev.epxzzy.epxzzysabers.Constants;
import dev.epxzzy.epxzzysabers.content.sabers.Proto.Protosaber;
import dev.epxzzy.epxzzysabers.core.foundation.CustomRenderedSaberModelRenderer;
import dev.epxzzy.epxzzysabers.core.util.colourUtils;

public class SingleBladeSaberItem extends Protosaber {
    public SingleBladeSaberItem(Properties properties) {
        super(properties);
    }

    @Override
    public CustomRenderedSaberModelRenderer getRenderer() {
        return new SingleBladedItemRenderer();
    }

    @Override
    public int getColour() {
        Constants.LOG.info("called");
        return colourUtils.portedRGBtoDecimal(colourUtils.rainbowColor((int) System.currentTimeMillis()));
    }
}
