package pl.slupski.yeelight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class BulbProps implements Serializable {

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

    public String getIp() {
        return location != null ? location.split(":")[0] : null;
    }

    public Integer getPort() {
        return location != null ? Integer.parseInt(location.split(":")[1]) : null;
    }

}
