package de.kdld16.hpi.transforms;

import de.kdld16.hpi.exception.NotWikidataObjectException;
import de.kdld16.hpi.util.WikidataHelper;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jonathan on 22.11.16.
 */
public class FilterByWikidataID<V> extends DoFn<KV<String,V>,KV<String,V>> {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    private int lower =0;
    private int upper = Integer.MAX_VALUE;

    public FilterByWikidataID(int upper) {
        this.upper=upper;
    }
    public FilterByWikidataID(int lower, int upper) {
        this.lower=lower;
        this.upper=upper;
    }

    @ProcessElement
    public void processElement(ProcessContext c) {
        String subject = c.element().getKey();
        if (subject.contains(WikidataHelper.wikidataPrefix)) {
            int id = Integer.parseInt(subject.replace(WikidataHelper.wikidataPrefix,"").replace(WikidataHelper.wikidataPostfix,""));
            if (lower <= id && id <= upper) {
                c.output(c.element());
            }
        } else {
            logger.error((new NotWikidataObjectException(subject)).getStackTrace().toString());
        };

    }
}
