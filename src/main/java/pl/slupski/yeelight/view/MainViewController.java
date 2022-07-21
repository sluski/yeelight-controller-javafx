package pl.slupski.yeelight.view;

import com.mollin.yapi.exception.YeelightResultErrorException;
import com.mollin.yapi.exception.YeelightSocketException;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.ToggleSwitch;
import org.springframework.stereotype.Component;
import pl.slupski.yeelight.*;
import pl.slupski.yeelight.interfaces.Observer;
import pl.slupski.yeelight.view.component.InfoTabController;

import java.util.Calendar;


@Component
@RequiredArgsConstructor
@FxmlView("main-scene.fxml")
public class MainViewController implements Observer<BulbProps> {

    private Thread searchingThread;

    @FXML
    private ListView listView = new ListView();

    @FXML
    private InfoTabController infoTabController;
    @FXML
    private ToggleSwitch powerSwitch;
    @FXML
    private Slider brightnessSlider;

    private BulbManager bulbManager;
    private Bulb selectedBulb;
    private long brightnessLastUpdate;

    @FXML
    protected void initialize() {
        bulbManager = new BulbManager();
        listView.getSelectionModel().selectedItemProperty().addListener((observableValue, o, selectedItem) -> onBulbSelect((Bulb) selectedItem));
        brightnessSlider.valueProperty().addListener((observableValue, aBoolean, t1) -> {
            try {
                if (Calendar.getInstance().getTimeInMillis() - brightnessLastUpdate > 50) {
                    selectedBulb.setBrightness((int) brightnessSlider.getValue());
                    brightnessLastUpdate = Calendar.getInstance().getTimeInMillis();
                }
            } catch (YeelightSocketException e) {
                e.printStackTrace();
            } catch (YeelightResultErrorException e) {
                e.printStackTrace();
            }
        });

        powerSwitch.selectedProperty().addListener(((observableValue, aBoolean, t1) -> {
            try {
                selectedBulb.setPower(powerSwitch.isSelected());
            } catch (YeelightSocketException e) {
                e.printStackTrace();
            } catch (YeelightResultErrorException e) {
                e.printStackTrace();
            }
        }));
        BulbData.attach(this);
    }

    @FXML
    public void searchForBulbs() {
        searchingThread = new BulbFinder();
        searchingThread.start();
    }

    @FXML
    public void loadBulbs() throws YeelightSocketException {
        bulbManager.getBulbs().forEach(bulb -> listView.getItems().add(bulb));
        listView.setCellFactory(param -> new ListCell<Bulb>() {
            @Override
            protected void updateItem(Bulb bulb, boolean empty) {
                super.updateItem(bulb, empty);
                if (empty || bulb == null || bulb.getBulbProps().getName() == null) {
                    setText(null);
                } else {
                    setText(bulb.getBulbProps().getName().equals("") ? bulb.getBulbProps().getId() : bulb.getBulbProps().getName());
                }
            }
        });
    }

    @Override
    public void update(BulbProps bulbProps) {
        try {
            bulbManager.addDevice(bulbProps);
            loadBulbs();
        } catch (YeelightSocketException ex) {
            ex.printStackTrace();
        }
    }

    private void onBulbSelect(Bulb bulb) {
        selectedBulb = bulb;
        powerSwitch.setSelected(bulb.getBulbProps().isOn());
        brightnessSlider.setValue(bulb.getBulbProps().getBrightness());
        infoTabController.update(bulb.getBulbProps());
    }
}
