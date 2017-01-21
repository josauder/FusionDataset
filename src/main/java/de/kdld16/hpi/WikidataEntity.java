package de.kdld16.hpi;

import de.kdld16.hpi.resolver.Mode;
import de.kdld16.hpi.resolver.RDFTypeTree;
import de.kdld16.hpi.util.*;
import de.kdld16.hpi.util.rdfdatatypecomparison.RDFDatatypeWrapper;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by jonathan on 23.11.16.
 */
public class WikidataEntity {

    public static Logger logger = LoggerFactory.getLogger(WikidataEntity.class);
    private static double smallLanguageThreshold;
    private static double minimumValueEquality;
    private Integer subject = null;

    private int n_facts=0;

    private RDFFactCollection acceptedFacts;
    private RDFFactCollection possibleConflicts;
    private CountingHashMap languageFactCounter;
    private HashMap<String,Double> languageCorrectCounter;
    private ArrayList<String> mostCommonLanguages;
    private DoFn.ProcessContext context;
    private int n_mostCommonLanguage =0;
    private int n_smallLanguages = 0;
    private int n_languages=0;
    private int n_resolvedConflicts=0;
    private ArrayList<String> smallLanguages;

    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/test/resources/application.properties"));
            smallLanguageThreshold = Double.parseDouble(properties.getProperty("smallLanguageThreshold"));
            minimumValueEquality = Double.parseDouble(properties.getProperty("minimumValueEquality"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WikidataEntity(Integer subject, Iterator<String> iter, DoFn.ProcessContext context) {
        this.subject=subject;
        this.context = context;
        acceptedFacts = new RDFFactCollection();
        possibleConflicts = new RDFFactCollection();
        languageFactCounter = new CountingHashMap();
        languageCorrectCounter = new HashMap<>();
        mostCommonLanguages = new ArrayList<>();

        while (iter.hasNext()) {
            addFact(new RDFFact(iter.next()));
        }
        determineMostCommonLanguage();
        determineSmallLanguages();
        resolveConflicts();

        logger.debug("Initialized with:" +
                " n_facts="+n_facts+
                " mostCommonLanguages="+ mostCommonLanguages +
                " n_languages="+n_languages+
                " n_mostCommonLanguage="+n_mostCommonLanguage+
                " n_smallLanguages="+n_smallLanguages);
    }


    /**
     * The heart Function of this entire Project - it determines how we resolve the conflicts in RDF properties
     */
    public void resolveConflicts()  {
        /**
         * At First we resolve the <rdf:type> property.
         * More information on how we resolve this can be found in the comment of RDFTypeTree.resolve(conflict)
         */
        RDFFactCollection conflict= possibleConflicts.newFilterByRdfProperty("<rdf:type>");
        if (conflict.size()>0) {
            new RDFTypeTree().resolve(conflict,this);
        }

        conflict= possibleConflicts.newFilterByRdfProperty("<dbo:populationTotal>");
        RDFDatatypeWrapper type = ClassifyProperties.getRdfDatatypeWrapper("<dbo:populationTotal>");
        Mode mode = new Mode(type);
        if (conflict.size()>0) {
            mode.resolve(conflict,this);
        }

        // We then resolve all other possible conflicts, until there are no more conflicts left
        /* while (possibleConflicts.size() > 0) {

            // We get all Triples with the same property, and the corresponding Mode.
            // Modes are different for different data types
            RDFFact f = possibleConflicts.getOne();
            conflict = possibleConflicts.newFilterByRdfProperty(f.getRdfProperty());
            RDFDatatypeWrapper type = ClassifyProperties.getRdfDatatypeWrapper(f.getRdfProperty());
            Mode mode = new Mode(type);

            // If the most commonly found item is very common (greater than a threshold), we accept it as truth
            ResolveResult result = mode.resolve(conflict);
            if (result.getConfidence()>minimumValueEquality) {
                assignConfidenceToFact(result,f.getRdfProperty());
                continue;
            }

             // If, after disregarding all languages for which this Wikidata Entity contains only few triples, the
             // most common value is very common (greater than a threshold), we accept that as truth. After this,
             // we continuously disregard languages with few triples.
            if (n_smallLanguages>0) {
                RDFFactCollection conflictWithoutSmallLangs = conflict.newFilterOutLanguages(smallLanguages);
                if (conflictWithoutSmallLangs.size()>0) {
                    result = mode.resolve(conflictWithoutSmallLangs);
                    if (result.getConfidence()>minimumValueEquality) {
                        assignConfidenceToFact(result,f.getRdfProperty());
                        continue;
                    }
                    else {
                        conflict=conflictWithoutSmallLangs;
                    }
                }
            }

             // If we have already resolved more than a set amount of conflicts, and we give languages that have
             // 'won' more conflicts so far a greater weight, and again the
             // most common value is very common (greater than a threshold), we accept that as truth.
            //TODO: Implement


             // We are out of options, and we choose to resolve in the most simple way by data-type:
             //Numeric Value -> Median (or value closest to mean)
             // Object/String values -> Most common value
            //TODO: Implement
            assignConfidenceToFact(result,f.getRdfProperty());

      //  logger.debug("Conflict in Subject :" + this.subject + "\t for property: " + f.getRdfProperty() + "\tresolving with: " + r.getClass().getSimpleName());

        }*/
    }

    public void assignConfidenceToFact(double confidence, RDFFact fact) {
        for (String lang : fact.getLanguages()) {
            Double d = languageCorrectCounter.containsKey(lang) ? languageCorrectCounter.get(lang) : new Double(0);
            languageCorrectCounter.put(lang, d + confidence);
        }
        emitFact(fact,confidence);
    }

    public void addFact(RDFFact fact) {
        this.n_facts++;
        if (!languageFactCounter.containsKey(fact.getLanguage())) {
            n_languages++;
            languageFactCounter.put(fact.getLanguage(),1);
        } else {
            languageFactCounter.put(fact.getLanguage(), languageFactCounter.get(fact.getLanguage())+1);
        }
        if (ClassifyProperties.rdfPropertyWrappers.containsKey(fact.getRdfProperty())) {
            possibleConflicts.addFact(fact);
        } else {
            emitFact(fact,1);
        }

    }

    public RDFFactCollection getAcceptedFacts() {
        return acceptedFacts;
    }


    public void determineSmallLanguages() {
        smallLanguages= new ArrayList<>();
        for (Map.Entry<String,Integer> e : languageFactCounter.entrySet()) {
            if (smallLanguageThreshold*e.getValue()< n_mostCommonLanguage) {
                smallLanguages.add(e.getKey());
                n_smallLanguages++;
            }
        }
    }
    public void determineMostCommonLanguage() {
        for (Map.Entry<String,Integer> e : languageFactCounter.entrySet()) {
           if (e.getValue()> n_mostCommonLanguage) {
               n_mostCommonLanguage=e.getValue();
               mostCommonLanguages.clear();
               mostCommonLanguages.add(e.getKey());
           } else if (e.getValue()== n_mostCommonLanguage) {
               mostCommonLanguages.add(e.getKey());
           }
        }
    }

    public void emitFact(RDFFact fact, double confidence) {
        context.output(DBPediaHelper.wikidataPrefix+subject+"> "+fact.toString() + " " + confidence);
    }


}
