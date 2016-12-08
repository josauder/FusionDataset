package de.kdld16.hpi.modes;

import de.kdld16.hpi.util.RDFFact;

/**
 * Created by jonathan on 08.12.16.
 */
public class ModeResult {

    private RDFFact mostCommon;
    private int occurence;
    private int outOf;

    public ModeResult(RDFFact mostCommon, int occurence, int outOf) {
        this.mostCommon = mostCommon;
        this.occurence = occurence;
        this.outOf = outOf;
    }

    public RDFFact getMostCommon() {
        return mostCommon;
    }

    public int getOccurence() {
        return occurence;
    }

    public int getOutOf() {
        return outOf;
    }
}
