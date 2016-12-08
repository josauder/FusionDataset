package de.kdld16.hpi.transforms;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollectionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jonathan on 11/14/16.
 */

public class LanguageTagAdder
        extends DoFn<String,String> {
    /**
     * Splits Line containing RDF-Triple into Key-Value Pair where Key is RDF Subject
     */

    static Logger logger = LoggerFactory.getLogger(LanguageTagAdder.class);

    private String language;
    public LanguageTagAdder(String language) {
        logger.debug("New LanguageTagAdder Initialized with Language: "+language);
        this.language=language;
    }

    @ProcessElement
    public void processElement(ProcessContext c) {
        c.output(c.element()+language);
    }


}