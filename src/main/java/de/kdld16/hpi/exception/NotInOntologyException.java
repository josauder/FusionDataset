package de.kdld16.hpi.exception;

/**
 * Created by jonathan on 22.11.16.
 */
public class NotInOntologyException extends FusionDatasetException {


    public NotInOntologyException(String faultyObject) {
        super("ERROR:" + faultyObject + "Is not contained in the Ontology tree provided: (isSubclassOf:null)");
    }
}
