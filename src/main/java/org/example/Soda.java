package org.example;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

//@XStreamAlias("SodaClass")

class Soda implements Beverage, Serializable, Comparable<Soda> {
    private String name;
    private double price;
    private int calories;
    private int flOz;

    // Constructor
    public Soda(String name, double price, int calories, int flOz) {
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.flOz = flOz;
    }

    public Soda() {
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFlOz(int flOz) {
        this.flOz = flOz;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCalories() {
        return calories;
    }

    public int getFlOz() {
        return flOz;
    }

    // Serialize Object
    public static void serializeToCSV(Beverage soda, String filename) throws IOException {
        Path path = Paths.get(filename);
        Files.writeString(path, prettyPrintCSV(soda), StandardOpenOption.APPEND);

    }

    // Deserialize object
    public static Soda deserializeFromCSV(String filename) throws OutOfMemoryError, IOException {
        Path path = Paths.get(filename);
        List<String> filedata = Files.readAllLines(path);
        String[] data = filedata.getFirst().split(",");
        Files.writeString(path, ""); // Clear deserialized object
        return formatValues(data);
    }

    // Serialize Set
    public static void setToCSV(Set<Soda> sodas, String filename) throws IOException {
        for (Beverage soda : sodas) {
            serializeToCSV(soda, filename);
        }
    }

    // Deserialize Set
    public static TreeSet<Soda> setFromCSV(String filename) throws OutOfMemoryError, IOException {
        TreeSet<Soda> sodaSet = new TreeSet<>();
        Path path = Paths.get(filename);
        List<String> filedata = Files.readAllLines(path);

        for (String filedatum : filedata) {
            String[] data = filedatum.split(",");
            for (int j = 0; j < data.length; j++) {
                sodaSet.add(formatValues(data));
            }
        }
        Files.writeString(path, ""); // Clear deserialized objects
        return sodaSet;
    }

    // Serialize to XML
    public static void serializeToXML(Beverage soda, String filename) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.processAnnotations(Soda.class);
        String dataXML = xstream.toXML(soda);
        Path path = Paths.get(filename);
        Files.writeString(path, dataXML);
    }

    // Deserialize from XML
    public static Soda deserializeFromXML(String filename) throws OutOfMemoryError, IOException {
        Path path = Paths.get(filename);
        XStream xstream = new XStream(new StaxDriver());
        String[] type = new String[]{"Soda"};
        Class[] types = new Class[]{Soda.class};
        xstream.allowTypes(types);
        String dataXML = Files.readString(path);
        return (Soda) xstream.fromXML(dataXML);
    }


    // Formats deserialized into object class
    private static Soda formatValues(String[] data){
        // Default data values
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null || data[i].isEmpty()){
                data[i] = "0";
            }
            else {
                data[i] = data[i].trim();
            }
        }
        return new Soda(data[0].trim(), Double.parseDouble(data[1].trim()), Integer.parseInt(data[2].trim()), Integer.parseInt(data[3].trim()));
    }

    // Format CSV output
    private static String prettyPrintCSV(Beverage soda) {
        return soda.getName() + "," + soda.getPrice() + "," + soda.getCalories() + "," + soda.getFlOz() + "\n";
    }

    // Print class information
    public static void sodaPrint(Beverage soda) {
        System.out.println(soda.getName() + " , $" + soda.getPrice() + " , " + soda.getCalories() + " , " + soda.getFlOz());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Soda beverage = (Soda) o;
        return price == beverage.price && calories == beverage.calories && flOz == beverage.flOz && Objects.equals(name, beverage.name);
    }

    @Override
    public int compareTo(Soda o) {
        return -o.name.compareTo(name);
    }
}
