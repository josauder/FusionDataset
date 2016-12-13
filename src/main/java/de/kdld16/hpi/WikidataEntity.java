package de.kdld16.hpi;

import de.kdld16.hpi.modes.AbstractMode;
import de.kdld16.hpi.modes.ModeResult;
import de.kdld16.hpi.util.ClassifyProperties;
import de.kdld16.hpi.util.RDFFact;
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

    private ArrayList<RDFFact> acceptedFacts;
    private ArrayList<RDFFact> possibleConflicts;
    private HashMap<String,Integer> languageFactCounter;
    private HashMap<String,Integer> languageCorrectCounter;
    private ArrayList<String> mostCommonLanguages;

    private int n_mostCommonLanguage =0;
    private int n_smallLanguages = 0;
    private int n_languages=0;
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
        acceptedFacts = new ArrayList<>();
        possibleConflicts = new ArrayList<>();
        languageFactCounter = new HashMap<>();
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

    public void resolveConflicts()  {
        while (possibleConflicts.size() > 0) {
        //Calculate Mode Object for each fact
            RDFFact f = possibleConflicts.get(0);
            ArrayList<RDFFact> conflict = filterByRdfProperty(possibleConflicts, f.getRdfProperty());
            AbstractMode mode = getMode(f.getRdfProperty());

            ModeResult result = mode.resolve(conflict);

            if (result.getConfidence()>minimumValueEquality) {
                acceptAsTrueFact(result.getMostCommon());
                possibleConflicts = filterOutRdfProperty(possibleConflicts, f.getRdfProperty());
                continue;
            }

            if (n_smallLanguages>0) {
                ArrayList<RDFFact> conflictWithoutSmallLangs = filterOutLanguages(conflict,smallLanguages);
                if (conflictWithoutSmallLangs.size()>0) {
                    result = mode.resolve(conflictWithoutSmallLangs);
                    if (result.getConfidence()>minimumValueEquality) {
                        acceptedFacts.add(result.getMostCommon());
                        possibleConflicts = filterOutRdfProperty(possibleConflicts, f.getRdfProperty());
                        continue;
                    }
                    else {
                        conflict=conflictWithoutSmallLangs;
                    }
                }
            }
            possibleConflicts=filterOutRdfProperty(possibleConflicts,f.getRdfProperty());
            acceptAsTrueFact(result.getMostCommon());

      //  logger.debug("Conflict in Subject :" + this.subject + "\t for property: " + f.getRdfProperty() + "\tresolving with: " + r.getClass().getSimpleName());

        }
    }

    public void acceptAsTrueFact(RDFFact fact) {
        acceptedFacts.add(fact);
        String language = fact.getLanguage();
        if (languageCorrectCounter.containsKey(language)) {
            languageCorrectCounter.put(language,languageCorrectCounter.get(language)+1);
        }
        languageCorrectCounter.put(language,1);
    }

    public static AbstractMode getMode(String property) {
        AbstractMode r;
        try {
            r = ClassifyProperties.acceptOnlyOne.get(property).newInstance();
            return r;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addFact(RDFFact fact) {
        this.n_facts++;
        if (ClassifyProperties.acceptOnlyOne.containsKey(fact.getRdfProperty())) {
            possibleConflicts.add(fact);
        } else {
            acceptedFacts.add(fact);
        }

        if (!languageFactCounter.containsKey(fact.getLanguage())) {
            n_languages++;
            languageFactCounter.put(fact.getLanguage(),1);
        } else {
            languageFactCounter.put(fact.getLanguage(), languageFactCounter.get(fact.getLanguage())+1);
        }
    }

    public ArrayList<RDFFact> getAcceptedFacts() {
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


    public static ArrayList<RDFFact> filterByRdfProperty(ArrayList<RDFFact> in, String property) {
        ArrayList<RDFFact> out = new ArrayList<>();
        for (RDFFact fact: in) {
            if (fact.getRdfProperty().equals(property)) {
                out.add(fact);
            }
        }
        return out;
    }

    public static ArrayList<RDFFact> filterOutRdfProperty(ArrayList<RDFFact> in, String property) {
        ArrayList<RDFFact> out = new ArrayList<>();
        for (RDFFact fact: in) {
            if (!fact.getRdfProperty().equals(property)) {
                out.add(fact);
            }
        }
        return out;
    }
    public static ArrayList<RDFFact> filterByLanguage(ArrayList<RDFFact> in, String language) {
        ArrayList<RDFFact> out = new ArrayList<>();
        for (RDFFact fact: in) {
            if (fact.getLanguage().equals(language)) {
                out.add(fact);
            }
        }
        return out;
    }

    public static ArrayList<RDFFact> filterByLanguages(ArrayList<RDFFact> in, ArrayList<String> languages) {
        ArrayList<RDFFact> out = new ArrayList<>();
        for (RDFFact fact: in) {
            for (String lang: languages) {
                if (fact.getLanguage().equals(lang)) {
                    out.add(fact);
                }
            }
        }
        return out;
    }
    public static ArrayList<RDFFact> filterOutLanguage(ArrayList<RDFFact> in, String language) {
        ArrayList<RDFFact> out = new ArrayList<>();
        for (RDFFact fact: in) {
            if (!fact.getLanguage().equals(language)) {
                out.add(fact);
            }
        }
        return out;
    }

    public static ArrayList<RDFFact> filterOutLanguages(ArrayList<RDFFact> in, ArrayList<String> languages) {
        ArrayList<RDFFact> out = new ArrayList<>();
        for (RDFFact fact: in) {
            boolean ok = true;
            for (String lang: languages) {
                if (fact.getLanguage().equals(lang)) {
                    ok = false;
                }
            }
            if (ok) {
                out.add(fact);
            }
        }
        return out;
    }
}
