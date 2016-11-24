package de.kdld16.hpi.resolver;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jonathan on 24.11.16.
 */
public class ModeResolverTest {

    @Test
    public void testResolver()  {
        LinkedList<String> in = new LinkedList<>();
        LinkedList<String> out;
        for (int i=0; i<10; i++) {
            in.add("<a>");
        }
        ModeResolver mr = new ModeResolver();
        out=mr.resolve("",in);
        assertTrue(out.size()==1);
        assertTrue(out.get(0).equals("<a>"));

        for (int i=0; i<11; i++) {
            in.add("<b>");
        }
        out=mr.resolve("",in);
        assertTrue(out.size()==1);
        assertTrue(out.get(0).equals("<b>"));

        in.add("<a>");
        in.add("<c>");
        out=mr.resolve("",in);
        assertTrue(out.size()==1);
        assertFalse(out.get(0).equals("<c>"));
    }
}
