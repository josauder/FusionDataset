package de.kdld16.hpi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.kdld16.hpi.resolver.Resolver;
import de.kdld16.hpi.util.configuration.PropertyResolveConfiguration;
import de.kdld16.hpi.util.configuration.ResolverConfiguration;
import de.kdld16.hpi.util.rdfdatatypecomparison.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by jonathan on 11/14/16.
 */
public class ClassifyProperties {

    static Logger logger = LoggerFactory.getLogger(ClassifyProperties.class);

    public static HashMap<String, Class<? extends RDFDatatypeWrapper>> rdfPropertyWrappers;
    public static ResolverConfiguration resolverConfiguration;

    static {
        resolveAllRdfDatatypeWrappers(readFunctionalPropertiesFile());
    }

    public static HashSet<String> readFunctionalPropertiesFile() {
        try {
            BufferedReader br;
            String line;

            HashSet<String> functionalProperties = new HashSet<>();
            br = PropertiesUtils.readFileFromProperties("functionalPropertiesFile");
            while ((line = br.readLine()) != null) {
                functionalProperties.add(DBPediaHelper.replaceNamespace(line));
            }
            return functionalProperties;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static void resolveAllRdfDatatypeWrappers(HashSet<String> functionalProperties) {
        try {
            BufferedReader br;
            String line;
            rdfPropertyWrappers = new HashMap<>();
            br = PropertiesUtils.readFileFromProperties("ontologyFile");
            while ((line = br.readLine()) != null) {
                if (line.contains("/populationTotal")) {
                    logger.debug("SASDSADSADASDSA");
                }
                String[] triple = DBPediaHelper.replaceNamespace(line).split(" ", 3);
                if (triple[1].equals("<http://www.w3.org/2000/01/rdf-schema#range>") && functionalProperties.contains(triple[0])) {
                    Class<? extends RDFDatatypeWrapper> wrapper = rdfDataTypeToWrapper(triple[2]);
                    logger.debug("Property: " + triple[0] + " mapped to RDFDatatypeWrapper: " + wrapper.getSimpleName());
                    rdfPropertyWrappers.put(triple[0], wrapper);
                }
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static Class<? extends RDFDatatypeWrapper> rdfDataTypeToWrapper(String property) {
        if (property.endsWith(" .")) {
            property=property.substring(0,property.length()-2);
        }
        if (property.equals("<xsd:double>")) {
            return DoubleWrapper.class;

        }
        if (property.equals("<xsd:nonNegativeInteger>") ||
                property.equals("<xsd:integer>")) {
            return IntegerWrapper.class;

        }
        if (property.equals("<rdf:langString>")) {
            return CloseStringWrapper.class;
        }
        return IdenticalStringWrapper.class;
    }


    public static RDFDatatypeWrapper getRdfDatatypeWrapper(RDFFact fact) {
        return getRdfDatatypeWrapper(fact.getRdfProperty());
    }


    public static RDFDatatypeWrapper getRdfDatatypeWrapper(String property) {

        Class<? extends RDFDatatypeWrapper> c=null;
        if (rdfPropertyWrappers.containsKey(property)) {
            c = ClassifyProperties.rdfPropertyWrappers.get(property);
        } else {
            c=rdfDataTypeToWrapper(property);
        }

        try {
            return c.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
                logger.error(e.getStackTrace().toString());
                return null;
        }
    }
}
