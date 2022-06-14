package pl.slupski.yeelight;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FxmlView("main-scene.fxml")
public class MainViewController implements Observer {

    private Thread searchingThread;
    private List<Bulb> bulbs;
    @FXML
    private ListView listView = new ListView();
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

    @FXML
    protected void initialize() {
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Bulb selectedBulb = (Bulb) t1;
                bulbId.setText(selectedBulb.getId());
                bulbName.setText(selectedBulb.getName());
                bulbLocation.setText(selectedBulb.getLocation());
                bulbCacheControl.setText(selectedBulb.getCacheControl());
                bulbVersion.setText(selectedBulb.getFirmwareVersion());
                bulbOn.setText(Boolean.toString(selectedBulb.isOn()));
                bulbBrightness.setText(Integer.toString(selectedBulb.getBrightness()));
                bulbColorMode.setText(Integer.toString(selectedBulb.getColorMode()));
                bulbColorTemperature.setText(Integer.toString(selectedBulb.getColorTemperature()));
                bulbRgb.setText(Integer.toString(selectedBulb.getRgb()));
                bulbHue.setText(Integer.toString(selectedBulb.getHue()));
                bulbSaturation.setText(Integer.toString(selectedBulb.getSaturation()));
            }
        });

        BulbData.attach(this);
    }

    @FXML
    public void searchForBulbs() {
        searchingThread = new BulbFinder();
        searchingThread.start();
    }

    @FXML
    public void loadBulbs() {
        bulbs = BulbData.findAll();
        listView.getItems().clear();
        bulbs.forEach(bulb -> listView.getItems().add(bulb));
        listView.setCellFactory(param -> new ListCell<Bulb>() {
            @Override
            protected void updateItem(Bulb bulb, boolean empty) {
                super.updateItem(bulb, empty);
                if(empty || bulb == null || bulb.getName() == null) {
                    setText(null);
                } else {
                    setText(bulb.getName().equals("") ? bulb.getId() : bulb.getName());
                }
            }
        });
    }

    @Override
    public void update() {
        loadBulbs();
    }
}
