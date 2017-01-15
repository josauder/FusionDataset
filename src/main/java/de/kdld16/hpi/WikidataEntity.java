package de.kdld16.hpi;

import de.kdld16.hpi.modes.AbstractMode;
import de.kdld16.hpi.modes.ResolveResult;
import de.kdld16.hpi.util.*;
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
    private CountingHashMap languageCorrectCounter;
    private ArrayList<String> mostCommonLanguages;

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

    public WikidataEntity(Integer subject, Iterator<String> iter) {
        this.subject=subject;
        acceptedFacts = new RDFFactCollection();
        possibleConflicts = new RDFFactCollection();
        languageFactCounter = new CountingHashMap();
        languageCorrectCounter = new CountingHashMap();
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
            acceptAsTrueFact(new RDFTypeTree().resolve(conflict),"<rdf:type>");
        }


        /**
         * We then resolve all other possible conflicts, until there are no more conflicts left
         */
        while (possibleConflicts.size() > 0) {

            /**
             * We get all Triples with the same property, and the corresponding Mode.
             * Modes are different for different data types
             */
            RDFFact f = possibleConflicts.getOne();
            conflict = possibleConflicts.newFilterByRdfProperty(f.getRdfProperty());
            AbstractMode mode = getMode(f.getRdfProperty());

            /**
             * If the most commonly found item is very common (greater than a threshold), we accept it as truth
             */
            ResolveResult result = mode.resolve(conflict);
            if (result.getConfidence()>minimumValueEquality) {
                acceptAsTrueFact(result,f.getRdfProperty());
                continue;
            }

            /**
             * If, after disregarding all languages for which this Wikidata Entity contains only few triples, the
             * most common value is very common (greater than a threshold), we accept that as truth. After this,
             * we continuously disregard languages with few triples.
             */
            if (n_smallLanguages>0) {
                RDFFactCollection conflictWithoutSmallLangs = conflict.newFilterOutLanguages(smallLanguages);
                if (conflictWithoutSmallLangs.size()>0) {
                    result = mode.resolve(conflictWithoutSmallLangs);
                    if (result.getConfidence()>minimumValueEquality) {
                        acceptAsTrueFact(result,f.getRdfProperty());
                        continue;
                    }
                    else {
                        conflict=conflictWithoutSmallLangs;
                    }
                }
            }

            /**
             * If we have already resolved more than a set amount of conflicts, and we give languages that have
             * 'won' more conflicts so far a greater weight, and again the
             * most common value is very common (greater than a threshold), we accept that as truth.
             */
            //TODO: Implement


            /**
             * We are out of options, and we choose to resolve in the most simple way by data-type:
             * Numeric Value -> Median (or value closest to mean)
             * Object/String values -> Most common value
             */
            //TODO: Implement
            acceptAsTrueFact(result,f.getRdfProperty());

      //  logger.debug("Conflict in Subject :" + this.subject + "\t for property: " + f.getRdfProperty() + "\tresolving with: " + r.getClass().getSimpleName());

        }
    }

    public void acceptAsTrueFact(ResolveResult result, String rdfProperty) {

        int max_lang_count=0;
        String max_lang=null;
        for (String lang : result.getLanguages()) {
            int currentCount=languageCorrectCounter.putOrIncrement(lang);
            if (currentCount>max_lang_count) {
                max_lang_count=currentCount;
                max_lang=lang;
            }
        }

        RDFFact fact = new RDFFact(rdfProperty,result.getValue(),max_lang);
        n_resolvedConflicts++;
        possibleConflicts.filterOutRdfProperty(fact.getRdfProperty());
        acceptedFacts.addFact(fact);
    }


    public static AbstractMode getMode(String property) {
        AbstractMode r;
        try {
            r = ClassifyProperties.acceptOnlyOne.get(property).newInstance();
            return r;
        } catch (IllegalAccessException | InstantiationException e) {
            logger.error(e.getStackTrace().toString());
            return null;
        }
    }

    public void addFact(RDFFact fact) {
        this.n_facts++;
        if (ClassifyProperties.acceptOnlyOne.containsKey(fact.getRdfProperty())) {
            possibleConflicts.addFact(fact);
        } else {
            acceptedFacts.addFact(fact);
        }
        if (!languageFactCounter.containsKey(fact.getLanguage())) {
            n_languages++;
            languageFactCounter.put(fact.getLanguage(),1);
        } else {
            languageFactCounter.put(fact.getLanguage(), languageFactCounter.get(fact.getLanguage())+1);
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



}
