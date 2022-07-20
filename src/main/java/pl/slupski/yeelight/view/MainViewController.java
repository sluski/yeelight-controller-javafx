package pl.slupski.yeelight.view;

import com.mollin.yapi.exception.YeelightSocketException;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import pl.slupski.yeelight.BulbProps;
import pl.slupski.yeelight.BulbData;
import pl.slupski.yeelight.BulbFinder;
import pl.slupski.yeelight.BulbManager;
import pl.slupski.yeelight.interfaces.Observer;
import pl.slupski.yeelight.view.component.InfoTabController;

import java.util.List;

@Component
@RequiredArgsConstructor
@FxmlView("main-scene.fxml")
public class MainViewController implements Observer<BulbProps> {

    private Thread searchingThread;

    @FXML
    private ListView listView = new ListView();

    @FXML
    private InfoTabController infoTabController;

    private BulbManager bulbManager;

    @FXML
    protected void initialize() {
        bulbManager = new BulbManager();
        listView.getSelectionModel().selectedItemProperty().addListener((observableValue, o, selectedItem) -> onBulbSelect((BulbProps) selectedItem));
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
        listView.setCellFactory(param -> new ListCell<BulbProps>() {
            @Override
            protected void updateItem(BulbProps bulbProps, boolean empty) {
                super.updateItem(bulbProps, empty);
                if (empty || bulbProps == null || bulbProps.getName() == null) {
                    setText(null);
                } else {
                    setText(bulbProps.getName().equals("") ? bulbProps.getId() : bulbProps.getName());
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

    private void onBulbSelect(BulbProps bulbProps) {
        infoTabController.update(bulbProps);
    }
}
