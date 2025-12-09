package com.tdd.laboratory;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LaboratoryTests {

    @Test
    void initLaboratory() {
        var laboratory = new Laboratory();

        List<String> list = List.of("A", "B", "C", "D");

        assertEquals(list, laboratory.knownSubstances);
    }

    @Test
    void getExistingSubstanceQuantity(){
        var laboratory = new Laboratory();

        laboratory.stocks.put("A", 2.1);

        assertEquals(2.1, laboratory.getQuantity("A"));
    }

    @Test
    void getNonExistingSubstanceQuantity(){
        var laboratory = new Laboratory();

        assertThrows(IllegalArgumentException.class, () -> {
            laboratory.getQuantity("E");
        });
    }
}
