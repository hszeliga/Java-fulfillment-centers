package util;

import javafx.scene.control.Alert;
import system.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFromCSV {
    private static List<FulfillmentCenter> magazyny = new ArrayList<>();
    private static List<Item> produkty = new ArrayList<>();
    private static List<Item> produktyKoszyk = new ArrayList<>();

    public static List<FulfillmentCenter> getFulfillmentCenterData() throws IOException {
        magazyny = new ArrayList<>();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("centers.csv"));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");
                magazyny.add(new FulfillmentCenter(data[0], Double.valueOf(data[1])));
            }
            csvReader.close();
        } catch (FileNotFoundException exception) {}

        return magazyny;
    }


    public static List<Item> getItemData() throws IOException {
        produkty = new ArrayList<>();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("products.csv"));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");
                produkty.add(new Item(data[0],ItemCondition.NEW,SaleState.IN_STOCK,Double.valueOf(data[3]),Integer.valueOf(data[4]),Double.valueOf(data[5])));
            }
            csvReader.close();
        } catch (FileNotFoundException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nie znaleziono pliku");
            alert.setHeaderText("Brak pliku products.csv");
            alert.setContentText("Nie znaleziono pliku CSV zawierającego produkty");
            alert.showAndWait();
        }
        return produkty;
    }

    public static List<Item> getCartData() throws IOException {
        produktyKoszyk = new ArrayList<>();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("cart.csv"));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");
                produktyKoszyk.add(new Item(data[0],ItemCondition.NEW,SaleState.IN_STOCK,0,Integer.valueOf(data[1]),0));
            }
            csvReader.close();
        } catch (FileNotFoundException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nie znaleziono pliku");
            alert.setHeaderText("Brak pliku cart.csv");
            alert.setContentText("Nie znaleziono pliku CSV zawierającego produkty w koszyku");
            alert.showAndWait();
        }
        return produktyKoszyk;
    }

}