package com.epxzzy.createsaburs.utils;

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
        //CreateSaburs.LOGGER.warn("converted(RGB2DEC) colour:" + aa);
        return aa;
    }

    public static String getHexString(int decimalcolour) {
        int[] theones = PortedDecimaltoRGB(decimalcolour);
        String f = Integer.toHexString(theones[0]).equals("0") ? "00" : Integer.toHexString(theones[0]).toUpperCase();
        String s = Integer.toHexString(theones[1]).equals("0") ? "00" : Integer.toHexString(theones[1]).toUpperCase();
        String t = Integer.toHexString(theones[2]).equals("0") ? "00" : Integer.toHexString(theones[2]).toUpperCase();

        return "#"+f+s+t;
    }

    public static int[] RGBtoHSL(int r, int g, int b) {
        float rPercent = r / 255f;
        float gPercent = g / 255f;
        float bPercent = b / 255f;

        float max = Math.max(rPercent, Math.max(gPercent, bPercent));
        float min = Math.min(rPercent, Math.min(gPercent, bPercent));
        float delta = max - min;

        float h = 0;
        if (delta != 0) {
            if (max == rPercent) {
                h = (gPercent - bPercent) / delta + (gPercent < bPercent ? 6 : 0);
            } else if (max == gPercent) {
                h = (bPercent - rPercent) / delta + 2;
            } else {
                h = (rPercent - gPercent) / delta + 4;
            }
            h *= 60;
        }

        float l = (max + min) / 2;
        float s = delta == 0 ? 0 : delta / (1 - Math.abs(2 * l - 1));

        return new int[]{Math.round(h), Math.round(s * 100), Math.round(l * 100)};
    }

    public static int[] HSLtoRGB(int h, int s, int l) {
        float sPercent = s / 100f;
        float lPercent = l / 100f;

        float c = (1 - Math.abs(2 * lPercent - 1)) * sPercent;
        float x = c * (1 - Math.abs((h / 60f) % 2 - 1));
        float m = lPercent - c / 2;

        float rPrime = 0, gPrime = 0, bPrime = 0;

        if (h == 360) {
            //rollback cause the decimal convertor seems to get fucky with it
            h = 0;
        }

        if (h >= 0 && h < 60) {
            rPrime = c;
            gPrime = x;
        } else if (h >= 60 && h < 120) {
            rPrime = x;
            gPrime = c;
        } else if (h >= 120 && h < 180) {
            gPrime = c;
            bPrime = x;
        } else if (h >= 180 && h < 240) {
            gPrime = x;
            bPrime = c;
        } else if (h >= 240 && h < 300) {
            rPrime = x;
            bPrime = c;
        } else if (h >= 300 && h < 360) {
            rPrime = c;
            bPrime = x;
        }

        int r = (int) Math.floor((rPrime + m) * 255);
        int g = (int) Math.floor((gPrime + m) * 255);
        int b = (int) Math.floor((bPrime + m) * 255);

        return new int[]{r, g, b};
    }

    public static int[] rainbowColor(int timeStep) {
        int localTimeStep = Math.abs(timeStep) % 1536;
        int timeStepInPhase = localTimeStep % 256;
        int phaseBlue = localTimeStep / 256;
        int red = colorInPhase(phaseBlue + 4, timeStepInPhase);
        int green = colorInPhase(phaseBlue + 2, timeStepInPhase);
        int blue = colorInPhase(phaseBlue, timeStepInPhase);
        return new int[]{red, green, blue};
    }
    private static int colorInPhase(int phase, int progress) {
        phase = phase % 6;
        if (phase <= 1)
            return 0;
        if (phase == 2)
            return progress;
        if (phase <= 4)
            return 255;
        else
            return 255 - progress;
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
