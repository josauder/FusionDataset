package de.kdld16.hpi;

import de.kdld16.hpi.util.RDFTypeTree;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by jonathan on 31.12.16.
 */
public class RDFTypeTreeTest {

    @Test
    public void testClassLoad() {
        assertFalse(RDFTypeTree.isSubClassOf("<dbo:Archaea>","abc"));
    }
}
