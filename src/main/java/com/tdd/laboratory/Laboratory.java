package com.tdd.laboratory;

import java.util.HashMap;
import java.util.List;

public class Laboratory {

    List<String> knownSubstances;
    HashMap<String, Double> stocks = new HashMap<>();

    Double getQuantity(String substance) {
        return 0.0;
    }

    public static void main(String[] args){
        Laboratory laboratory = new Laboratory();
    }
}
