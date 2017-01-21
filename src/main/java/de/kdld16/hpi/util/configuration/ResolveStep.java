package de.kdld16.hpi.util.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.kdld16.hpi.resolver.Resolver;

/**
 * Created by jonathan on 21.01.17.
 */
public class ResolveStep {

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    @JsonProperty("resolver")
    String resolver;

    public ResolveStep() {

    }
}
