package com.epxzzy.createsaburs.misc;

public class ColourConverter {

    // Convert decimal color to RGB
    public static int[] PortedDecimaltoRGB(int decimalColor) {
        int red = (decimalColor >> 16) & 0xFF;
        int green = (decimalColor >> 8) & 0xFF;
        int blue = decimalColor & 0xFF;
        return new int[]{red, green, blue};
    }

    public static int portedRGBtoDecimal(int[] reg) {
        int aa = (reg[0] << 16) | (reg[1] << 8) | reg[2];
        //createsaburs.LOGGER.warn("converted(RGB2DEC) colour:" + aa);
        return aa;
    }

    public static int[] HSLtoRGB(float h, float s, float l) {
        if (s == 100) {
            s-=1;
        }

        if (l == 100) {
            l-=1;
        }
        if (h == 360){
            h-=1;
        }


        //  Formula needs all values between 0 - 1.

        h = h % 360.0f;
        h /= 360f;
        s /= 100f;
        l /= 100f;

        float q = 0;

        if (l < 0.5)
            q = l * (1 + s);
        else
            q = (l + s) - (s * l);

        float p = 2 * l - q;

        int r = Math.round(Math.max(0, HueToRGB(p, q, h + (1.0f / 3.0f)) * 256));
        int g = Math.round(Math.max(0, HueToRGB(p, q, h) * 256));
        int b = Math.round(Math.max(0, HueToRGB(p, q, h - (1.0f / 3.0f)) * 256));

        int[] array = { r, g, b };
        return array;
    }

    private static float HueToRGB(float p, float q, float h) {
        if (h < 0)
            h += 1;

        if (h > 1)
            h -= 1;

        if (6 * h < 1) {
            return p + ((q - p) * 6 * h);
        }

        if (2 * h < 1) {
            return q;
        }

        if (3 * h < 2) {
            return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
        }

        return p;
    }


    public static void main(String[] args) {
        /* Example usage
        int decimalColor = 0xAABBCC;
        int[] rgb = decimalToRGB(decimalColor);
        System.out.println("RGB: " + rgb[0] + ", " + rgb[1] + ", " + rgb[2]);

        float h = 120.0f; // Hue
        float s = 0.5f;   // Saturation
        float l = 0.5f;   // Lightness
        int decimalColorFromHSL = hslToDecimal(h, s, l);
        System.out.println("Decimal from HSL: " + Integer.toHexString(decimalColorFromHSL).toUpperCase());

         */
    }
}
