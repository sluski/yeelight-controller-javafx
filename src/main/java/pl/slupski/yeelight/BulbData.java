package pl.slupski.yeelight;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BulbData {

    private static Map<String, Bulb> bulbs = new Hashtable<>();

    public static void add(Bulb bulb) {
        System.out.println("Adding: " + bulb);
        if(!bulbs.containsKey(bulb.getId())) {
            bulbs.put(bulb.getId(), bulb);
        }
    }

    public static List<Bulb> findAll() {
        return bulbs.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
    }
}
