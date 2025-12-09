package com.tdd.laboratory;

import java.util.HashMap;
import java.util.List;

public class Laboratory {

    List<String> knownSubstances;
    HashMap<String, Double> stocks = new HashMap<>();

    public Laboratory(List<String> substances){
        knownSubstances = List.of("A", "B", "C", "D");
    }

    Double getQuantity(String substance) {
        if(!knownSubstances.contains(substance))
            throw new IllegalArgumentException("This substance does not exist");

        return stocks.get(substance);
    }
}
