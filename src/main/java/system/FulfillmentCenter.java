package system;

import javafx.beans.property.*;
import java.io.Serializable;
import java.util.*;

public class FulfillmentCenter implements Comparator<Object>, Serializable {
    private String nazwaMagazynu;
    private double maxMasa;
    private double masaCounter;
    public Map<String, Item> listaProduktow = new HashMap<String, Item>();

    public transient StringProperty nazwaMagazynuProperty = new SimpleStringProperty();


    public StringProperty nazwaMagazynuProperty() {
        return nazwaMagazynuProperty;
    }

    public String getNazwaMagazynuProperty() {
        return nazwaMagazynuProperty().get();
    }

    public void setNazwaMagazynuProperty(String nazwa) {
        nazwaMagazynuProperty().set(nazwa);
    }

    public double getMasaCounter() {
        return masaCounter;
    }

    public String getNazwaMagazynu() {
        return nazwaMagazynu;
    }

    public double getMaxMasa() {
        return maxMasa;
    }

    public FulfillmentCenter(String nazwaMagazynu, double maxMasa) {
        this.nazwaMagazynu = nazwaMagazynu;
        this.maxMasa = maxMasa;
        this.nazwaMagazynuProperty.set(nazwaMagazynu);
    }

    public void addProduct(Item item){
        if((masaCounter + item.getMasa()*item.getIlosc())>maxMasa)
            System.err.println("Przekroczono maksymalna pojemnosc magazynu. Produkt nie zostal dodany.");
        else{
            if (!listaProduktow.containsKey(item.getNazwa())) {
                listaProduktow.put(item.getNazwa(), item);
                masaCounter+=item.getMasa()*item.getIlosc();
            }
            else
                item.zwiekszIlosc();
        }

    }
    public void getProduct(Item item) {
        if(listaProduktow.get(item.getNazwa()).getIlosc()>1){
            listaProduktow.get(item.getNazwa()).zmniejszIloscO1();
            masaCounter-=item.getMasa();
        }
        else{
            masaCounter-=item.getMasa();
            removeProduct(item);
        }
    }

    private void removeProduct(Item item) {
        listaProduktow.remove(item.getNazwa());
    }

    public void search(String nazwa){
        System.out.println("Wyniki wyszukiwania dla " + nazwa + ": ");
        for(Item item: listaProduktow.values()){
            if(compare(nazwa,item.getNazwa())==1)
                System.out.println(item + " | ");
        }
    }

    public void searchPartial(String nazwa){
        System.out.println("Wyniki wyszukiwania dla " + nazwa + ": ");
        for (Item item : listaProduktow.values()) {
            if (item.getNazwa().toLowerCase().indexOf(nazwa.toLowerCase()) == 0)
                System.out.print(item + " | ");
        }
        System.out.println();
    }

    public int countByCondition(ItemCondition stan){
        int counter =0;
        System.out.print("Liczba przedmiotow w stanie " + stan + ": ");
        for(Item item: listaProduktow.values()){
            if(item.getStan()==stan)
                counter+=item.getIlosc();
        }
        return counter;
    }

    public void filterByState(SaleState status){
        System.out.println("Lista przedmiotow w stanie " + status + ": ");
        for(Item item: listaProduktow.values()){
            if(item.getStatus()==status)
                System.out.println(item.getNazwa());
        }
    }

    public void summary(){
        for(Item item: listaProduktow.values())
            System.out.println(item.getNazwa() + " |stan: " + item.getStan() + " |masa: " + item.getMasa() + " |ilosc: " + item.getIlosc());
    }

    public void sortByName(){
        Map<String, Item> sortedMap = new TreeMap<String, Item>(listaProduktow);
        System.out.println(sortedMap.values());
    }

    public void sortByAmount(){
        List<Item> list1 = new ArrayList<>(listaProduktow.values());
        List<Item> list2 = new ArrayList<>(list1);
        list1.sort(this::compare);
        Collections.reverse(list1);
        System.out.println(list1);
    }

    public Item max(){
        return Collections.max(listaProduktow.values(), Comparator.comparing(Item::getIlosc));

    }

    public double calcFilling(){
        return 100*masaCounter/maxMasa;
    }


    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof String){
            if(o1.toString().compareTo(o2.toString()) == 0)
                return 1;
            else
                return -1;
        }
        if(o1 instanceof Integer){
            return (int)o1 - (int)o2;
        }

        if(o1 instanceof Item){
            return ((Item)o1).getIlosc() - ((Item)o2).getIlosc();
        }
        return 0;
    }

    @Override
    public String toString() {
        return nazwaMagazynu;
    }

}