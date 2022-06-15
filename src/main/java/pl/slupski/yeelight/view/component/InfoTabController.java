package pl.slupski.yeelight.view.component;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import pl.slupski.yeelight.Bulb;

@Component
@FxmlView("info-tab.fxml")
public class InfoTabController {

    @FXML
    private Text bulbId;
    @FXML
    private Text bulbName;
    @FXML
    private Text bulbLocation;
    @FXML
    private Text bulbCacheControl;
    @FXML
    private Text bulbVersion;
    @FXML
    private Text bulbOn;
    @FXML
    private Text bulbBrightness;
    @FXML
    private Text bulbColorMode;
    @FXML
    private Text bulbColorTemperature;
    @FXML
    private Text bulbRgb;
    @FXML
    private Text bulbHue;
    @FXML
    private Text bulbSaturation;

    public void update(Bulb bulb) {
        bulbId.setText(bulb.getId());
        bulbName.setText(bulb.getName());
        bulbLocation.setText(bulb.getLocation());
        bulbCacheControl.setText(bulb.getCacheControl());
        bulbVersion.setText(bulb.getFirmwareVersion());
        bulbOn.setText(Boolean.toString(bulb.isOn()));
        bulbBrightness.setText(Integer.toString(bulb.getBrightness()));
        bulbColorMode.setText(Integer.toString(bulb.getColorMode()));
        bulbColorTemperature.setText(Integer.toString(bulb.getColorTemperature()));
        bulbRgb.setText(Integer.toString(bulb.getRgb()));
        bulbHue.setText(Integer.toString(bulb.getHue()));
        bulbSaturation.setText(Integer.toString(bulb.getSaturation()));
    }
}
