package util;

import gui.Controller;
import gui.TableModel;
import java.io.*;
import java.util.ArrayList;

public class Serialization implements Serializable {

    public static void productsSerialize() throws IOException, ClassNotFoundException {
        try{
            FileOutputStream file = new FileOutputStream("products.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(new ArrayList<TableModel>(Controller.getData()));
            out.close();
            file.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void cartSerialize() throws IOException, ClassNotFoundException {
        try{
            FileOutputStream file = new FileOutputStream("cart.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(new ArrayList<TableModel>(Controller.getCartData()));
            out.close();
            file.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
