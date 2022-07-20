package pl.slupski.yeelight.interfaces;

import pl.slupski.yeelight.EffectEnum;

public interface BulbService {

    void changeColorTemperature(int value, EffectEnum effect);
}
