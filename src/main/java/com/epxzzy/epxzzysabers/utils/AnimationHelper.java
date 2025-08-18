package com.epxzzy.epxzzysabers.utils;

public class AnimationHelper {
    // regular ease out
    public static double easeOut(double number) {
       return 1 - Math.pow(1 - number, 5);
    }

    //soft start => swift movement => soft end
    public static double easeInOutQuart(double number){
        return number < 0.5 ? 8 * number * number * number * number : 1 - Math.pow(-2 * number + 2, 4) / 2;
    }

    //swift start => sudden jolt => swift end
    public static double easeInOutExpo(double number){
        return number == 0
                ? 0
                : number == 1
                ? 1
                : number < 0.5 ? Math.pow(2, 20 * number - 10) / 2
                : (2 - Math.pow(2, -20 * number + 10)) / 2;

    }
    //ease out but with a small recoil back
    public static double easeOutBack(double number){
        double c1 = 1.70158;
        double c3 = c1 + 1;

        return 1 + c3 * Math.pow(number - 1, 3) + c1 * Math.pow(number - 1, 2);
    }

}
