package util;

import gui.Controller;
import gui.TableModel;

import java.io.*;

public class WriteToCSV {
    private static final String CSV_SEPARATOR = ";";

    public static void writeProducts(){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("products.csv"), "UTF-8"));
            for (TableModel tm : Controller.getData()) {
                StringBuffer firstLine = new StringBuffer();

                firstLine.append(tm.getItem().getNazwaProperty());
                firstLine.append(CSV_SEPARATOR);
                firstLine.append(tm.getItem().getStanProperty());
                firstLine.append(CSV_SEPARATOR);
                firstLine.append(tm.getItem().getStatusProperty());
                firstLine.append(CSV_SEPARATOR);
                firstLine.append(tm.getItem().getMasaProperty());
                firstLine.append(CSV_SEPARATOR);
                firstLine.append(tm.getItem().getIloscProperty());
                firstLine.append(CSV_SEPARATOR);
                firstLine.append(tm.getItem().getCenaProperty());
                firstLine.append(CSV_SEPARATOR);
                firstLine.append(tm.getFulfillmentCenter().getNazwaMagazynuProperty());
                bw.write(firstLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException ignored){}
    }

    public static void writeCart(){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("cart.csv"), "UTF-8"));
            for (TableModel tm : Controller.getCartData()) {
                StringBuffer firstLine = new StringBuffer();

                firstLine.append(tm.getItem().getNazwaProperty());
                firstLine.append(CSV_SEPARATOR);
                firstLine.append(tm.getItem().getNewIloscProperty());
                bw.write(firstLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException ignored){}
    }
}
