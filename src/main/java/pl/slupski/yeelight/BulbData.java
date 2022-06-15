package pl.slupski.yeelight;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BulbData {

    private static List<Observer> observers = new ArrayList<>();
    private static volatile Map<String, Bulb> bulbs = new Hashtable<>();

    public synchronized static void add(Bulb bulb) {
        System.out.println("Adding: " + bulb);
        if(!bulbs.containsKey(bulb.getId())) {
            bulbs.put(bulb.getId(), bulb);
            notifyObservers();
        }
    }

    public static List<Bulb> findAll() {
        return bulbs.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
    }

    public static void attach(Observer observer) {
        observers.add(observer);
    }

    public static void detach(Observer observer) {
        observers.remove(observer);
    }

    public static void notifyObservers() {
        observers.forEach(observer -> observer.update());
    }
}
