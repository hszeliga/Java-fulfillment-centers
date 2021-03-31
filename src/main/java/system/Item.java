package system;

import javafx.beans.property.*;
import java.io.Serializable;

public class Item implements Comparable<Item>, Serializable {
    private String nazwa;
    private transient ItemCondition stan;
    private double masa;
    private int ilosc;
    private SaleState status;
    private double cena;

    public transient StringProperty nazwaProperty = new SimpleStringProperty();
    public transient ObjectProperty stanProperty = new SimpleObjectProperty();
    public transient ObjectProperty statusProperty = new SimpleObjectProperty();
    public transient DoubleProperty masaProperty = new SimpleDoubleProperty();
    public transient IntegerProperty iloscProperty = new SimpleIntegerProperty();
    public transient IntegerProperty newIloscProperty = new SimpleIntegerProperty();
    public transient DoubleProperty cenaProperty = new SimpleDoubleProperty();


    public Item(String nazwa, ItemCondition stan, SaleState status, double masa, int ilosc, double cena) {
        this.nazwa = nazwa;
        this.stan = stan;
        this.status = status;
        this.masa = masa;
        this.ilosc = ilosc;
        this.cena = cena;
        this.nazwaProperty.set(nazwa);
        this.stanProperty.set(stan);
        this.statusProperty.set(status);
        this.masaProperty.set(masa);
        this.iloscProperty.set(ilosc);
        this.cenaProperty.set(cena);
    }

    //nazwaProperty:
    public StringProperty nazwaProperty() {
        return nazwaProperty;
    }

    public String getNazwaProperty() {
        return nazwaProperty().get();
    }

    public void setNazwaProperty(String nazwa) {
        nazwaProperty().set(nazwa);
    }

    //stanProperty:
    public ObjectProperty stanProperty() {
        return stanProperty;
    }

    public Object getStanProperty() {
        return stanProperty().get();
    }

    public void setStanProperty(ItemCondition stan) {
        stanProperty().set(stan);
    }

    //statusProperty:
    public ObjectProperty statusProperty() {
        return statusProperty;
    }

    public Object getStatusProperty() {
        return statusProperty().get();
    }

    public void setStatusProperty(SaleState status) {
        statusProperty().set(status);
    }

    //masaProperty:
    public DoubleProperty masaProperty() {
        return masaProperty;
    }

    public double getMasaProperty() {
        return masaProperty().get();
    }

    public void setMasaProperty(Double masa) {
        masaProperty().set(masa);
    }

    //iloscProperty:
    public IntegerProperty iloscProperty() {
        return iloscProperty;
    }

    public int getIloscProperty() {
        return iloscProperty().get();
    }

    public void setIloscProperty(int ilosc) {
        iloscProperty().set(ilosc);
    }

    //newIloscProperty:
    public IntegerProperty newIloscProperty() {
        return newIloscProperty;
    }

    public int getNewIloscProperty() {
        return newIloscProperty().get();
    }

    public void setNewIloscProperty(int ilosc) {
        newIloscProperty().set(ilosc);
    }

    //cenaProperty:
    public DoubleProperty cenaProperty() {
        return cenaProperty;
    }

    public double getCenaProperty() {
        return cenaProperty().get();
    }

    public void setCenaProperty(Double cena) {
        cenaProperty().set(cena);
    }


    public double getCena() {
        return cena;
    }

    public SaleState getStatus() {
        return status;
    }

    public ItemCondition getStan() {
        return stan;
    }

    public int getIlosc() {
        return ilosc;
    }

    public double getMasa() {
        return masa;
    }

    public String getNazwa() {
        return nazwa;
    }


    public void zwiekszIlosc(){
        this.ilosc +=ilosc;
    }

    public void zmniejszIloscO1(){
        this.ilosc =ilosc-1;
    }

    public void summary(){
        System.out.println(nazwa + " |stan: " + stan + " |masa: " + masa + " |ilosc: " + ilosc);

    }


    @Override
    public String toString() {
        return nazwa;
    }

    @Override
    public int compareTo(Item o) {
        return nazwa.compareTo(o.nazwa);
    }

}