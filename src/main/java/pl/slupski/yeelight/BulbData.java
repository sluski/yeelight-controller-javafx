package pl.slupski.yeelight;

import com.mollin.yapi.exception.YeelightSocketException;
import pl.slupski.yeelight.interfaces.Observer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class BulbData {

    private static List<Observer> observers = new ArrayList<>();
    private static volatile Map<String, Bulb> bulbs = new Hashtable<>();

    public static void add(BulbProps bulbProps) throws YeelightSocketException {
        System.out.println("Adding: " + bulbProps);
        if(!bulbs.containsKey(bulbProps.getId())) {
            bulbs.put(bulbProps.getId(), new Bulb(bulbProps));
            notifyObservers(bulbProps);
        }
    }

    public static void attach(Observer observer) {
        observers.add(observer);
    }

    public static void notifyObservers(BulbProps bulbProps) {
        observers.forEach(observer -> observer.update(bulbProps));
    }
}
