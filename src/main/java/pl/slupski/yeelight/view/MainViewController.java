package pl.slupski.yeelight.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import pl.slupski.yeelight.Bulb;
import pl.slupski.yeelight.BulbData;
import pl.slupski.yeelight.BulbFinder;
import pl.slupski.yeelight.Observer;
import pl.slupski.yeelight.view.component.InfoTabController;

import java.util.List;

@Component
@FxmlView("main-scene.fxml")
public class MainViewController implements Observer {

    private Thread searchingThread;
    private List<Bulb> bulbs;

    @FXML
    private ListView listView = new ListView();

    @FXML
    private InfoTabController infoTabController;

    @FXML
    protected void initialize() {
        listView.getSelectionModel().selectedItemProperty().addListener((observableValue, o, selectedItem) -> infoTabController.update((Bulb) selectedItem));
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
                if (empty || bulb == null || bulb.getName() == null) {
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
