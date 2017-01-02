package de.kdld16.hpi.util;

import de.kdld16.hpi.modes.ResolveResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by jonathan on 31.12.16.
 */
public class RDFTypeTree {
    /** From http://wiki.dbpedia.org/services-resources/ontology:
     *      "Since the DBpedia 3.7 release, the ontology is a directed-acyclic graph, not a tree.
     *      Classes may have multiple superclasses, which was important for the mappings to schema.org.
     *      A taxonomy can still be constructed by ignoring all superclasses except the one that is specified
     *      first in the list and is considered the most important."
     */
    static Logger logger = LoggerFactory.getLogger(RDFTypeTree.class);

    static HashMap<String,String> subClasses;
    Properties properties;
    static {
        subClasses = new HashMap<>();
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/test/resources/application.properties"));
            BufferedReader br = new BufferedReader(new FileReader(properties.getProperty("ontologyFile")));
            String line;
            while ((line=br.readLine())!=null) {

                String[] triple = DBPediaHelper.replaceNamespace(line).split(" ",3);
                if (triple[1].equals("<http://www.w3.org/2000/01/rdf-schema#subClassOf>")) {
                    if (subClasses.containsKey(triple[0])) {
                        logger.debug(triple[0] + "" + subClasses.get(triple[0]) + "" + triple[2]);
                    }
                    subClasses.put(triple[0],triple[2]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean isSubClassOf(String a, String b) {
        return b.equals(subClasses.get(a));
    }

    public static ResolveResult resolveTypeConflict(RDFFactCollection conflict) {
        //TODO: get all heuristics into property file
        return new ResolveResult(conflict.getOne().getRdfObject(),new ArrayList<>(), 1,1);
    }

}
