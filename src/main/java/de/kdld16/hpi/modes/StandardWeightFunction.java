package de.kdld16.hpi.modes;

import java.util.ArrayList;

/**
 * Created by jonathan on 13.01.17.
 */
public class StandardWeightFunction implements WeightFunction {

    @Override
    public double getWeight(ArrayList<String> languages) {
        return languages.size();
    }
}
