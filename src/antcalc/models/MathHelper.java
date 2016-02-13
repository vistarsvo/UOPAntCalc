package antcalc.models;

import java.util.Random;

public class MathHelper {
    private static Random fRandom = new Random();

    public static float getRandDelta() {
        float ret =  Math.round(Math.random() * 1000f) / 1000f;
        return ret;
    }

    public static float getRandSigma() {
        return (float) Math.round(fRandom.nextDouble() * 1000f) / 1000f;
    }

    public static float round(double a, int c) {
        c = c * 10;
        return Math.round(a * (float)c) / (float)c;
    }

    public static float toRadians(float Ad) {
        return (float) (Ad * Math.PI / 180f);
    }

    public static int integerConvert(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return -1;
        }
    }

    public static float floatConvert(String s) {
        try {
            return Float.parseFloat(s);
        } catch (Exception e) {
            return -1f;
        }
    }
}
