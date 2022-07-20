package pl.slupski.yeelight;

import com.mollin.yapi.YeelightDevice;
import com.mollin.yapi.exception.YeelightResultErrorException;
import com.mollin.yapi.exception.YeelightSocketException;
import lombok.Getter;
import pl.slupski.yeelight.util.ColorUtil;

public class Bulb {

    @Getter
    private final BulbProps bulbProps;
    private final YeelightDevice device;


    public Bulb(BulbProps bulbProps) throws YeelightSocketException {
        this.bulbProps = bulbProps;
        device = new YeelightDevice(bulbProps.getIp(), bulbProps.getPort());
    }

    public void setColorTemperature(int r, int g, int b) throws YeelightSocketException, YeelightResultErrorException {
        Runnable runnable = () -> {
            try {
                device.setColorTemperature(ColorUtil.calcColor(r, g, b));
            } catch (YeelightResultErrorException e) {
                e.printStackTrace();
            } catch (YeelightSocketException e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();
    }


}
