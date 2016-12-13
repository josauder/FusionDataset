package de.kdld16.hpi.modes;

import de.kdld16.hpi.util.RDFFact;

/**
 * Created by jonathan on 08.12.16.
 */
public class ModeResult {

    private RDFFact fact;
    private int occurrence;
    private int outOf;
    private float confidence;

    public ModeResult(RDFFact fact, int occurence, int outOf) {
        this.fact = fact;
        this.occurrence = occurence;
        this.outOf = outOf;
        this.confidence=occurence/(float)outOf;
    }

    public RDFFact getFact() {
        return fact;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public int getOutOf() {
        return outOf;
    }

    public float getConfidence() {
        return confidence;
    }
}
