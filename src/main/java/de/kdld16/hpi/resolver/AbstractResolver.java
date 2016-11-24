package de.kdld16.hpi.resolver;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by jonathan on 23.11.16.
 */
public interface AbstractResolver {

    public LinkedList<String> resolve(String property, LinkedList<String> conflict) ;

}

