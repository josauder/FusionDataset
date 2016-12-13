package de.kdld16.hpi.mode;

import de.kdld16.hpi.modes.Mode;
import de.kdld16.hpi.modes.ModeResult;
import de.kdld16.hpi.util.RDFFact;
import de.kdld16.hpi.util.RDFFactCollection;
import org.junit.Test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jonathan on 24.11.16.
 */
public class ModeResolverTest {

    @Test
    public void testResolver()  {
        RDFFactCollection in = new RDFFactCollection();
        ModeResult out;

        RDFFact a = new RDFFact("<> <> <a>");
        RDFFact b = new RDFFact("<> <> <b>");

        assertTrue(in.size()==0);
        in.addFact(a);

        assertTrue(in.size()==1);
        for (int i=0; i<9; i++) {
            in.addFact(a);
        }
        Mode mr = new Mode();
        out=mr.getMode(in);
        assertTrue(in.size()==10);
        assertTrue(out.getFact().getRdfObject().equals("<a>"));

        for (int i=0; i<11; i++) {
            in.addFact(b);
        }
        out=mr.getMode(in);
        assertTrue(in.size()==21);
        assertTrue(out.getFact().getRdfObject().equals("<b>"));

        in.addFact(a);
        in.addFact(a);
        out=mr.getMode(in);
        assertTrue(in.size()==23);
        assertTrue(out.getFact().getRdfObject().equals("<a>"));
    }
}
