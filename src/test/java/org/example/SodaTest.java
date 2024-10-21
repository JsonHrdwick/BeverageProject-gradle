package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class SodaTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void serializeAndDeserializeToCSV() throws IOException {
        Beverage testSoda = new Soda("Coke", 5.99, 200, 32);
        System.out.println("Initialized Soda Beverage: ");
        Soda.sodaPrint(testSoda);

        System.out.println();

        Soda.serializeToCSV(testSoda, "src/test/java/org/example/SerializeTest.csv");

        Beverage deserializedSoda = Soda.deserializeFromCSV("src/test/java/org/example/SerializeTest.csv");
        System.out.println();
        System.out.println("Retrieved Soda Beverage: ");
        Soda.sodaPrint(deserializedSoda);

        // Tests the overridden Soda.equals method
        assertTrue(testSoda.equals(deserializedSoda));

        // Tests every field
        //assertEquals(testSoda, deserializedSoda);
    }

    @Test
    void setToAndFromCSV() throws IOException {
        Set<Soda> testSet = new TreeSet<>();
        testSet.add(new Soda("Coke", 2.99, 200, 32));
        testSet.add(new Soda("Fanta", 5.99, 400, 64));
        testSet.add(new Soda("Dr. Pepper", 3.99, 180, 32));
        testSet.add(new Soda("Sprite", 1.99, 100, 16));

        System.out.println("Set: ");
        for (Soda soda : testSet) {
            Soda.sodaPrint(soda);
        }
        System.out.println();

        Soda.setToCSV(testSet, "src/test/java/org/example/SetSerializeTest.csv");

        Set<Soda> deserializeSet = new TreeSet<>();
        deserializeSet = Soda.setFromCSV("src/test/java/org/example/SetSerializeTest.csv");

        for (Soda soda : deserializeSet) {
            Soda.sodaPrint(soda);
        }
        System.out.println();

        assertEquals(testSet, deserializeSet);
    }
    @Test
    void toAndFromXML() throws IOException {
        Beverage testSoda = new Soda("Coke", 2.99, 200, 32);
        Soda.serializeToXML(testSoda, "src/test/java/org/example/XMLSerializeTest.xml");
        Soda.sodaPrint(testSoda);
        System.out.println();
        Beverage newSoda = Soda.deserializeFromXML("src/test/java/org/example/XMLSerializeTest.xml");
        Soda.sodaPrint(newSoda);

        assertTrue(testSoda.equals(newSoda));
    }

    @Test
    void toAndFromBinary() throws IOException {
        Beverage testSoda = new Soda("Coke", 2.99, 200, 32);
        Soda.serializeToBinary(testSoda, "src/test/java/org/example/BinarySerializeTest.binary");
        Soda.sodaPrint(testSoda);
        System.out.println();
        Beverage newSoda = Soda.deserializeFromBinary("src/test/java/org/example/BinarySerializeTest.binary");
        Soda.sodaPrint(newSoda);

        assertTrue(testSoda.equals(newSoda));
    }

    @Test
    void compareTest() throws IOException {
        Soda testSoda = new Soda("Sprite", 2.99, 200, 32);
        Soda testSoda2 = new Soda("Coke", 2.99, 200, 32);

        System.out.println(testSoda.compareTo(testSoda2));
    }
}