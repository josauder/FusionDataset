package de.kdld16.hpi.util;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by jonathan on 11/14/16.
 */
public class ClassifyProperties {
    //TODO: Everything, just a dummy so far


    static String[] acceptOnlyOneArray= {
        "<http://dbpedia.org/ontology/weight>",
                "<http://dbpedia.org/ontology/acceleration>",
                "<http://dbpedia.org/ontology/populationTotal>",
                "<http://dbpedia.org/ontology/wheelbase>",
                "<http://dbpedia.org/ontology/co2Emission>",
                "<http://dbpedia.org/ontology/retirementDate>",
                "<http://dbpedia.org/ontology/averageAnnualGeneration>",
                "<http://dbpedia.org/ontology/height>",
                "<http://dbpedia.org/ontology/topSpeed>",
                "<http://dbpedia.org/ontology/birthYear>",
                "<http://dbpedia.org/ontology/restingDate>",
                "<http://dbpedia.org/ontology/zipCode>",
                "<http://dbpedia.org/ontology/deathDate>",
                "<http://dbpedia.org/ontology/fuelCapacity>",
                "<http://dbpedia.org/ontology/latestReleaseDate>",
                "<http://dbpedia.org/ontology/netIncome>",
                "<http://dbpedia.org/ontology/deathYear>",
                "<http://dbpedia.org/ontology/birthDate>",
                "<http://dbpedia.org/ontology/installedCapacity>",
                "<http://dbpedia.org/ontology/foalDate>",
                "<http://dbpedia.org/ontology/redline>",
                "<http://dbpedia.org/ontology/diameter>",
                "<http://dbpedia.org/ontology/length>",
                "<http://dbpedia.org/ontology/operatingIncome>",
                "<http://dbpedia.org/ontology/torqueOutput>",
                "<http://dbpedia.org/ontology/width>",
                "<http://dbpedia.org/ontology/marketCapitalisation>",
                "<http://dbpedia.org/ontology/fuelConsumption>",
                "<http://dbpedia.org/ontology/displacement>",
                "<http://dbpedia.org/ontology/powerOutput>",
                "<http://www.w3.org/2003/01/geo/wgs84_pos#lat>",
                "<http://www.w3.org/2003/01/geo/wgs84_pos#long>",
                "<http://www.georss.org/georss/point>",
                "<http://dbpedia.org/ontology/iso31661Code>",
                "<http://dbpedia.org/ontology/iso6391Code>",
                "<http://dbpedia.org/ontology/iso6392Code>",
                "<http://dbpedia.org/ontology/iso6393Code>",
                "<http://dbpedia.org/ontology/totalPopulation>",
                "<http://xmlns.com/foaf/0.1/homepage"};

    public static HashSet<String> acceptOnlyOne = new HashSet<>(Arrays.asList(acceptOnlyOneArray));

}
