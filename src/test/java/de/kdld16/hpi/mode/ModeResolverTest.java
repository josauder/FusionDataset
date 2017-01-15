package de.kdld16.hpi.mode;

import de.kdld16.hpi.modes.*;
import de.kdld16.hpi.util.RDFFact;
import de.kdld16.hpi.util.RDFFactCollection;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jonathan on 24.11.16.
 */
public class ModeResolverTest {

    static Logger logger = LoggerFactory.getLogger(ModeResolverTest.class);

    public void testModeGeneric(String[] valA, String[] valB,AbstractMode mr)  {
        RDFFactCollection in = new RDFFactCollection();
        ResolveResult out;
        int a=0;
        int b=0;

        assertTrue(in.size()==0);
        in.addFact(new RDFFact("@"+in.size()+" <> " + valA[a++]));

        assertTrue(in.size()==1);
        for (int i=0; i<9; i++) {
            in.addFact(new RDFFact("@"+in.size()+" <> " + valA[a++]));
        }
        out=mr.resolve(in);
        assertEquals(in.size(),10);
        assertEquals(out.getValue(),valA[0]);
        assertEquals(out.getConfidence(),1,0);
        assertEquals(out.getLanguages().size(),in.size());

        for (int i=0; i<11; i++) {
            in.addFact(new RDFFact("@"+in.size()+" <> " + valB[b++]));
        }
        out=mr.resolve(in);
        assertEquals(in.size(),21);
        assertEquals(out.getValue(),valB[0]);
        assertEquals(out.getLanguages().size(),11);
        in.addFact(new RDFFact("@"+in.size()+" <> " + valA[a++]));
        in.addFact(new RDFFact("@"+in.size()+" <> " + valA[a++]));

        out=mr.resolve(in);
        assertEquals(in.size(),23);
        assertEquals(out.getValue(),valA[0]);
        assertEquals(out.getLanguages().size(),12);
    }
    
    @Test
    public void testMode() {
        String[] a = {"<a>","<a>","<a>","<a>","<a>","<a>","<a>","<a>","<a>","<a>","<a>","<a>","<a>","<a>"};
        String[] b = {"<b>","<b>","<b>","<b>","<b>","<b>","<b>","<b>","<b>","<b>","<b>","<b>","<b>"};
        testModeGeneric(a,b, new Mode());
    }

    @Test
    public void testNumericMode() {
        Properties properties = new Properties();
        double tolerance= Double.MAX_VALUE;
        try {
            properties.load(new FileInputStream("src/test/resources/application.properties"));
            tolerance = Double.parseDouble(properties.getProperty("resolver.FloatModeResolver.tolerance"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        String[] a = new String[13];
        String[] b = new String[12];
        Random r = new Random();
        int i=1;
        a[0]="1000.0^^<xsd:double>";
        b[0]="10000.0^^<xsd:double>";
        for (; i<12; i++) {
            double rand =1+((r.nextDouble()*2)-1)*tolerance;
            a[i]=(1000*rand)+"^^<xsd:double>";
            b[i]=(10000*rand)+"^^<xsd:double>";
        }
        a[i]=(1000+(1000*((r.nextDouble()*2)-1)*tolerance))+"^^<xsd:double>";
        testModeGeneric(a,b, new DoubleMode());
    }
}
