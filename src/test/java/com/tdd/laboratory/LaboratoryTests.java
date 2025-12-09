package com.tdd.laboratory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LaboratoryTests {

    private Laboratory laboratory;

    @BeforeEach
    void setup(){
        laboratory = new Laboratory(
                List.of("A", "B", "C", "D"),
                Map.of(
                        "A", Map.of("B", 2.1, "C", 1.2),
                        "C", Map.of("B", 3.4, "D", 0.8),
                        "D", Map.of("A", 5.0, "B", 1.2)
                )
        );
        laboratory.stocks.clear();
    }

    @Test
    void initLaboratory() {
        List<String> list = List.of("A", "B", "C", "D");
        Map<String, Map<String, Double>> reactions = Map.of(
                "A", Map.of("B", 2.1, "C", 1.2),
                "C", Map.of("B", 3.4, "D", 0.8),
                "D", Map.of("A", 5.0, "B", 1.2)
        );

        assertEquals(list, laboratory.knownSubstances);
        assertEquals(reactions, laboratory.reactions);
    }

    @Test
    void initLaboratoryWithNonExistingSubstancesInReactions(){
        List<String> list = List.of("A", "B", "C", "D");
        Map<String, Map<String, Double>> reactions = Map.of(
                "A", Map.of("B", 2.1, "C", 1.2),
                "C", Map.of("E", 3.4, "D", 0.8),
                "D", Map.of("A", 5.0, "B", 1.2)
        );

        assertThrows(IllegalArgumentException.class, () -> {
            new Laboratory(list, reactions);
        });
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
