package de.kdld16.hpi.resolver.simple;

import de.kdld16.hpi.resolver.ResolveResult;
import de.kdld16.hpi.resolver.Resolver;
import de.kdld16.hpi.util.ClassifyProperties;
import de.kdld16.hpi.util.RDFFactCollection;
import de.kdld16.hpi.util.rdfdatatypecomparison.RDFDatatypeWrapper;

/**
 * Created by jonathan on 15.01.17.
 */
public class SimpleDatatypeResolver implements Resolver {

    @Override
    public ResolveResult resolve(RDFFactCollection conflict) {
        Resolver r  = ClassifyProperties.getResolver(conflict.getOne());
        return r.resolve(conflict);
    }
}
