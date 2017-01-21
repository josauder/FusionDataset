package de.kdld16.hpi.resolver.simple;

import de.kdld16.hpi.WikidataEntity;
import de.kdld16.hpi.resolver.Mode;
import de.kdld16.hpi.resolver.Resolver;
import de.kdld16.hpi.util.ClassifyProperties;
import de.kdld16.hpi.util.RDFFactCollection;
import de.kdld16.hpi.util.rdfdatatypecomparison.RDFDatatypeWrapper;

/**
 * Created by jonathan on 15.01.17.
 */
public class SimpleDatatypeResolver implements Resolver {

    @Override
    public void resolve(RDFFactCollection conflict, WikidataEntity entity) {
        RDFDatatypeWrapper r  = ClassifyProperties.getRdfDatatypeWrapper(conflict.getOne());
        //return
                new Mode(r).resolve(conflict, entity);
    }
}
