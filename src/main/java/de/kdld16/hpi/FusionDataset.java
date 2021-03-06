package de.kdld16.hpi;
import de.kdld16.hpi.transforms.LanguageTagAdder;
import de.kdld16.hpi.transforms.PartitionByDataset;
import de.kdld16.hpi.transforms.ResolveFusionConflicts;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
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

        PipelineOptions options = PipelineOptionsFactory.fromArgs(args).create();
        //options.setRunner(DirectRunner.class);
        Pipeline p = Pipeline.create(options);

        /*
  SparkPipelineOptions options = PipelineOptionsFactory.as(SparkPipelineOptions.class);
        options.setSparkMaster("local[2]");
        Pipeline p = Pipeline.create(options);
        */


        File[] files = new File(datasetDirectory).listFiles();
        PCollectionList<KV<Integer,String>> dataSet=null;

        for (File f: files) {
            String language = f.getName().split("wkd_uris_")[1].replace(".ttl","");
            PCollection<KV<Integer,String>> newPCollection;
            newPCollection = p.apply(f.getName()+"-Reader",
                    TextIO.Read.from(f.getAbsolutePath())).apply(f.getName()+"-LanguageTagAdder",ParDo.of(new LanguageTagAdder(language)));

            if (dataSet==null) {
                dataSet=PCollectionList.of(newPCollection);
            } else {
                dataSet=dataSet.and(newPCollection);
          //      dataSet.apply(Flatten.<String>pCollections());
            }
        }

        PCollectionList<String> list = dataSet.apply(Flatten.<KV<Integer,String>>pCollections())
            // Group by RDF-Subject (See Apache Beam Documentation)
              //  .apply(ParDo.of(new FilterByWikidataID<>(495,495)))
            .apply(GroupByKey.<Integer,String>create())
            // Resolve Fusion Conflicts
            .apply(ParDo.of(new ResolveFusionConflicts()))
            // Write output to targetDirectory
                .apply(Partition.of(3, new PartitionByDataset()));


        String[] datasetNames = {"instance_types_transitive_fused_wkd_uris.ttl", "mappingbased_objects_fused_wkd_uris.ttl", "mappingbased_literals_fused_wkd_uris.ttl"};
        for (int i = 0; i<3; i++) {
            list.get(i).apply(TextIO.Write.withoutSharding().to(targetDirectory+"/"+datasetNames[i]));
        } {
        }

        // try/catch Block due to Version 0.3.0 of apache beam, unnecessary as of Version 0.4.0-SNAPSHOT
        try {
            p.run().waitUntilFinish();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
}
