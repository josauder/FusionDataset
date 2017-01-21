package de.kdld16.hpi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by jonathan on 21.01.17.
 */
public class PropertiesUtils {
    static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    public static String getPropertyValue(String prop) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/test/resources/application.properties"));
            String filename = properties.getProperty(prop);
            if (filename==null) {
                logger.error("No Property value found for property:" +prop);
            }
            return filename;
        } catch (IOException e) {
            logger.error("No Application.properties found!");
        }
        return null;
    }

    public static BufferedReader readFileFromProperties(String prop) {
        try {
            String filename=getPropertyValue(prop);
            BufferedReader br = new BufferedReader(new FileReader(filename));
            if (br == null) {
                throw new FileNotFoundException();
            }
            return br;
        } catch (FileNotFoundException e) {
            logger.error("Could not find functional Property file - This means only user defined rdfPropertyWrappers will be resolved." +
                    "You can generate your own functional property file with running the script 'src/scripts/find_functional_properties.py' with the correct paths");
        }
        return null;
    }
}
