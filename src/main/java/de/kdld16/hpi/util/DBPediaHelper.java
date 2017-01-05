package de.kdld16.hpi.util;

/**
 * Created by jonathan on 22.11.16.
 */
public class DBPediaHelper {


    public static String wikidataPrefix = "<http://wikidata.dbpedia.org/resource/Q";
    public static String wikidataPostfix = ">";


    public static String replaceNamespace(String fact) {
        return fact
                .replaceAll("http://dbpedia.org/ontology/","dbo:")
                .replaceAll("http://www.w3.org/1999/02/22-rdf-syntax-ns#","rdf:")
                .replaceAll("http://www.wikidata.org/entity/","wikidata:")
                .replaceAll("http://www.w3.org/2001/XMLSchema#","xsd:")
                .replaceAll("http://www.w3.org/2002/07/owl#","owl:");
    }
}
