package de.kdld16.hpi.exception;

/**
 * Created by jonathan on 22.11.16.
 */
public class NotWikidataObjectException extends FusionDatasetException {


    public NotWikidataObjectException(String faultySubject) {
        super("ERROR:" + faultySubject + "Is not a valid ");
    }
}
