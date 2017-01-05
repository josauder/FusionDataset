package de.kdld16.hpi.transforms;

import de.kdld16.hpi.util.DBPediaHelper;
import de.kdld16.hpi.util.RDFFact;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollectionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jonathan on 11/14/16.
 */

public class LanguageTagAdder
        extends DoFn<String,KV<Integer,String>> {
    /**
     * Splits Line containing RDF-Triple into Key-Value Pair where Key is RDF Subject
     */

    static Logger logger = LoggerFactory.getLogger(LanguageTagAdder.class);

    private String language;
    public LanguageTagAdder(String language) {
        this.language=language;
    }


    @ProcessElement
    public void processElement(ProcessContext c) {
        String[] items = c.element().split(" ",2);
        if (items.length>1 && !items[0].isEmpty()) {
            if (items[0].contains(DBPediaHelper.wikidataPrefix)) {
                int id = Integer.parseInt(items[0].replace(DBPediaHelper.wikidataPrefix,"").replace(DBPediaHelper.wikidataPostfix,""));
                c.output(KV.of(id, language+" "+DBPediaHelper.replaceNamespace(items[1].substring(0,items[1].length()-1).trim())));
            }

        }
        else {
            logger.debug("Bad Triple:"+c.element());
        }
    }

}