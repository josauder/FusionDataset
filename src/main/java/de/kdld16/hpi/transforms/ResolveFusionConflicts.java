package de.kdld16.hpi.transforms;

import de.kdld16.hpi.WikidataEntity;
import de.kdld16.hpi.util.DBPediaHelper;
import de.kdld16.hpi.util.RDFFact;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Iterator;

/**
 * Created by jonathan on 11/14/16.
 */
public class ResolveFusionConflicts
        extends DoFn<KV<Integer,Iterable<String>>,String> {

    @ProcessElement
    public void processElement(ProcessContext c) {

        Integer rdfSubject = c.element().getKey();
        new WikidataEntity(rdfSubject,c.element().getValue().iterator(),c);
    }
}
