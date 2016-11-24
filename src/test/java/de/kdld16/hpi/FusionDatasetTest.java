package de.kdld16.hpi;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Properties;

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
