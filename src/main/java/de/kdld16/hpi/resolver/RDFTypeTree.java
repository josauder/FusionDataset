package de.kdld16.hpi.resolver;

import de.kdld16.hpi.WikidataEntity;
import de.kdld16.hpi.exception.NotInOntologyException;
import de.kdld16.hpi.util.DBPediaHelper;
import de.kdld16.hpi.util.RDFFact;
import de.kdld16.hpi.util.RDFFactCollection;
import de.kdld16.hpi.util.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by jonathan on 31.12.16.
 */
public class RDFTypeTree implements Resolver {
    /** From http://wiki.dbpedia.org/services-resources/ontology:
     *      "Since the DBpedia 3.7 release, the ontology is a directed-acyclic graph, not a tree.
     *      Classes may have multiple superclasses, which was important for the mappings to schema.org.
     *      A taxonomy can still be constructed by ignoring all superclasses except the one that is specified
     *      first in the list and is considered the most important."
     */
    static Logger logger = LoggerFactory.getLogger(RDFTypeTree.class);

    static HashMap<String,String> subClasses;
    static HashMap<String,String> equivalentClasses;
    Properties properties;
    static {
        subClasses = new HashMap<>();
        equivalentClasses= new HashMap<>();
        try {
            /**
             * Parses ontology file for the properities: subClassOf and equivalentClass - saves them in HashMap.
             */
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/test/resources/application.properties"));
            BufferedReader br = new BufferedReader(new FileReader(properties.getProperty("ontologyFile")));
            String line;
            while ((line=br.readLine())!=null) {
                String[] triple = DBPediaHelper.replaceNamespace(line).split(" ",3);
                if (triple[1].equals("<http://www.w3.org/2000/01/rdf-schema#subClassOf>")) {
                    if (!subClasses.containsKey(triple[0])) {
                        subClasses.put(triple[0],triple[2].substring(0,triple[2].length()-2));
                    }
                } else if ((triple[1].equals("<owl:equivalentClass>") && (triple[2].startsWith("<dbo:")))) {
                    if (!equivalentClasses.containsKey(triple[0])) {
                        equivalentClasses.put(triple[2].substring(0, triple[2].length() - 2),triple[0]);
                    } else {
                        logger.warn("Equivalent Class for " + triple[0] + " already exists");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean isSubClassOf(String a, String b) throws NotInOntologyException{
        String c = subClasses.get(a);
        if (c==null) {
            throw new NotInOntologyException(a);
        }
        return b.equals(subClasses.get(a));
    }

    public static String getEquivalentClass(String c) {
        return equivalentClasses.containsKey(c) ? equivalentClasses.get(c) : c;
    }

    private static HashMap<String,ArrayList<String>> toCountingLanguageMap(RDFFactCollection conflict) {
        HashMap<String, ArrayList<String>> dboTypes = new HashMap<>();
        for (RDFFact fact : conflict.asList()) {
            String ob = getEquivalentClass(fact.getRdfObject());
            if (ob.startsWith("<dbo:") || ob.startsWith("<owl:")) {
                if (!dboTypes.containsKey(ob)) {
                    dboTypes.put(ob, new ArrayList<>());
                }
                dboTypes.get(ob).add(fact.getLanguage());
            }
        }
        return dboTypes;
    }

    /**
     * Constructs the tree from the  HashMap constructed in toCountingLanguageMap:
     * Warning: This is terrible code - it is hacky and messy - it only exists becuase University Projects run on a deadline!
     * @param rootNode
     * @param dboTypes
     */
    private static void constructTreeFromMap(TreeNode rootNode, HashMap<String,ArrayList<String>> dboTypes) {
        while (dboTypes.size() > 0) {
            for (Map.Entry<String, ArrayList<String>> e : dboTypes.entrySet()) {
                boolean br2 = false;
                ArrayList<TreeNode> currentNodes = new ArrayList<>();

                currentNodes.add(rootNode);
                while (!currentNodes.isEmpty()) {
                    boolean br = false;
                    ArrayList<TreeNode> newCurrentNodes = new ArrayList<>();

                    for (TreeNode n : currentNodes) {
                        try {
                            if (isSubClassOf(e.getKey(), n.getKey())) {
                                n.addChild(new TreeNode(e.getKey(), e.getValue(), null));
                                dboTypes.entrySet().remove(e);
                                br = true;
                                break;
                            }
                        } catch (NotInOntologyException ex) {
                            ex.printStackTrace();
                            dboTypes.entrySet().remove(e);
                            br = true;
                            break;
                        }
                        newCurrentNodes.addAll(n.getChildren());
                    }
                    if (br) {
                        br2 = true;
                        break;
                    }
                    currentNodes = newCurrentNodes;
                }
                if (br2) {
                    break;
                }
            }
        }
    }

    /**
     * We construct the Subclass-Tree on the property rdf:type and keep only the type which
     * is dominant in all possible conflicts
     * @param conflict
     * @return best-matching type
     */
    @Override
    public void resolve(RDFFactCollection conflict, WikidataEntity entity) {
        //TODO: get all heuristics into property file
        /**
         * First we construct the tree from the rdf:type conflict, we save how many languages we have type property for
         */
        HashMap<String,ArrayList<String>> dboTypes = toCountingLanguageMap(conflict);
        TreeNode node = new TreeNode("<owl:Thing>", dboTypes.get("<owl:Thing>"), null);
        dboTypes.remove("<owl:Thing>");
        constructTreeFromMap(node, dboTypes);
        confindencesFromTree(1,node,entity);
    }

    public void confindencesFromTree(double confidence, TreeNode tree, WikidataEntity entity) {
        entity.assignConfidenceToFact(confidence,new RDFFact("<rdf:type>",tree.getKey(),tree.getValue()));
        int total=0;
        for (TreeNode child : tree.getChildren()) {
            total += child.getValue().size();
        }
        for (TreeNode child: tree.getChildren()) {
            confindencesFromTree((confidence*child.getValue().size())/total, child, entity);
        }
    }
}

