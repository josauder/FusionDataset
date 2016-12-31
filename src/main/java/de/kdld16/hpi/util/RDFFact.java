package de.kdld16.hpi.util;

import org.apache.jena.vocabulary.RDF;

import java.io.Serializable;

/**
 * Created by jonathan on 08.12.16.
 */
public class RDFFact implements Serializable, Comparable<RDFFact>{

    private String language;
    private String rdfProperty;

    public String getRdfObject() {
        return rdfObject;
    }

    public String getRdfProperty() {
        return rdfProperty;
    }


    public String getLanguage() {
        return language;
    }

    private String rdfObject;

    public RDFFact(String line) {
        String[] arr = line.split(" ",3);
        language=arr[0];
        rdfProperty=arr[1];
        rdfObject=arr[2];
    }
    public RDFFact(String rdfProperty, String rdfObject, String language) {
        this.language=language;
        this.rdfObject=rdfObject;
        this.rdfProperty=rdfProperty;
    }

    public int compareTo(RDFFact other) {
        int p=  this.rdfProperty.compareTo(other.getRdfProperty());
        return p==0 ? this.getLanguage().compareTo(other.getLanguage()) : p;
    }

}
