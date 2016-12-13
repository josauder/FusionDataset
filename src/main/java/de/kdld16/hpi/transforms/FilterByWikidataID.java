package de.kdld16.hpi.transforms;

import de.kdld16.hpi.exception.NotWikidataObjectException;
import de.kdld16.hpi.util.DBPediaHelper;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

/**
 * Created by jonathan on 22.11.16.
 */
public class FilterByWikidataID<V> extends DoFn<KV<String,V>,KV<String,V>> {


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
        if (subject.contains(DBPediaHelper.wikidataPrefix)) {
            int id = Integer.parseInt(subject.replace(DBPediaHelper.wikidataPrefix,"").replace(DBPediaHelper.wikidataPostfix,""));
            if (lower <= id && id <= upper) {
                c.output(c.element());
            }
        } else {
         //   logger.debug((new NotWikidataObjectException(subject)).getStackTrace().toString());
        }

    }
}
