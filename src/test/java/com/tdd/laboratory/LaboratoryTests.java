package com.tdd.laboratory;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LaboratoryTests {

    @Test
    void initLaboratory() {
        List<String> list = List.of("A", "B", "C", "D");

        var laboratory = new Laboratory(list);

        assertEquals(list, laboratory.knownSubstances);
    }

    @Test
    void getExistingSubstanceQuantity(){
        var laboratory = new Laboratory(List.of("A", "B", "C", "D"));

        laboratory.stocks.put("A", 2.1);

        assertEquals(2.1, laboratory.getQuantity("A"));
    }

    @Test
    void getNonExistingSubstanceQuantity(){
        var laboratory = new Laboratory(List.of("A", "B", "C", "D"));

        assertThrows(IllegalArgumentException.class, () -> {
            laboratory.getQuantity("ZZZ");
        });
    }

    @Test
    void addToStock(){
        var laboratory = new Laboratory(List.of("A", "B", "C", "D"));

        laboratory.add("A", 2.0);

        assertEquals(2.0, laboratory.getQuantity("A"));
    }

    @Test
    void addMultipleSubstances(){
        var laboratory = new Laboratory(List.of("A", "B", "C", "D"));

        laboratory.add("A", 2.0);
        laboratory.add("B", 3.0);

        assertEquals(2.0, laboratory.getQuantity("A"));
        assertEquals(3.0, laboratory.getQuantity("B"));
    }

    @Test
    void addMultipleOfSameSubstance(){
        var laboratory = new Laboratory(List.of("A", "B", "C", "D"));

        laboratory.add("A", 2.0);
        laboratory.add("A", 3.0);

        assertEquals(5.0, laboratory.getQuantity("A"));
    }

    @Test
    void addNonExistingSubstance(){
        var laboratory = new Laboratory(List.of("A", "B", "C", "D"));

        assertThrows(IllegalArgumentException.class, () -> {
            laboratory.add("E", 2.0);
        });
    }

    @Test
    void addZeroQuantity(){
        var laboratory = new Laboratory(List.of("A", "B", "C", "D"));

        assertThrows(IllegalArgumentException.class, () -> {
            laboratory.add("A", 0.0);
        });
    }

    @Test
    void addNegativeValueWhileStockIsNull(){
        var laboratory = new Laboratory(List.of("A", "B", "C", "D"));

        assertThrows(IllegalArgumentException.class, () -> {
            laboratory.add("A", -2.0);
        });
    }

    @Test
    void addNegativeValueWhileStockIsEnough(){
        var laboratory = new Laboratory(List.of("A", "B", "C", "D"));

        laboratory.add("A", 2.0);
        laboratory.add("A", -1.0);

        assertEquals(1.0, laboratory.getQuantity("A"));
    }

    @Test
    void addNegativeValueWhileStockIsNotEnough(){
        var laboratory = new Laboratory(List.of("A", "B", "C", "D"));

        laboratory.add("A", 1.0);

        assertThrows(IllegalArgumentException.class, () -> {
            laboratory.add("A", -2.0);
        });
    }
}
