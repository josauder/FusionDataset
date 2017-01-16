package de.kdld16.hpi.resolver;

import java.util.ArrayList;

/**
 * Created by jonathan on 13.01.17.
 */
public interface WeightFunction {

    public double getWeight(ArrayList<String> languages);
}
