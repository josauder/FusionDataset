package de.kdld16.hpi.resolver;

import java.util.Collection;
import java.util.List;

/**
 * Created by jonathan on 08.12.16.
 */
public class ResolveResult {

    private String value;
    private int occurrence;
    private int outOf;
    private double confidence;
    private Collection<String> languages;

    public List<String> getOtherValues() {
        return otherValues;
    }

    public void setOtherValues(List<String> otherValues) {
        this.otherValues = otherValues;
    }

    private List<String> otherValues;


    public ResolveResult(String value, Collection<String> languages, int occurence, int outOf) {
        this.value = value;
        this.occurrence = occurence;
        this.outOf = outOf;
        this.languages=languages;
        this.confidence=occurence/(float)outOf;
    }

    public ResolveResult(String value, Collection<String> languages, double confidence) {
        this.value = value;
        this.languages=languages;
        this.confidence=confidence;
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

    public double getConfidence() {
        return confidence;
    }
}
