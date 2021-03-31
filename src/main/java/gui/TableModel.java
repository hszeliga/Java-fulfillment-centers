package gui;

import javafx.beans.property.*;
import system.FulfillmentCenter;
import system.Item;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TableModel implements Serializable {
    private  final ObjectProperty<Item> item = new SimpleObjectProperty<>();
    private  final ObjectProperty<FulfillmentCenter> fulfillmentCenter = new SimpleObjectProperty<>();


    private void writeObject(ObjectOutputStream out)
            throws IOException {
        // out.defaultWriteObject();
        out.writeUTF(getItem().getNazwaProperty());
        out.writeObject(getItem().getStanProperty());
        out.writeObject(getItem().getStatusProperty());
        out.writeDouble(getItem().getMasaProperty());
        out.writeInt(getItem().getIloscProperty());
        out.writeDouble(getItem().getCenaProperty());
        out.writeUTF(getFulfillmentCenter().getNazwaMagazynuProperty());
    }


    public  Item getItem() {
        return item.get();
    }

    public ObjectProperty<Item> itemProperty(){
        return item;
    }

    public void setItem(Item item){
        this.item.set(item);
    }
    public FulfillmentCenter getFulfillmentCenter() {
        return fulfillmentCenter.get();
    }

    public ObjectProperty<FulfillmentCenter> fulfillmentCenterProperty(){
        return fulfillmentCenter;
    }

    public void setFulfillmentCenter(FulfillmentCenter fulfillmentCenter){
        this.fulfillmentCenter.set(fulfillmentCenter);
    }

    public TableModel(Item item, FulfillmentCenter fulfillmentCenter) {
        this.item.set(item);
        this.fulfillmentCenter.set(fulfillmentCenter);
    }

}