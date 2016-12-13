package de.kdld16.hpi.util;

import org.apache.jena.vocabulary.RDF;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by jonathan on 13.12.16.
 */
public class RDFFactCollection {
    private ArrayList<RDFFact> facts;
    private HashSet<String> languages;
    
    public RDFFactCollection(ArrayList<RDFFact> facts) {
        this.facts=facts;
    }
    public RDFFactCollection() {
        this.languages=new HashSet<>();
        this.facts=new ArrayList<>();
    }

    public RDFFactCollection(ArrayList<RDFFact> facts, HashSet<String> languages) {
        this.languages=languages;
        this.facts=facts;
    }

    public void addFact(RDFFact fact) {
        this.languages.add(fact.getLanguage());
        this.facts.add(fact);
    }
    public int size() {
        return this.facts.size();
    }
    public RDFFact getOne() {
        return this.facts.get(0);
    }

    public ArrayList<RDFFact> asList() {
        return this.facts;
    }

    public RDFFactCollection newFilterByRdfProperty(String property) {
        ArrayList<RDFFact> out = new ArrayList<>();
        HashSet<String> outlangs = new HashSet<>();
        for (RDFFact fact: facts) {
            if (fact.getRdfProperty().equals(property)) {
                out.add(fact);
                outlangs.add(fact.getLanguage());
            }
        }
        return new RDFFactCollection(out);
    }

    public RDFFactCollection newFilterOutRdfProperty(String property) {
        ArrayList<RDFFact> out = new ArrayList<>();
        HashSet<String> outlangs = new HashSet<>();
        for (RDFFact fact: facts) {
            if (!fact.getRdfProperty().equals(property)) {
                out.add(fact);
                outlangs.add(fact.getLanguage());
            }
        }
        return new RDFFactCollection(out);
    }
    public RDFFactCollection newFilterByLanguage(String language) {
        ArrayList<RDFFact> out = new ArrayList<>();
        HashSet<String> outlangs = new HashSet<>();
        for (RDFFact fact: facts) {
            if (fact.getLanguage().equals(language)) {
                out.add(fact);
                outlangs.add(fact.getLanguage());
            }
        }
        return new RDFFactCollection(out);
    }

    public RDFFactCollection newFilterByLanguages(ArrayList<String> languages) {
        ArrayList<RDFFact> out = new ArrayList<>();
        HashSet<String> outlangs = new HashSet<>();
        for (RDFFact fact: facts) {
            for (String lang: languages) {
                if (fact.getLanguage().equals(lang)) {
                    out.add(fact);
                    outlangs.add(fact.getLanguage());
                }
            }
        }
        return new RDFFactCollection(out);
    }
    public RDFFactCollection newFilterOutLanguage(String language) {
        ArrayList<RDFFact> out = new ArrayList<>();
        HashSet<String> outlangs = new HashSet<>();
        for (RDFFact fact: facts) {
            if (!fact.getLanguage().equals(language)) {
                out.add(fact);
                outlangs.add(fact.getLanguage());
            }
        }
        return new RDFFactCollection(out);
    }

    public RDFFactCollection newFilterOutLanguages(ArrayList<String> languages) {
        ArrayList<RDFFact> out = new ArrayList<>();
        HashSet<String> outlangs = new HashSet<>();
        for (RDFFact fact: facts) {
            boolean ok = true;
            for (String lang: languages) {
                if (fact.getLanguage().equals(lang)) {
                    ok = false;
                }
            }
            if (ok) {
                out.add(fact);
                outlangs.add(fact.getLanguage()); 
            }
        }
        return new RDFFactCollection(out);
    }

    public void filterByRdfProperty(String property) {
        ArrayList<RDFFact> out = new ArrayList<>();
        HashSet<String> outlangs = new HashSet<>();
        for (RDFFact fact: facts) {
            if (fact.getRdfProperty().equals(property)) {
                out.add(fact);
                outlangs.add(fact.getLanguage());
            }
        }
        facts=out;
    }

    public void filterOutRdfProperty(String property) {
        ArrayList<RDFFact> out = new ArrayList<>();
        HashSet<String> outlangs = new HashSet<>();
        for (RDFFact fact: facts) {
            if (!fact.getRdfProperty().equals(property)) {
                out.add(fact);
                outlangs.add(fact.getLanguage());
            }
        }
        facts=out;
    }
    public void filterByLanguage(String language) {
        ArrayList<RDFFact> out = new ArrayList<>();
        HashSet<String> outlangs = new HashSet<>();
        for (RDFFact fact: facts) {
            if (fact.getLanguage().equals(language)) {
                out.add(fact);
            }
        }
        facts=out;
    }

    public void filterByLanguages(ArrayList<String> languages) {
        HashSet<String> outlangs = new HashSet<>();
        ArrayList<RDFFact> out = new ArrayList<>();
        for (RDFFact fact: facts) {
            for (String lang: languages) {
                if (fact.getLanguage().equals(lang)) {
                    out.add(fact);
                    outlangs.add(fact.getLanguage());
                }
            }
        }
        facts=out;
    }
    public void filterOutLanguage(String language) {
        HashSet<String> outlangs = new HashSet<>();
        ArrayList<RDFFact> out = new ArrayList<>();
        for (RDFFact fact: facts) {
            if (!fact.getLanguage().equals(language)) {
                out.add(fact);
                outlangs.add(fact.getLanguage());
            }
        }
        facts=out;
    }

    public void filterOutLanguages(ArrayList<String> languages) {
        HashSet<String> outlangs = new HashSet<>();
        ArrayList<RDFFact> out = new ArrayList<>();
        for (RDFFact fact: facts) {
            boolean ok = true;
            for (String lang: languages) {
                if (fact.getLanguage().equals(lang)) {
                    ok = false;
                }
            }
            if (ok) {
                out.add(fact);
                outlangs.add(fact.getLanguage());
            }
        }
        facts=out;
    }
}
