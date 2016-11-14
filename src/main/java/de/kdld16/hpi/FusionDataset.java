package de.kdld16.hpi;
import de.kdld16.hpi.transforms.RDFSubjectAsKey;
import de.kdld16.hpi.transforms.ResolveFusionConflicts;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jonathan on 11/13/16.
 */
public class FusionDataset {

    static Logger logger = LoggerFactory.getLogger(FusionDataset.class);

    static String datasetDirectory = "/home/jonathan/hpi/ld/datasetsTest/*";
    static String targetDirectory = "/home/jonathan/hpi/ld/datasetsTest/outputs/output";

    /**
     * Main Class which defines and runs workflow pipeline
     */
    public static void main(String[] args) {
        PipelineOptions options = PipelineOptionsFactory.create();

        Pipeline p = Pipeline.create(options);

        p.apply(TextIO.Read.from(datasetDirectory))
                // Split Line into Key-Value Pair with RDF-Subject as Key
                .apply(ParDo.of(new RDFSubjectAsKey()))
                // Group by RDF-Subject (See Apache Beam Documentation)
                .apply(GroupByKey.<String,String>create())
                // Resolve Fusion Conflicts
                .apply(ParDo.of(new ResolveFusionConflicts()))
                // Write output to targetDirectory
                .apply(TextIO.Write.to(targetDirectory));

        // try/catch Block due to Version 0.3.0 of apache beam, unnecessary as of Version 0.4.0-SNAPSHOT
        try {
            p.run().waitUntilFinish();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
}
