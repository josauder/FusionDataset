package de.kdld16.hpi.util.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.kdld16.hpi.resolver.Resolver;
import de.kdld16.hpi.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by jonathan on 21.01.17.
 */
public class JsonConfiguration {
    static Logger logger = LoggerFactory.getLogger(JsonConfiguration.class);
    static ResolverConfiguration resolverConfiguration;
    static HashMap<String,Class<? extends Resolver>> specialResolvers;
    public static ArrayList<String> properties;
    public static ArrayList<Class<? extends Resolver>> resolvers;
    static public HashSet<String> functionalPropertySet;
    static {
        loadResolverConfigurationFromJson();
    }


    public static void loadResolverConfigurationFromJson() {
        try {
            String filename = PropertiesUtils.getPropertyValue("resolveConfigurationFile");
            resolverConfiguration = new ObjectMapper().readValue(new File(filename), ResolverConfiguration.class);
            properties = new ArrayList<>();
            resolvers = new ArrayList<>();
            functionalPropertySet = new HashSet<>();
            for (PropertyResolveConfiguration prop : resolverConfiguration.getPropertyResolveConfigurations()) {
                //TODO: Allows for only one step!!!
                resolvers.add(prop.getList().get(0).getResolver());
                properties.add(prop.getRdfProperty());
                functionalPropertySet.add(prop.getRdfProperty());
            }
            resolverConfiguration = null;
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
    }

    public static Resolver instantiate(int i) {
        return instantiate(resolvers.get(i),properties.get(i));
    }

    private static Resolver instantiate(Class<? extends Resolver> resolver,String property) {
        try{
            return resolver.getDeclaredConstructor(String.class).newInstance(property);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }
}
