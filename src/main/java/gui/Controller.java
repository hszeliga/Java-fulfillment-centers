package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import system.*;
import util.ReadFromCSV;
import util.Serialization;

import java.io.*;

public class Controller implements Serializable {
    @FXML private ComboBox fulfillmentChoiceComboBox;
    @FXML private TableView<TableModel> productsTable;
    @FXML private TableColumn<TableModel, String> nazwa;
    @FXML private TableColumn<TableModel, ItemCondition> stan;
    @FXML private TableColumn<TableModel, SaleState> status;
    @FXML private TableColumn<TableModel, Number> masa;
    @FXML private TableColumn<TableModel, Number> ilosc;
    @FXML private TableColumn<TableModel, Number> cena;
    @FXML private TableColumn<TableModel, String> magazyn;
    @FXML private TextField searchBox;
    @FXML private TableView<TableModel> cartTable;
    @FXML private TableColumn<TableModel, String> nazwaKoszyk;
    @FXML private TableColumn<TableModel, Number> iloscKoszyk;
    @FXML private Button addToCartButton;
    @FXML private Button buyButton;
    @FXML private Spinner<Integer> setQuantitySpinner;
    @FXML private Label quantityLabel;
    @FXML private Button removeFromCartButton;

    private static ObservableList<TableModel> data = FXCollections.observableArrayList();
    private static ObservableList<TableModel> cartData = FXCollections.observableArrayList();


    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        //POBIERANIE PRODUKTOW:
        ObservableList<Item> items = FXCollections.observableArrayList();
        ObservableList<FulfillmentCenter> fulfillmentCenters = FXCollections.observableArrayList();
        try {
            for(int i = 0; i< ReadFromCSV.getItemData().size(); i++)
                items.add(ReadFromCSV.getItemData().get(i));
            for (int i = 0; i < ReadFromCSV.getFulfillmentCenterData().size(); i++)
                fulfillmentCenters.add(ReadFromCSV.getFulfillmentCenterData().get(i));

            for (int i = 0; i < items.size(); i++)
                data.add(new TableModel(items.get(i), fulfillmentCenters.get((int) (Math.random() * (((fulfillmentCenters.size() - 1) - 0) + 1)) + 0)));
        }catch (IndexOutOfBoundsException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nie znaleziono pliku");
            alert.setHeaderText("Brak pliku centers.csv");
            alert.setContentText("Nie znaleziono pliku CSV zawierającego magazyny");
            alert.showAndWait();
        }

        //POBIERANIE KOSZYKA:
        ObservableList<Item> itemsCart = FXCollections.observableArrayList();
        try {
            for(int i = 0; i< ReadFromCSV.getCartData().size(); i++) {
                itemsCart.add(ReadFromCSV.getCartData().get(i));
                itemsCart.get(i).setNewIloscProperty(itemsCart.get(i).getIlosc());
            }

            for (int i = 0; i < ReadFromCSV.getCartData().size(); i++) {
                cartData.add(new TableModel(itemsCart.get(i), fulfillmentCenters.get((int) (Math.random() * (((fulfillmentCenters.size() - 1) - 0) + 1)) + 0)));
            }
        }catch (IndexOutOfBoundsException e){}


        //TABELA PRODUKTOW:
        nazwa.setCellValueFactory(cellData -> cellData.getValue().getItem().nazwaProperty());
        stan.setCellValueFactory(cellData -> cellData.getValue().getItem().stanProperty());
        status.setCellValueFactory(cellData -> cellData.getValue().getItem().statusProperty());
        masa.setCellValueFactory(cellData -> cellData.getValue().getItem().masaProperty());
        ilosc.setCellValueFactory(cellData -> cellData.getValue().getItem().iloscProperty());
        cena.setCellValueFactory(cellData -> cellData.getValue().getItem().cenaProperty());
        magazyn.setCellValueFactory(cellData -> cellData.getValue().getFulfillmentCenter().nazwaMagazynuProperty());

