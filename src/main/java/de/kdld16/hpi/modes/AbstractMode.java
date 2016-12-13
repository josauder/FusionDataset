package de.kdld16.hpi.modes;

import de.kdld16.hpi.util.RDFFact;
import de.kdld16.hpi.util.RDFFactCollection;

import java.util.ArrayList;

/**
 * Created by jonathan on 09.12.16.
 */
public abstract class AbstractMode {
    public ModeResult getMode(RDFFactCollection conflict) {
        return null;
    }
}
