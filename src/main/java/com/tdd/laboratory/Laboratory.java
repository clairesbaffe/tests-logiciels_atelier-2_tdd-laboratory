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

        Set<String> reactionNames = initReactions.keySet();

        initReactions.values().stream()
                .flatMap(reaction -> reaction.keySet().stream())
                .forEach((reaction -> checkSubstanceAndProductValidityWithReactionNamesGiven(reaction, reactionNames)));

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
        if(quantity <= 0)
            throw new IllegalArgumentException("Cannot make negative quantity");

        Map<String, Double> reaction = reactions.get(product);

        double maxBasedOnSubstances = reaction.entrySet().stream()
                .mapToDouble(entry -> {
                    String substance = entry.getKey();
                    Double neededQuantityPerUnit = entry.getValue();
                    Double availableQuantity = getQuantity(substance);

                    if(availableQuantity == null)
                        throw new IllegalArgumentException("At least one substance is missing");

                    double totalQuantityNeeded = quantity * neededQuantityPerUnit;

                    if(totalQuantityNeeded > availableQuantity)
                        return availableQuantity / neededQuantityPerUnit;
                    else
                        return quantity;
                })
                .min()
                .orElse(quantity);

        reaction.forEach((substance, neededQuantityPerUnit) -> {
            double totalQuantityNeeded = maxBasedOnSubstances * neededQuantityPerUnit;
            add(substance, -totalQuantityNeeded);
        });

        add(product, maxBasedOnSubstances);
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

    void checkSubstanceAndProductValidityWithReactionNamesGiven(
            String substance,
            Set<String> reactionNames
    ){
        if(!knownSubstances.contains(substance) && !reactionNames.contains(substance)){
            throw new IllegalArgumentException("This substance or product does not exist");
        }
    }
}
