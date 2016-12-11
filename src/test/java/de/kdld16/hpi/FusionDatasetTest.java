package de.kdld16.hpi;

//import org.junit.jupiter.api.Test;
import org.slf4j.*;
import org.slf4j.Logger;
import org.junit.*;


/**
 * Unit test for simple App.
 */
public class FusionDatasetTest
{

    Logger logger = LoggerFactory.getLogger(FusionDatasetTest.class);

    @Test
    public void TestFusionDataset () {
        /*
        logger.info("Fetching Dataset in Path : "+inputDirectory);
        logger.info("Writing Outputs to Path : "+outputDirectory+" with File Prefix "+outputPrefix);
        String[] args = {inputDirectory+"*", outputDirectory+"/"+outputPrefix};
           */
        String[] args={};
        FusionDataset.main(args);
    }
}
