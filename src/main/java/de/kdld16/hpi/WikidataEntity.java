package de.kdld16.hpi;

import de.kdld16.hpi.resolver.Resolver;
import de.kdld16.hpi.util.ClassifyProperties;
import de.kdld16.hpi.util.RDFFact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by jonathan on 23.11.16.
 */
public class WikidataEntity {
    public static Logger logger = LoggerFactory.getLogger(WikidataEntity.class);

    private String subject = null;
    private HashMap<String,HashSet<String>> acceptAllFacts;
    private HashMap<String,LinkedList<String>> acceptOnlyOneFacts;
    private int n_facts=0;

    public WikidataEntity(String subject) {
        this.subject=subject;
        this.acceptAllFacts = new HashMap<>();
        this.acceptOnlyOneFacts=new HashMap<>();
    };

    public void addFact(RDFFact fact) {
        
        if (!ClassifyProperties.acceptOnlyOne.containsKey(fact.getRdfProperty())) {
            if (!this.acceptAllFacts.containsKey(fact.getRdfProperty())) {
                HashSet<String> hs = new HashSet<>();
                hs.add(fact.getRdfObject());
                this.acceptAllFacts.put(fact.getRdfProperty(), hs);
            } else {
                this.acceptAllFacts.get(fact.getRdfProperty()).add(fact.getRdfObject());
            }
        } else {
            if (!this.acceptOnlyOneFacts.containsKey(fact.getRdfProperty())) {
                LinkedList<String> list = new LinkedList<>();
                list.add(fact.getRdfObject());
                acceptOnlyOneFacts.put(fact.getRdfProperty(),list);
            } else {
                acceptOnlyOneFacts.get(fact.getRdfProperty()).add(fact.getRdfObject());
            }
        }
        this.n_facts++;
    }

    public void resolveConflicts() {
        for (Map.Entry<String, LinkedList<String>> entry : acceptOnlyOneFacts.entrySet()) {

            String property = entry.getKey();

            int size = entry.getValue().size();
            if (size>1) {
                try {
                    Resolver resolver = ClassifyProperties.acceptOnlyOne.get(property).newInstance();

                    logger.debug("Conflict in Subject :" + this.subject + "\t for property: " + property + "\tresolving with: "+resolver.getClass().getSimpleName());
                    acceptOnlyOneFacts.put(property, resolver.resolve(property, entry.getValue()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Map.Entry<String,LinkedList<String>> entry2 : acceptOnlyOneFacts.entrySet()) {
             String rdfProperty = entry2.getKey();
            for (String rdfObject: entry2.getValue()) {
                b.append(this.subject);
                b.append(" ");
                b.append(rdfProperty);
                b.append(" ");
                b.append(rdfObject.trim());
                b.append("\n");
            }
        }
        for (Map.Entry<String, HashSet<String>> entry : acceptAllFacts.entrySet()) {
            String rdfProperty = entry.getKey();
            for (String rdfObject: entry.getValue()) {
                b.append(this.subject);
                b.append(" ");
                b.append(rdfProperty);
                b.append(" ");
                b.append(rdfObject.trim());
                b.append("\n");
            }
        }
        // Could be optimized! O(n)
        return b.toString().trim();
    }

}
