package pl.slupski.yeelight;

import pl.slupski.yeelight.interfaces.Observer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BulbData {

    private static List<Observer> observers = new ArrayList<>();
    private static volatile Map<String, BulbProps> bulbs = new Hashtable<>();

    public synchronized static void add(BulbProps bulbProps) {
        System.out.println("Adding: " + bulbProps);
        if(!bulbs.containsKey(bulbProps.getId())) {
            bulbs.put(bulbProps.getId(), bulbProps);
            notifyObservers(bulbProps);
        }
    }

    public static List<BulbProps> findAll() {
        return bulbs.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
    }

    public static void attach(Observer observer) {
        observers.add(observer);
    }

    public static void notifyObservers(BulbProps bulbProps) {
        observers.forEach(observer -> observer.update(bulbProps));
    }
}
