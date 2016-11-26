package de.kdld16.hpi;
import de.kdld16.hpi.transforms.FilterByWikidataID;
import de.kdld16.hpi.transforms.RDFSubjectAsKey;
import de.kdld16.hpi.transforms.ResolveFusionConflicts;
import de.kdld16.hpi.util.ClassifyProperties;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by jonathan on 11/13/16.
 */
public class FusionDataset {

    static Logger logger = LoggerFactory.getLogger(FusionDataset.class);



    /**
     * Main Class which defines and runs workflow pipeline
     */
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/test/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        String datasetDirectory = properties.getProperty("datasetDirectory");
        String targetDirectory = properties.getProperty("targetDirectory");
        String targetFilepattern = properties.getProperty("targetFilepattern");

        PipelineOptions options = PipelineOptionsFactory.create();

        Pipeline p = Pipeline.create(options);


        p.apply(TextIO.Read.from(datasetDirectory+"/*")
                //should work automatically!
                //.withCompressionType(TextIO.CompressionType.BZIP2)
                )
                // Split Line into Key-Value Pair with RDF-Subject as Key
                .apply(ParDo.of(new RDFSubjectAsKey()))
                // Group by RDF-Subject (See Apache Beam Documentation)
                .apply(GroupByKey.<String,String>create())
                // Resolve Fusion Conflicts
                //.apply(ParDo.of(new FilterByWikidataID<>(0,100)))
                .apply(ParDo.of(new ResolveFusionConflicts()))
                // Write output to targetDirectory
                .apply(TextIO.Write.to(targetDirectory+"/"+targetFilepattern).withSuffix(".ttl"));

        // try/catch Block due to Version 0.3.0 of apache beam, unnecessary as of Version 0.4.0-SNAPSHOT
        try {
            p.run().waitUntilFinish();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
}
