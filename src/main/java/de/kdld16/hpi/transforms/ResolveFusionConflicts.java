package de.kdld16.hpi.transforms;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Iterator;

/**
 * Created by jonathan on 11/14/16.
 */
public class ResolveFusionConflicts
        extends DoFn<KV<String,Iterable<String>>,String> {
    /**
     * Dummy class which later will contain the actual solution to our problem
     *      This means:
     *          - Find Type Conflicts by resolving instance types transitively
     *          - Find Conflicts by grouping properties into groups (acceptAll vs. acceptOne)
     *          - Further group acceptOne into different solutions for resolving conflict:
     *                  - Average
     *                  - Majority Voting
     *                  - Clustering
     *                  - Train Classifiers
     * We can make more pipeline steps after we have split the property- and type
     * clashes into further groups
     */

    @ProcessElement
    public void processElement(ProcessContext c) {

        /**
         * Right now just a dummy class, which counts the number of Triples found for this Subjet
         */
        StringBuilder b = new StringBuilder(c.element().getKey()+" : ");
        Iterator<String> iter = c.element().getValue().iterator();
        int i =0;
        while (iter.hasNext()) {
        //    b.append(iter.next());
            iter.next();
            i++;
        }
        b.append(String.valueOf(i));
        c.output(b.toString());

    }
}
