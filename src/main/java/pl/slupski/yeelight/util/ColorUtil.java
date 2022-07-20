package pl.slupski.yeelight.util;

public class ColorUtil {

    public static int calcColor(int r, int g, int b) {
        return r * 65536 + g * 256 + b;
    }
}
