package de.kdld16.hpi.modes;

import de.kdld16.hpi.util.RDFFact;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by jonathan on 08.12.16.
 */
public class ModeResult {

    private String value;
    private int occurrence;
    private int outOf;
    private float confidence;
    private Collection<String> languages;

    public ModeResult(String value, Collection<String> languages, int occurence, int outOf) {
        this.value = value;
        this.occurrence = occurence;
        this.outOf = outOf;
        this.languages=languages;
        this.confidence=occurence/(float)outOf;
    }

    public String getValue() {
        return value;
    }
    public Collection<String> getLanguages() {
        return languages;
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
