package de.kdld16.hpi.util.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 21.01.17.
 */
public class PropertyResolveConfiguration {

    @JsonProperty("rdfProperty")
    private String rdfProperty;

    @JsonProperty("resolveSteps")
    private List<ResolveStep> list;

    public List<ResolveStep> getList() {
        return list;
    }

    public void setList(List<ResolveStep> list) {
        this.list = list;
    }

    public String getRdfProperty() {
        return rdfProperty;
    }

    public void setRdfProperty(String rdfProperty) {
        this.rdfProperty = rdfProperty;
    }

    public PropertyResolveConfiguration() {

    }
}
