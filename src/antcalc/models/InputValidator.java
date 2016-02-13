package antcalc.models;

public class InputValidator {
    public static boolean integerValidator(String s, int min, int max) {
        try {
            int temp = Integer.parseInt(s);
            if (temp >= min && temp <= max) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean floatValidator(String s, float min, float max) {
        try {
            float temp = Float.parseFloat(s);
            if (temp >= min && temp <= max) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
