package com.tdd.laboratory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Laboratory {

    List<String> knownSubstances = new ArrayList<>();
    HashMap<String, Double> stocks = new HashMap<>();

    public Laboratory(){
        knownSubstances.add("A");
        knownSubstances.add("B");
        knownSubstances.add("C");
        knownSubstances.add("D");
    }

    Double getQuantity(String substance) {
        if(!knownSubstances.contains(substance)){
            throw new IllegalArgumentException("This substance does not exist");
        }

        return stocks.get(substance);
    }

    public static void main(String[] args){
        Laboratory laboratory = new Laboratory();
    }
}
