package de.kdld16.hpi;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/test/resources/application.properties"));
            String targetDirectory = (String)properties.get("targetDirectory");
            String targetFilepattern = (String) properties.get("targetFilepattern");

            for (File f: new File(targetDirectory).listFiles()) {
                if (f.getName().contains(targetFilepattern)) {
                   if (f.delete()) {
                       logger.debug("Cleaning Output folder - deleted "+ f.getPath());
                   }
                }
            }


            String[] args = {"--runner=SparkRunner"};
            FusionDataset.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
