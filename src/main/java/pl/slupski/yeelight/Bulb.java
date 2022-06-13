package pl.slupski.yeelight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class Bulb implements Serializable {

    private String cacheControl;
    private String location;
    private String id;
    private String model;
    private String firmwareVersion;
    private List<String> supportedMethods;
    private boolean on;
    private int brightness;
    private int colorMode;
    private int colorTemperature;
    private int rgb;
    private int hue;
    private int saturation;
    private String name;


}
