package com.tdd.laboratory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Laboratory {
    List<String> knownSubstances;
    HashMap<String, Double> stocks = new HashMap<>();
    Map<String, Map<String, Double>> reactions;

    public Laboratory(
            List<String> substances,
            Map<String, Map<String, Double>> initReactions
    ){
        knownSubstances = substances;

        initReactions.forEach((key, reaction) -> reaction.forEach((substance, quantity) -> {
            if(!knownSubstances.contains(substance)){
                throw new IllegalArgumentException("An unexisting substance is present in reactions list");
            }
        }));

        reactions = initReactions;
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
            double newStock = stocks.get(substance) + quantity;

            if(newStock == 0.0){
                stocks.remove(substance);
            } else {
                stocks.replace(substance, newStock);
            }
        }
    }
}
