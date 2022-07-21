package pl.slupski.yeelight;

import com.mollin.yapi.exception.YeelightSocketException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BulbManager {

    private final Map<String, Bulb> bulbs;

    public BulbManager() {
        bulbs = new HashMap<>();
    }

    public void addDevice(BulbProps bulbProps) {
        try {
            bulbs.put(bulbProps.getId(), new Bulb(bulbProps));
        } catch (YeelightSocketException ex) {
            ex.printStackTrace();
            // display error
            // log
        }
    }

    public List<Bulb> getBulbs() {
        List<Bulb> result = new ArrayList<>();
        for(Map.Entry<String, Bulb> bulb : bulbs.entrySet()) {
            result.add(bulb.getValue());
        }
        return result;
    }


}
