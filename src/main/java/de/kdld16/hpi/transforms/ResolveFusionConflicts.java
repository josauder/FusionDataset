package de.kdld16.hpi.transforms;

import de.kdld16.hpi.WikidataEntity;
import de.kdld16.hpi.util.RDFFact;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Iterator;

/**
 * Created by jonathan on 11/14/16.
 */
public class ResolveFusionConflicts
        extends DoFn<KV<String,Iterable<String>>,String> {

    @ProcessElement
    public void processElement(ProcessContext c) {

        String rdfSubject = c.element().getKey();
        WikidataEntity entity = new WikidataEntity(rdfSubject,c.element().getValue().iterator());
        for (RDFFact fact : entity.getAcceptedFacts())  {
            c.output(rdfSubject+" "+fact.getRdfProperty()+" "+fact.getRdfObject()+" . @"+fact.getLanguage());
        }
    }
}
