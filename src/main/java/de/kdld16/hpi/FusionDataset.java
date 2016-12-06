package de.kdld16.hpi;
import de.kdld16.hpi.transforms.LanguageTagAdder;
import de.kdld16.hpi.transforms.RDFSubjectAsKey;
import de.kdld16.hpi.transforms.ResolveFusionConflicts;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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


        File[] files = new File(datasetDirectory).listFiles();
        PCollectionList<String> dataSet=null;

        /*for (File f: files) {
            String language = f.getName().split("wkd_uris_")[1].replace(".ttl","");
            logger.debug(language);
            PCollection<String> newPCollection;
            newPCollection = p.apply(
                    TextIO.Read.from(f.getAbsolutePath())).apply(ParDo.of(new LanguageTagAdder(language)));

            if (dataSet==null) {
                dataSet=PCollectionList.of(newPCollection);
            } else {
                dataSet.and(newPCollection);
            }
        }*/
        //PCollection<String> completeDataset=
           //     dataSet.apply(Flatten.<String>pCollections())
        //p.apply(completeDataset
                //should work automatically!
                //.withCompressionType(TextIO.CompressionType.BZIP2)
          //      )
        p.apply(TextIO.Read.from(datasetDirectory+"/*"))
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
