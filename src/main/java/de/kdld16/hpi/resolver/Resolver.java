package de.kdld16.hpi.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by jonathan on 23.11.16.
 */
public abstract class Resolver {


    public LinkedList<String> resolve(String property, LinkedList<String> conflict) {
        return new LinkedList<>();
    } ;

}

