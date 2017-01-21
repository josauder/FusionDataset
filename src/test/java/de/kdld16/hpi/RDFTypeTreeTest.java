package de.kdld16.hpi;

import de.kdld16.hpi.exception.NotInOntologyException;
import de.kdld16.hpi.util.RDFFact;
import de.kdld16.hpi.util.RDFFactCollection;
import de.kdld16.hpi.resolver.RDFTypeTree;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jonathan on 31.12.16.
 */
public class RDFTypeTreeTest {

    @Test
    public void testClassLoad() throws NotInOntologyException {
        assertFalse(RDFTypeTree.isSubClassOf("<dbo:Archaea>","abc"));
        assertTrue(RDFTypeTree.isSubClassOf("<dbo:PopulatedPlace>","<dbo:Place>"));
        assertEquals(RDFTypeTree.getEquivalentClass("<dbo:Location>"),"<dbo:Place>");
    }



    public static ArrayList<RDFFact> produceTriples(String rdfObject, int n_languages) {
        ArrayList<RDFFact> list = new ArrayList<>();
        for (int i=0; i<n_languages; i++) {
            list.add(new RDFFact("<rdf:type>",rdfObject,i+""));
        }
        return list;
    }


    @Test
    public void testTreeResolve() {
        RDFFactCollection c = new RDFFactCollection();
        c.addAllFacts(produceTriples("<owl:Thing>",10));
        c.addAllFacts(produceTriples("<dbo:Place>",10));
        c.addAllFacts(produceTriples("<dbo:PopulatedPlace>",10));
        c.addAllFacts(produceTriples("<dbo:Settlement>",10));
        c.addAllFacts(produceTriples("<dbo:Region>",10));
        c.addAllFacts(produceTriples("<dbo:AdministrativeRegion>",10));
        c.addAllFacts(produceTriples("<dbo:Town>",10));
        c.addAllFacts(produceTriples("<dbo:GovernmentalAdministrativeRegion>",10));

        /*ResolveResult res = new RDFTypeTree().resolve(c);
        assertEquals(res.getValue(),"<dbo:PopulatedPlace>");
        assertEquals(res.getOutOf(),10);
        assertEquals(res.getOccurrence(),10);*/

        c = new RDFFactCollection();
        c.addAllFacts(produceTriples("<owl:Thing>",20));
        c.addAllFacts(produceTriples("<dbo:Place>",20));
        c.addAllFacts(produceTriples("<dbo:PopulatedPlace>",20));
        c.addAllFacts(produceTriples("<dbo:Settlement>",3));
        c.addAllFacts(produceTriples("<dbo:Region>",10));
        c.addAllFacts(produceTriples("<dbo:AdministrativeRegion>",10));
        c.addAllFacts(produceTriples("<dbo:Town>",3));
        c.addAllFacts(produceTriples("<dbo:GovernmentalAdministrativeRegion>",10));

        /*res = new RDFTypeTree().resolve(c);
        assertEquals(res.getValue(),"<dbo:GovernmentalAdministrativeRegion>");
        assertEquals(res.getOutOf(),20);
        assertEquals(res.getOccurrence(),10);*/

        c = new RDFFactCollection();
        c.addAllFacts(produceTriples("<owl:Thing>",20));
        c.addAllFacts(produceTriples("<dbo:Place>",20));
        c.addAllFacts(produceTriples("<dbo:PopulatedPlace>",20));
        c.addAllFacts(produceTriples("<dbo:Settlement>",10));
        c.addAllFacts(produceTriples("<dbo:Region>",3));
        c.addAllFacts(produceTriples("<dbo:AdministrativeRegion>",3));
        c.addAllFacts(produceTriples("<dbo:Town>",10));
        c.addAllFacts(produceTriples("<dbo:GovernmentalAdministrativeRegion>",3));

        /*res = new RDFTypeTree().resolve(c);
        assertEquals(res.getValue(),"<dbo:Town>");
        assertEquals(res.getOutOf(),20);
        assertEquals(res.getOccurrence(),10);*/
    }
}
