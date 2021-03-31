package system;

import java.util.*;

public class FulfillmentCenterContainer{
    public Map<String, FulfillmentCenter> magazyny = new HashMap<String,FulfillmentCenter>();



    public void addCenter(String nazwaMagazynu, double maxMasa){
        magazyny.put(nazwaMagazynu,new FulfillmentCenter(nazwaMagazynu,maxMasa));
    }

    public void removeCenter(String nazwaMagazynu){
        if(magazyny.containsKey(nazwaMagazynu))
            magazyny.remove(nazwaMagazynu);
        else
            System.out.println("Brak podanego magazynu.");

    }

    public List<FulfillmentCenter> findEmpty(){
        List<FulfillmentCenter> list = new ArrayList<FulfillmentCenter>();
        for(FulfillmentCenter k: magazyny.values()){
            if(k.getMasaCounter()==0)
                list.add(k);
        }
        return list;
    }


    public void summary(){
        for(FulfillmentCenter k: magazyny.values())
            System.out.println("Magazyn: " + k.getNazwaMagazynu() + " | Zapelnienie: " + k.calcFilling() + "%");
    }

    public void sort(){ //sortowanie wg aktualnego zapelnienia
        List<FulfillmentCenter> fulfillmentsByFillings = new ArrayList<>(magazyny.values());
        Collections.sort(fulfillmentsByFillings, Comparator.comparing(FulfillmentCenter::calcFilling));
        Collections.reverse(fulfillmentsByFillings);
        for (FulfillmentCenter k : fulfillmentsByFillings)
            System.out.println("Magazyn: " + k.getNazwaMagazynu() + " | Zapelnienie: " + k.calcFilling() + "%");
    }
}