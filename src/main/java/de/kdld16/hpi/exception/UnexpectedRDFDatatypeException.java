package de.kdld16.hpi.exception;

/**
 * Created by jonathan on 22.11.16.
 */
public class UnexpectedRDFDatatypeException extends FusionDatasetException {


    public UnexpectedRDFDatatypeException(String faultySubject) {
        super("ERROR:" + faultySubject + " no RDFDatatypeWrapper found");
    }
}
