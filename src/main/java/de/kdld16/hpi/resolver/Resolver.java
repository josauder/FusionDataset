package de.kdld16.hpi.resolver;

import de.kdld16.hpi.WikidataEntity;
import de.kdld16.hpi.util.RDFFactCollection;

/**
 * Created by jonathan on 15.01.17.
 */
public interface Resolver {

    public void resolve(RDFFactCollection conflict, WikidataEntity entity);
}
