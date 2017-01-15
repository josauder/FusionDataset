package de.kdld16.hpi.modes;

import de.kdld16.hpi.util.RDFFactCollection;

/**
 * Created by jonathan on 15.01.17.
 */
public interface Resolver {
    public ResolveResult resolve(RDFFactCollection conflict);
}
