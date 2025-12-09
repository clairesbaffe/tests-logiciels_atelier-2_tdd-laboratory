package com.tdd.laboratory;

import java.util.HashMap;
import java.util.List;

public class Laboratory {
    List<String> knownSubstances;
    HashMap<String, Double> stocks = new HashMap<>();

    public Laboratory(List<String> substances){
        knownSubstances = substances;
    }

    Double getQuantity(String substance) {
        if(!knownSubstances.contains(substance))
            throw new IllegalArgumentException("This substance does not exist");

        return stocks.get(substance);
    }

    void add(String substance, Double quantity){
        if(!knownSubstances.contains(substance))
            throw new IllegalArgumentException("This substance does not exist");

        if(stocks.get(substance) != null){
            Double newStock = stocks.get(substance) + quantity;
            stocks.replace(substance, newStock);
        } else {
            stocks.put(substance, quantity);
        }
    }
}
