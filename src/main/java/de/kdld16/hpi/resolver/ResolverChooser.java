package de.kdld16.hpi.resolver;

import de.kdld16.hpi.util.ClassifyProperties;

/**
 * Created by jonathan on 24.11.16.
 */
public class ResolverChooser {


    public static Resolver getResolver(String property, int n) {

        return new ModeResolver();
    }
}
