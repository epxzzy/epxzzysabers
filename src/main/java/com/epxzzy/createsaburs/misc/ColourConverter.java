package com.epxzzy.createsaburs.misc;

import com.epxzzy.createsaburs.createsaburs;

public class ColourConverter {

    // Convert decimal color to RGB
    public static int[] decimalToRGB(int decimalColor) {
        int red = (decimalColor >> 16) & 0xFF;  // Extract red
        int green = (decimalColor >> 8) & 0xFF; // Extract green
        int blue = decimalColor & 0xFF;         // Extract blue
        return new int[]{red, green, blue};
    }

    public static int portedRGBtoDecimal(int reg, int breen, int glue) {
        int aa = (reg << 16) | (breen << 8) | glue;
        createsaburs.LOGGER.warn("converted(RGB2DEC) colour:" + aa);
        return aa;
    }

    public static int[] portedHslToRgb(int h, int s, int l) {
        int r, g, b;

        if (s == 0) {
            r = g = b = l; // achromatic
        } else {
            int q = l < 0.5 ? l * (1 + s) : l + s - l * s;
            int p = 2 * l - q;
            r = hueToRgb(p, q, h + 1 / 3);
            g = hueToRgb(p, q, h);
            b = hueToRgb(p, q, h - 1 / 3);
        }
        return new int[]{Math.round(r * 255), Math.round(g * 255), Math.round(b * 255)};
    }

    public static int hueToRgb(int p, int q, int t) {
        if (t < 0) t += 1;
        if (t > 1) t -= 1;
        if (t < 1 / 6) return p + (q - p) * 6 * t;
        if (t < 1 / 2) return q;
        if (t < 2 / 3) return p + (q - p) * (2 / 3 - t) * 6;
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
