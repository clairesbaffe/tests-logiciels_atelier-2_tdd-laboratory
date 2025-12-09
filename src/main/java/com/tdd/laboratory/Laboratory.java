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

        Double currentStock = stocks.get(substance);

        if(quantity == 0)
            throw new IllegalArgumentException("Cannot add nothing");
        else if(quantity < 0 && (currentStock == null || currentStock + quantity < 0))
            throw new IllegalArgumentException("Cannot remove more than existing");

        if(currentStock == null){
            stocks.put(substance, quantity);
        } else {
            Double newStock = stocks.get(substance) + quantity;

            if(newStock == 0.0){
                stocks.remove(substance);
            } else {
                stocks.replace(substance, newStock);
            }
        }
    }
}
