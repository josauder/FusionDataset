package de.kdld16.hpi.modes;

import de.kdld16.hpi.util.RDFFact;

import java.util.ArrayList;

/**
 * Created by jonathan on 09.12.16.
 */
public abstract class AbstractMode {
    public ModeResult resolve(ArrayList<RDFFact> conflict) {
        return null;
    }
}
