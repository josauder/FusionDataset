package de.kdld16.hpi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.kdld16.hpi.resolver.Resolver;
import de.kdld16.hpi.util.ClassifyProperties;
import de.kdld16.hpi.util.PropertiesUtils;
import de.kdld16.hpi.util.configuration.ResolverConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jonathan on 21.01.17.
 */
public class JSONTest {

    Logger logger = LoggerFactory.getLogger(JSONTest.class);


    @Test
    public void test() {

        try {
            String filename = PropertiesUtils.getPropertyValue("resolveConfigurationFile");
            ResolverConfiguration config = new ObjectMapper().readValue(new File(filename), ResolverConfiguration.class);
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
    }

}
