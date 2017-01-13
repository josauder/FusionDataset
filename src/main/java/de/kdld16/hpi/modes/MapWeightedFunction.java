package de.kdld16.hpi.modes;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jonathan on 13.01.17.
 */
public class MapWeightedFunction implements WeightFunction {

    Map<String,Double> map;
    public MapWeightedFunction(Map<String,Double> map) {
        this.map=map;
    }

    @Override
    public double getWeight(ArrayList<String> languages) {
        double sum = 0;
        for (String l: languages) {
            sum+= map.get(l);
        }
        return sum;
    }
}
