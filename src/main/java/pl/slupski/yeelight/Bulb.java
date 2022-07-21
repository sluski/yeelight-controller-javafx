package pl.slupski.yeelight;

import com.mollin.yapi.YeelightDevice;
import com.mollin.yapi.exception.YeelightResultErrorException;
import com.mollin.yapi.exception.YeelightSocketException;
import lombok.Getter;
import pl.slupski.yeelight.util.ColorUtil;

public class Bulb {

    @Getter
    private final BulbProps bulbProps;
    private YeelightDevice device;


    public Bulb(BulbProps bulbProps) throws YeelightSocketException {
        this.bulbProps = bulbProps;
        device = new YeelightDevice(bulbProps.getIp(), bulbProps.getPort());
    }

    public void setPower(boolean power) throws YeelightSocketException, YeelightResultErrorException {
        device.setPower(power);
        bulbProps.setOn(power);
    }

    public void setBrightness(int brightness) throws YeelightSocketException, YeelightResultErrorException {
        device.setBrightness(brightness);
        bulbProps.setBrightness(brightness);
    }

    public void setColorTemperature(int r, int g, int b) throws YeelightSocketException, YeelightResultErrorException {
        device.setColorTemperature(ColorUtil.calcColor(r, g, b));
    }


}