        Serialization.productsSerialize();

        //TOOLTIP:
        productsTable.setRowFactory(tableView -> new TableRow<TableModel>() {
            private Tooltip tooltip = new Tooltip();
            @Override
            public void updateItem(TableModel cell, boolean empty) {
                super.updateItem(cell, empty);
                if (cell == null) {
                    setTooltip(null);
                } else {
                    tooltip.setText(
                            "Magazyn: " + cell.getFulfillmentCenter().getNazwaMagazynu() + "\n"
                                    + "Maksymalna pojemność: " + cell.getFulfillmentCenter().getMaxMasa() + "\n"
                    );
                    setTooltip(tooltip);
                }
            }
        });

        productsTable.setItems(data);

        //COMBOBOX:
        fulfillmentChoiceComboBox.getItems().add("dowolny");
        fulfillmentChoiceComboBox.getItems().addAll(ReadFromCSV.getFulfillmentCenterData());

        fulfillmentChoiceComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(fulfillmentChoiceComboBox.getSelectionModel().getSelectedIndex() ==0)
                    productsTable.setItems(data);
                else {
                    ObservableList<TableModel> newData = FXCollections.observableArrayList();
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getFulfillmentCenter().getNazwaMagazynuProperty().equals(fulfillmentChoiceComboBox.getSelectionModel().getSelectedItem().toString())) {
                            newData.add(new TableModel(data.get(i).getItem(), data.get(i).getFulfillmentCenter()));
                        }
                    }
                    productsTable.setItems(newData);
                }
            }

        });

        //TEXTFIELD:
        searchBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (searchBox.getCharacters().length()==0)
                    productsTable.setItems(data);
                else {
                    ObservableList<TableModel> newData = FXCollections.observableArrayList();
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getItem().getNazwa().contains(searchBox.getCharacters().toString().toLowerCase())) {
                            newData.add(new TableModel(data.get(i).getItem(), data.get(i).getFulfillmentCenter()));
                        }
                    }
                    productsTable.setItems(newData);
                }
            }
        });

        //KOSZYK:
        nazwaKoszyk.setCellValueFactory(cellData -> cellData.getValue().getItem().nazwaProperty());
        iloscKoszyk.setCellValueFactory(cellData -> cellData.getValue().getItem().newIloscProperty());


        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    TableModel table = productsTable.getSelectionModel().getSelectedItem();
                    setQuantitySpinner.setVisible(true);
                    quantityLabel.setVisible(true);
                    SpinnerValueFactory<Integer> quantity = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, table.getItem().getIlosc(), 1);
                    setQuantitySpinner.setValueFactory(quantity);
                    setQuantitySpinner.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            setQuantitySpinner.commitValue();
                            table.getItem().setNewIloscProperty(setQuantitySpinner.getValue());
                            table.getItem().setIloscProperty(table.getItem().getIlosc() - setQuantitySpinner.getValue());
                        }
                    });
                    if(!cartTable.getItems().stream().anyMatch(itemCheck -> table.getItem().getNazwaProperty().equals(itemCheck.getItem().getNazwaProperty())))
                        cartData.add(table);
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Błąd");
                        alert.setHeaderText("Produkt znajduje się już w koszyku");
                        alert.showAndWait();
                    }
                }catch (NullPointerException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Nie zaznaczono produktu");
                    alert.showAndWait();
                }
            }
        });
        cartTable.setItems(cartData);
        Serialization.cartSerialize();

        //BUTTON USUN Z KOSZYKA:
        removeFromCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    cartData.remove(cartTable.getSelectionModel().getSelectedItem());
                }catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Nie zaznaczono produktu");
                    alert.showAndWait();
                }
            }});

        //BUTTON ZAKUPU:
        buyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cartData.clear();
            }
        });


    }

    public static ObservableList<TableModel> getData() {
        return data;
    }

    public static ObservableList<TableModel> getCartData() {
        return cartData;
    }
}