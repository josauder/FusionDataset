package de.kdld16.hpi.transforms;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jonathan on 11/14/16.
 */

public class RDFSubjectAsKey
        extends DoFn<String,KV<String, String>> {
    /**
     * Splits Line containing RDF-Triple into Key-Value Pair where Key is RDF Subject
     */

    static Logger logger = LoggerFactory.getLogger(RDFSubjectAsKey.class);

    @ProcessElement
    public void processElement(ProcessContext c) {
        String[] items = c.element().split(" ",2);
        if (items.length>1 && !items[0].isEmpty()) {
            c.output(KV.of(items[0], items[1]));
        }
    }
}