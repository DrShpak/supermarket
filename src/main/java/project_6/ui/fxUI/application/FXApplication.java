package project_6.ui.fxUI.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project_6.DatabaseStaticContext;
import project_6.buyer.Buyer;
import project_6.misc.Position;
import project_6.misc.Storage;
import project_6.tradingFloor.TradingFloorBuilder;
import project_6.tradingFloor.TradingFloorFx;

import java.sql.SQLException;
import java.util.List;

public class FXApplication extends Application {
    private static final Buyer buyer;
    private static final TradingFloorFx floor;

    static {
        Buyer buyerTmp = null;
        TradingFloorFx floorTmp = null;
        try {
            buyerTmp = DatabaseStaticContext.buyerDAO.queryForId("Anton");
            floorTmp = new TradingFloorBuilder().setDefaultStuffInStorage().buildFx();
        } catch (Exception e) {
            e.printStackTrace();
        }
        buyer = buyerTmp;
        floor = floorTmp;
    }

    @FXML
    private TableView<Position> storage;
    @FXML
    private TableColumn<Position, String> storageCount;
    @FXML
    private TableColumn<Position, String> storageSku;
    @FXML
    private TableColumn<Position, String> storageName;
    @FXML
    private TableColumn<Position, String> storagePrice;
    @FXML
    private TableColumn<Position, String> storageCalories;
    @FXML
    private TableView<Buyer> buyerView;
    @FXML
    private TableColumn<Buyer, String> buyerName;
    @FXML
    private TableColumn<Buyer, String> buyerAge;
    @FXML
    private TableColumn<Buyer, String> buyerCredits;
    @FXML
    private TableColumn<Buyer, String> buyerCalories;
    @FXML
    private TableView<Position> shopping;
    @FXML
    private TableColumn<Position, String> shoppingCount;
    @FXML
    private TableColumn<Position, String> shoppingSku;
    @FXML
    private TableColumn<Position, String> shoppingName;
    @FXML
    private TableColumn<Position, String> shoppingPrice;
    @FXML
    private TableColumn<Position, String> shoppingCalories;
    @FXML
    private TextField SaleValue;
    @FXML
    private Button SaleButton;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //noinspection ConstantConditions
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXApplication.fxml"));
        primaryStage.setTitle("Supermarket");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    @SuppressWarnings("unused")
    @FXML
    public void exitApplication(ActionEvent event) {
        DatabaseStaticContext.close();
        Platform.exit();
    }

    @FXML
    public void initialize() {
        storage.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
                SaleButton.setDisable(newSelection == null)
        );
        SaleButton.onActionProperty().setValue((event) -> {
            floor.changePrice(Integer.parseInt(SaleValue.getText()), storage.getSelectionModel().getSelectedItem().getProduct().getSkuNumber());
            storage.refresh();
        });

        storage.getItems().addAll(Storage.getPositions());
        storageCount.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getCount())));
        storageSku.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getProduct().getSkuNumber())));
        storageName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getProduct().getName())));
        storagePrice.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getProduct().getSalePrice())));
        storageCalories.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getProduct().getCalories())));

        buyerView.getItems().add(buyer);
        buyerName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getName())));
        buyerAge.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getAge())));
        buyerCredits.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getCapital())));
        buyerCalories.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getCalories())));

        shopping.getItems().addAll(buyer.getShoppingList());
        shoppingCount.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getCount())));
        shoppingSku.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getProduct().getSkuNumber())));
        shoppingName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getProduct().getName())));
        shoppingPrice.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getProduct().getSalePrice())));
        shoppingCalories.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getProduct().getCalories())));
    }

    @FXML
    public void addAge() throws SQLException {
        buyer.setAge(buyer.getAge() + 1);
        buyerView.refresh();
        DatabaseStaticContext.buyerDAO.update(buyer);
    }

    @FXML
    public void addCalories() throws SQLException {
        buyer.setCalories(buyer.getCalories() + 1000);
        buyerView.refresh();
        DatabaseStaticContext.buyerDAO.update(buyer);
    }

    @FXML
    public void addCredits() throws SQLException {
        buyer.setCapital(buyer.getCapital() + 1000);
        buyerView.refresh();
        DatabaseStaticContext.buyerDAO.update(buyer);
    }

    public void buy() throws SQLException {
        var item = buyer.getShoppingList().stream().filter(x -> buyer.getResidue(x) > 0).findFirst();
        if (item.isEmpty()) {
            return;
        }
        floor.buy(buyer, List.of(item.get()));
        storage.refresh();
        buyerView.refresh();
        shopping.refresh();
        DatabaseStaticContext.buyerDAO.update(buyer);
    }
}
