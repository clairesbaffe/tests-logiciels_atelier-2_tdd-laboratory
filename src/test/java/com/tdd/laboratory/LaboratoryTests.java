package com.tdd.laboratory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LaboratoryTests {

    private Laboratory laboratory;

    @BeforeEach
    void setup(){
        laboratory = new Laboratory(List.of("A", "B", "C", "D"));
        laboratory.stocks.clear();
    }

    @Test
    void initLaboratory() {
        List<String> list = List.of("A", "B", "C", "D");

        assertEquals(list, laboratory.knownSubstances);
    }

    @Test
    void getExistingSubstanceQuantity(){
        laboratory.stocks.put("A", 2.1);

        assertEquals(2.1, laboratory.getQuantity("A"));
    }

    @Test
    void getNonExistingSubstanceQuantity(){
        assertThrows(IllegalArgumentException.class, () -> {
            laboratory.getQuantity("ZZZ");
        });
    }

    @Test
    void addToStock(){
        laboratory.add("A", 2.0);

        assertEquals(2.0, laboratory.getQuantity("A"));
    }

    @Test
    void addMultipleSubstances(){
        laboratory.add("A", 2.0);
        laboratory.add("B", 3.0);

        assertEquals(2.0, laboratory.getQuantity("A"));
        assertEquals(3.0, laboratory.getQuantity("B"));
    }

    @Test
    void addMultipleOfSameSubstance(){
        laboratory.add("A", 2.0);
        laboratory.add("A", 3.0);

        assertEquals(5.0, laboratory.getQuantity("A"));
    }

    @Test
    void addNonExistingSubstance(){
        assertThrows(IllegalArgumentException.class, () -> {
            laboratory.add("E", 2.0);
        });
    }

    @Test
    void addZeroQuantity(){
        assertThrows(IllegalArgumentException.class, () -> {
            laboratory.add("A", 0.0);
        });
    }

    @Test
    void addNegativeValueWhileStockIsNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            laboratory.add("A", -2.0);
        });
    }

    @Test
    void addNegativeValueWhileStockIsEnough(){
        laboratory.add("A", 2.0);
        laboratory.add("A", -1.0);

        assertEquals(1.0, laboratory.getQuantity("A"));
    }

    @Test
    void addNegativeValueWhileStockIsNotEnough(){
        laboratory.add("A", 1.0);

        assertThrows(IllegalArgumentException.class, () -> {
            laboratory.add("A", -2.0);
        });
    }

    @Test
    void removeAllStock(){
        laboratory.add("A", 2.0);
        laboratory.add("A", -2.0);

        assertNull(laboratory.getQuantity("A"));
    }
}
