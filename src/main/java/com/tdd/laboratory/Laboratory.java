package com.tdd.laboratory;

import java.util.*;

public class Laboratory {
    List<String> knownSubstances;
    HashMap<String, Double> stocks = new HashMap<>();
    Map<String, Map<String, Double>> reactions;

    public Laboratory(
            List<String> substances,
            Map<String, Map<String, Double>> initReactions
    ){
        knownSubstances = substances;

        initReactions.values().stream()
                .flatMap(reaction -> reaction.keySet().stream())
                .forEach(this::checkSubstanceValidity);

        reactions = initReactions;
    }

    Double getQuantity(String substance) {
        checkSubstanceAndProductValidity(substance);
        return stocks.get(substance);
    }

    void add(String substance, Double quantity){
        checkSubstanceAndProductValidity(substance);
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

    void make(String product, Double quantity){
        checkProductValidity(product);

        Map<String, Double> reaction = reactions.get(product);

        reaction.forEach((substance, neededQuantityPerUnit) -> {
            double totalQuantityNeeded = quantity * neededQuantityPerUnit;
            add(substance, -totalQuantityNeeded);
        });

        add(product, quantity);
    }

    void checkSubstanceValidity(String substance){
        if(!knownSubstances.contains(substance))
            throw new IllegalArgumentException("This substance does not exist");
    }

    void checkProductValidity(String product){
        if(!reactions.containsKey(product))
            throw new IllegalArgumentException("This product does not exist");
    }

    void checkSubstanceAndProductValidity(String substance){
        if(!knownSubstances.contains(substance) && !reactions.containsKey(substance)){
            throw new IllegalArgumentException("This substance or product does not exist");
        }
    }
}
