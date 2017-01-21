package de.kdld16.hpi.util.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.kdld16.hpi.resolver.Resolver;
import de.kdld16.hpi.util.ClassifyProperties;

/**
 * Created by jonathan on 21.01.17.
 */
public class ResolveStep {

    Class<? extends Resolver> resolver;

    public String getResolverName() {
        return resolverName;
    }

    public Class<? extends Resolver> getResolver(String property) {
        return resolver;
    }

    public void setResolverName(String resolverName) {
        this.resolverName = resolverName;
        try{
            this.resolver = Class.forName(Resolver.class.getPackage().getName()+"."+resolverName).asSubclass(Resolver.class);
        } catch(final ClassNotFoundException e){
            throw new IllegalStateException(e);
        }
    }

    public Class<? extends Resolver> getResolver() {
        return this.resolver;
    }

    @JsonProperty("resolver")
    String resolverName;

    public ResolveStep() {
    }
}
