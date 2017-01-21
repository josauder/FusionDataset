package de.kdld16.hpi.util.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 21.01.17.
 */
public class ResolverConfiguration {



    @JsonProperty("propertyResolveConfiguration")
    private List<PropertyResolveConfiguration> propertyResolveConfigurations;

    public ResolverConfiguration() {
    }
    public List<PropertyResolveConfiguration> getPropertyResolveConfigurations() {
        return propertyResolveConfigurations;
    }

    public void setPropertyResolveConfigurations(List<PropertyResolveConfiguration> propertyResolveConfigurations) {
        this.propertyResolveConfigurations = propertyResolveConfigurations;
    }
}
