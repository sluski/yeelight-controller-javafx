package pl.slupski.yeelight.view.component;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import pl.slupski.yeelight.BulbProps;

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

    public void update(BulbProps bulbProps) {
        bulbId.setText(bulbProps.getId());
        bulbName.setText(bulbProps.getName());
        bulbLocation.setText(bulbProps.getLocation());
        bulbCacheControl.setText(bulbProps.getCacheControl());
        bulbVersion.setText(bulbProps.getFirmwareVersion());
        bulbOn.setText(Boolean.toString(bulbProps.isOn()));
        bulbBrightness.setText(Integer.toString(bulbProps.getBrightness()));
        bulbColorMode.setText(Integer.toString(bulbProps.getColorMode()));
        bulbColorTemperature.setText(Integer.toString(bulbProps.getColorTemperature()));
        bulbRgb.setText(Integer.toString(bulbProps.getRgb()));
        bulbHue.setText(Integer.toString(bulbProps.getHue()));
        bulbSaturation.setText(Integer.toString(bulbProps.getSaturation()));
    }
}
